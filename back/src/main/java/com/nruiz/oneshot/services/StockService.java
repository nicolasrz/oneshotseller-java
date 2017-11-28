package com.nruiz.oneshot.services;

import com.nruiz.oneshot.models.Stock;
import com.nruiz.oneshot.repositories.StockRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock createStock(int total){
        Stock stock = new Stock();
        stock.setTotal(total);

        return this.stockRepository.save(stock);
    }

    public List<Stock> getAllStocks(){
        return this.stockRepository.findAll();
    }

    public Stock getStockById(Long id){
        return this.stockRepository.findOne(id);
    }

    public Stock updateStock(Stock s){
        Stock stock = this.stockRepository.findOne(s.getId());
        stock.setTotal(s.getTotal());

        return this.stockRepository.save(stock);
    }
}
