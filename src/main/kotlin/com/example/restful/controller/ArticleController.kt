package com.example.restful.controller

import com.example.restful.model.Article
import com.example.restful.repository.ArticleRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/articles")
class ArticleController(private val articleRepository: ArticleRepository) {
    @GetMapping("")
    fun index(): ResponseEntity<MutableList<Article>> {
        return ResponseEntity.ok().body(articleRepository.findAll())
    }

    @PostMapping("")
    fun create(@Valid @RequestBody article: Article): Article = articleRepository.save(article)

    @GetMapping("/{id}")
    fun getById(@PathVariable(value = "id") articleId: Long): ResponseEntity<Article> {
        return articleRepository.findById(articleId).map { article ->
            ResponseEntity.ok().body(article)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/{id}")
    fun update(@PathVariable(value = "id") articleId: Long, @Valid @RequestBody newArticle: Article): ResponseEntity<Article> {
        return articleRepository.findById(articleId).map { existingArticle ->
            val updatedArticle: Article = existingArticle.copy(title = newArticle.title, content = newArticle.content)
            ResponseEntity.ok().body(articleRepository.save(updatedArticle))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable(value = "id") articleId: Long): ResponseEntity<Void> {
        return articleRepository.findById(articleId).map { article ->
            articleRepository.delete(article)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
}