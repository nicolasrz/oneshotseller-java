package com.nruiz.oneshot.repositories;

import com.nruiz.oneshot.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 * Created by Nicolas on 28/10/2017.
 */

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {


}