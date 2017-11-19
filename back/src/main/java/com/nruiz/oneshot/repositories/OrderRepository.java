package com.nruiz.oneshot.repositories;

import com.nruiz.oneshot.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Nicolas on 28/10/2017.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
}
