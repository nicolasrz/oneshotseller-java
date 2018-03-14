package com.nruiz.oneshot.repositories;

import com.nruiz.oneshot.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Nicolas on 28/10/2017.
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
