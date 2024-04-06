package com.gisdev.dea.service;

import com.gisdev.dea.dataType.Status;
import com.gisdev.dea.dto.bookDto.SimpleBookDto;
import com.gisdev.dea.dto.orderDto.OrderDto;
import com.gisdev.dea.entity.Book;
import com.gisdev.dea.entity.Orders;
import com.gisdev.dea.entity.OrderBook;
import com.gisdev.dea.exception.BadRequestException;
import com.gisdev.dea.exception.NotFoundException;
import com.gisdev.dea.exception.OrderRejectedException;
import com.gisdev.dea.repository.OrdersRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrdersService {

    private OrdersRepository ordersRepository;
    private BookService bookService;
    private OrderBookService orderBookService;

    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    public Orders findById(Long userId) {
        return ordersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Order with id " + userId + " was not found!"));
    }

    @Transactional(rollbackOn = Exception.class)
    public void saveOrder(OrderDto orderDto) {
        List<SimpleBookDto> bookList = orderDto.getLibra();

        if (bookList.isEmpty()) {
            throw new BadRequestException("List of books is empty!");
        }

        checkStock(bookList);
        Orders orders = new Orders();
        orders.setStatus(Status.PENDING);
        orders.setTotalPrice(0.0);
        orders = ordersRepository.save(orders);

        for (SimpleBookDto book : bookList) {
            Book bookFromDb = bookService.findById(book.getId());
            OrderBook orderBook = new OrderBook();
            orderBook.setOrders(orders);
            orderBook.setQuantity(book.getQuantity());
            orderBook.setBook(bookFromDb);
            orderBookService.save(orderBook);
        }
    }

    public List<Orders> getAllOrdersPending() {
        return ordersRepository.getAllOrdersPending();
    }

    public void executeOrder(Long orderId, Status status) {
        Orders orders = findById(orderId);

        if (status == null) {
            throw new BadRequestException("Status of the order should not be empty!");
        }
        double total = 0.0;
        if (status.equals(Status.ACCEPTED)) {
            List<OrderBook> orderBookList = orderBookService.findByOrder(orders);
            for (OrderBook orderBook : orderBookList) {
                Book book = orderBook.getBook();
                book.setStock(book.getStock() - orderBook.getQuantity());
                bookService.saveBook(book);
                total += book.getPrice() * orderBook.getQuantity();
            }
        } else {
            throw new OrderRejectedException("Your order has not been accepted!");
        }
        orders.setStatus(status);
        orders.setTotalPrice(total);
        ordersRepository.save(orders);
    }

    public void checkStock(List<SimpleBookDto> bookList) {
        for (SimpleBookDto book : bookList) {
            Book bookFromDb = bookService.findById(book.getId());

            if (bookFromDb.getStock()== null || bookFromDb.getStock() <= 0) {
                throw new BadRequestException("Book with title " + bookFromDb.getTitle() +
                        " has not enough stock!");
            }

            //check for book quantity and book stock
            if (bookFromDb.getStock() < book.getQuantity()) {
                throw new BadRequestException("Not enough stock for book: " + bookFromDb.getTitle());
            }
        }
    }

    public List<Orders> getAllOrderUser(Long userId) {
        return ordersRepository.findByUsersId(userId);
    }
}

