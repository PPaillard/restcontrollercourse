package com.wcs.restcontrollercourse.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.wcs.restcontrollercourse.dto.ArticleDto;
import com.wcs.restcontrollercourse.model.Article;
import com.wcs.restcontrollercourse.repository.ArticleRepository;

@RestController
@RequestMapping("/articles")
public class ArticleController {
	
	@Autowired
	ArticleRepository articleRepository;

	// Get all articles
	@GetMapping
	public List<Article> getArticles(){
		return articleRepository.findAll();
	}
	
	// Get one article
	// http://localhost:8080/articles/13
	@GetMapping("/{id}")
	public Article getArticle(@PathVariable(required = true) Long id) {		
		/*
		try {
			return articleRepository.getById(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}*/
		
		Optional<Article> optArticle = articleRepository.findById(id);
		// Si mon objet optionnel contient bien un article, je le renvoi
		if(optArticle.isPresent()) {
			return optArticle.get();
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	
	// Create article
	@PostMapping
	public Article createArticle(@Valid @RequestBody ArticleDto articleDto) {
		Article article = new Article();
		article.setTitle(articleDto.getTitle());
		article.setContent(articleDto.getContent());
		article.setCreation(new Date());
		return articleRepository.save(article);
	}
	
	// Update article
	//http://localhost:8080/articles/{id}
	@PutMapping("/{id}")
	public Article updateArticle(@PathVariable(required = true) Long id, @Valid @RequestBody ArticleDto articleDto) {
		
		Optional<Article> optArticle = articleRepository.findById(id);
		// Si mon objet optionnel ne contient pas d'article, je renvoi une erreur
		if(optArticle.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		// On modifie l'entité
		Article article = optArticle.get();
		article.setTitle(articleDto.getTitle());
		article.setContent(articleDto.getContent());
		// on enregistre l'entité enregistrée en DB
		return articleRepository.save(article);
	}
	
	// Delete article
	//http://localhost:8080/articles/{id} 
	@DeleteMapping("/{id}")
	public void deleteArticle(@PathVariable(required = true) Long id){
		Boolean exist = articleRepository.existsById(id);
		// Si mon objet optionnel ne contient pas d'article, je renvoi une erreur
		if(!exist) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		articleRepository.deleteById(id);
	}
}
