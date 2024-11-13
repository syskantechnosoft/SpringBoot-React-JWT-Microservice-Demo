package com.syskan.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syskan.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
