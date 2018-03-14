package com.nruiz.oneshot.controllers;

import com.nruiz.oneshot.models.Stock;
import com.nruiz.oneshot.services.StockService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Nicolas on 28/10/2017.
 */
@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Stock> createStock(@RequestBody() int total){
        return new ResponseEntity<>(this.stockService.createStock(total), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Stock>> getAllStocks(){
        return new ResponseEntity<>(this.stockService.getAllStocks(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Stock> getStockById(@PathVariable Long id){
        return new ResponseEntity<>(this.stockService.getStockById(id), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/", method = RequestMethod.PATCH)
    public ResponseEntity<Stock> updateStock(@RequestBody Stock s){
       return new ResponseEntity<>(this.stockService.updateStock(s), HttpStatus.CREATED);

    }
}
