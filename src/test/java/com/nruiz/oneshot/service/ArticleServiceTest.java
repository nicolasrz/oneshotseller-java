package com.nruiz.oneshot.service;

import static org.junit.Assert.assertEquals;

import com.nruiz.oneshot.Application;
import com.nruiz.oneshot.models.Article;
import com.nruiz.oneshot.models.Elem;
import com.nruiz.oneshot.models.Stock;
import com.nruiz.oneshot.services.ArticleService;
import com.nruiz.oneshot.services.StockService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {Application.class})
@RunWith(SpringRunner.class)
@DataJpaTest
@EnableAutoConfiguration
@ComponentScan("com.nruiz.oneshot")
public class ArticleServiceTest {

    @Autowired(required = true)
    private ArticleService articleService;


    @Autowired(required = true)
    private StockService stockService;

    public ArticleServiceTest() {
    }

    @Test
    public void testSaveArticle(){
        Article article = new Article();

        Stock stock = this.stockService.createStock(100);
        article.setStock(stock);
        article.setImage("image");
        article.setPrice(100f);
        article.setName("name");
        article.setDescription("description");

        Article articleSaved = this.articleService.saveArticle(article);

        assertEquals(article.getImage(), articleSaved.getImage());
        assertEquals(article.getDescription(), articleSaved.getDescription());
        assertEquals(article.getPrice(), articleSaved.getPrice(), 0f);
        assertEquals(article.getName(), articleSaved.getName());
        assertEquals(article.getStock().getTotal(), articleSaved.getStock().getTotal());


    }

    @Test
    public void testGetArticleById(){
        Article article = new Article();

        Stock stock = this.stockService.createStock(100);
        article.setStock(stock);
        article.setImage("image");
        article.setPrice(100f);
        article.setName("name");
        article.setDescription("description");
        article.setId(1);

        article = this.articleService.saveArticle(article);

        Article isArticleSaved = this.articleService.getArticleById(article.getId());


        assertEquals(article.getDescription(), isArticleSaved.getDescription());
        assertEquals(article.getImage(), isArticleSaved.getImage());
        assertEquals(article.getPrice(), isArticleSaved.getPrice(), 0f);
        assertEquals(article.getName(), isArticleSaved.getName());
        assertEquals(article.getStock().getTotal(), isArticleSaved.getStock().getTotal());

    }

    @Test
    public void testGetArticleByIdNotFound(){
        Article articleNotfound = this.articleService.getArticleById(200);

        assertEquals(null, articleNotfound.getDescription());
        assertEquals(null, articleNotfound.getImage());
        assertEquals(0.0f, articleNotfound.getPrice(), 0f);
        assertEquals(null, articleNotfound.getName());
        assertEquals(null, articleNotfound.getStock());
    }


    @Test
    public void getArticlesByElems(){
        Article article1 = new Article();

        Stock stock = this.stockService.createStock(100);
        article1.setStock(stock);
        article1.setImage("image");
        article1.setPrice(100f);
        article1.setName("name");
        article1.setDescription("description");

        Article article2 = new Article();

        Stock stock2 = this.stockService.createStock(100);
        article2.setStock(stock2);
        article2.setImage("image");
        article2.setPrice(100f);
        article2.setName("name");
        article2.setDescription("description");

        article1 = this.articleService.saveArticle(article1);
        article2 = this.articleService.saveArticle(article2);

        Elem elem1 = new Elem();
        elem1.setId(article1.getId());
        elem1.setIndex((long) 4);

        Elem elem2 = new Elem();
        elem2.setId(article2.getId());
        elem2.setIndex((long) 5);

        List<Elem> elems = new ArrayList<>();
        elems.add(elem1);
        elems.add(elem2);

        List<Article> getArticles = this.articleService.getArticlesByElems(elems);

        assertEquals(getArticles.get(0).getIndex(), article1.getIndex());
        assertEquals(getArticles.get(0).getId(), article1.getId());

        assertEquals(getArticles.get(1).getIndex(), article2.getIndex());
        assertEquals(getArticles.get(1).getId(), article2.getId());



    }

}
