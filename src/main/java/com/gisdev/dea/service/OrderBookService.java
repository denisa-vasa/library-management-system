package com.gisdev.dea.service;

import com.gisdev.dea.entity.Orders;
import com.gisdev.dea.entity.OrderBook;
import com.gisdev.dea.repository.OrderBookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderBookService {

    private OrderBookRepository orderBookRepository;

    public void save(OrderBook orderBook) {
        orderBookRepository.save(orderBook);
    }

    public List<OrderBook> findByOrder(Orders orders) {
        return orderBookRepository.findByOrders(orders);
    }
}
