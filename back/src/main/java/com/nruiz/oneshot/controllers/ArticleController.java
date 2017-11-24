package com.nruiz.oneshot.controllers;

import com.nruiz.oneshot.models.Article;
import com.nruiz.oneshot.repositories.ArticleRepository;
import com.nruiz.oneshot.repositories.StockRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;


    @Autowired
    private StockRepository stockRepository;



    @RequestMapping(value = "/", method= RequestMethod.GET)
    public ResponseEntity<List<Article>> getArticles(){

        List<Article> articles = this.articleRepository.findAll();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public ResponseEntity<Article> getArticleById(@PathVariable long id){

        Article article = this.articleRepository.findOne(id);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method= RequestMethod.POST, produces="application/json", consumes="application/json")
    public ResponseEntity<Article> saveArticle(@RequestBody Article article){
        Article articleToSave = new Article();

        articleToSave.setDescription(article.getDescription());
        articleToSave.setName(article.getName());
        articleToSave.setPrice(article.getPrice());
        articleToSave.setImage(article.getImage());
        articleToSave.setStock(this.stockRepository.findOne(article.getStock().getId()));

        articleToSave = this.articleRepository.save(articleToSave);
        return new ResponseEntity<>(articleToSave, HttpStatus.CREATED);
    }
}
