package com.wcs.restcontrollercourse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wcs.restcontrollercourse.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	
	List<Article> findByTitleContainingOrContentContaining(String title, String content);

}
