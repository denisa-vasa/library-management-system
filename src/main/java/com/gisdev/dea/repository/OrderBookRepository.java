package com.gisdev.dea.repository;

import com.gisdev.dea.entity.Orders;
import com.gisdev.dea.entity.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {
    List<OrderBook> findByOrders(Orders orders);
}
