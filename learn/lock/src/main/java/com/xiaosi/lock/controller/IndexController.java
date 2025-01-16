package com.xiaosi.lock.controller;

import com.baomidou.lock.exception.LockFailureException;
import com.xiaosi.lock.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/process/{userId}")
    public ResponseEntity<?> processOrder(@PathVariable String userId) {
        try {
            orderService.processOrder(userId);
            return ResponseEntity.ok("Order processed successfully");
        } catch (LockFailureException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Failed to acquire lock");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
