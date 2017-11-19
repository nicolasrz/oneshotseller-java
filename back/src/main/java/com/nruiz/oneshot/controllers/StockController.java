package com.nruiz.oneshot.controllers;

import com.nruiz.oneshot.models.Stock;
import com.nruiz.oneshot.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Nicolas on 28/10/2017.
 */
@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Stock> addStock(@RequestBody() int total){
        Stock stock = new Stock();
        stock.setTotal(total);
        stock = this.stockRepository.save(stock);

        return new ResponseEntity<>(stock, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Stock>> getAllStocks(){
        return new ResponseEntity<>(this.stockRepository.findAll(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Stock> getStockById(@PathVariable Long id){
        return new ResponseEntity<>(this.stockRepository.findOne(id), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/", method = RequestMethod.PATCH)
    public ResponseEntity<Stock> updateStock(@RequestBody Stock s){
       Stock stock = this.stockRepository.findOne(s.getId());
       stock.setTotal(s.getTotal());
       stock = this.stockRepository.save(stock);

       return new ResponseEntity<>(stock, HttpStatus.CREATED);

    }
}
