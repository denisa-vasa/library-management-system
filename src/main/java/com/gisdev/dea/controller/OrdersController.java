package com.gisdev.dea.controller;

import com.gisdev.dea.dataType.Status;
import com.gisdev.dea.dto.orderDto.OrderDto;
import com.gisdev.dea.entity.Orders;
import com.gisdev.dea.service.OrdersService;
import com.gisdev.dea.util.constant.RestConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(RestConstants.OrdersController.BASE_PATH)
public class OrdersController {

    private OrdersService ordersService;

    @PostMapping(RestConstants.OrdersController.SAVE_POROSI)
    @PreAuthorize("hasAnyAuthority(@Role.USER_NAME)")
    public ResponseEntity<String> saveOrder(@RequestBody @Validated OrderDto orderDto) {
        ordersService.saveOrder(orderDto);
        return ResponseEntity.ok("The order was saved successfully!");
    }

    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> ordersList = ordersService.getAllOrders();
        return new ResponseEntity<>(ordersList, HttpStatus.OK);
    }

    @GetMapping(RestConstants.OrdersController.GET_POROSI_PENDING)
    @PreAuthorize("hasAnyAuthority(@Role.ADMIN_NAME)")
    public ResponseEntity<List<Orders>> getAllOrdersPending() {
        List<Orders> orders = ordersService.getAllOrdersPending();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping(RestConstants.OrdersController.EXECUTE_ORDER)
    @PreAuthorize("hasAnyAuthority(@Role.ADMIN_NAME)")
    public ResponseEntity<Long> executeOrder(@PathVariable Long id,
                                             @RequestParam Status status) {
        ordersService.executeOrder(id, status);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping(RestConstants.OrdersController.GET_ORDER_USER)
    @PreAuthorize("hasAnyAuthority(@Role.USER_NAME)")
    public ResponseEntity<List<Orders>> getAllOrderUser(@PathVariable Long id) {
        List<Orders> ordersList = ordersService.getAllOrderUser(id);
        return new ResponseEntity<>(ordersList, HttpStatus.OK);
    }
}
