package com.example.restful.controller

import com.example.restful.repository.ArticleRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/articles")
class ArticleController(private val articleRepository: ArticleRepository) {
    @GetMapping("")
    fun index(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(articleRepository.findAll())
        } catch (error: Exception) {
            ResponseEntity.status(500).body(error.message)
        }
    }
}