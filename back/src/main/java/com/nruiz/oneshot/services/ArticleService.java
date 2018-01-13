package com.nruiz.oneshot.services;

import com.nruiz.oneshot.models.Article;
import com.nruiz.oneshot.models.Elem;
import com.nruiz.oneshot.repositories.ArticleRepository;
import com.nruiz.oneshot.repositories.StockRepository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private ArticleRepository articleRepository;
    private StockRepository stockRepository;

    public ArticleService(ArticleRepository articleRepository,
        StockRepository stockRepository) {
        this.articleRepository = articleRepository;
        this.stockRepository = stockRepository;
    }

    public List<Article> getArticles(){
        return this.articleRepository.findAll();
    }

    public Article getArticleById(long id){
        return this.articleRepository.findOne(id);
    }

    public List<Article> getArticlesByIds(List<Elem> elems){
        List<Article> getArticles = new ArrayList<>();
        for(Elem elem : elems){
            Article article = articleRepository.findOne(elem.getId());
            article.setIndex(elem.getIndex());
            getArticles.add(article);

        }
        return getArticles;
    }
    public Article saveArticle(Article article){
        Article articleToSave = new Article();

        articleToSave.setDescription(article.getDescription());
        articleToSave.setName(article.getName());
        articleToSave.setPrice(article.getPrice());
        articleToSave.setImage(article.getImage());
        articleToSave.setStock(this.stockRepository.findOne(article.getStock().getId()));

        return this.articleRepository.save(articleToSave);
    }
}
