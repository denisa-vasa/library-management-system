package com.gisdev.dea.repository;

import com.gisdev.dea.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query("SELECT o FROM Orders o WHERE o.status = 'PENDING'")
    List<Orders> getAllOrdersPending();

    List<Orders> findByUsersId(Long userId);
}
