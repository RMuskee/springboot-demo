package demo.springboot.controller;

import demo.springboot.model.Article;
import demo.springboot.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles")
    public String root(Model model) {
        List<Article> articles = (List<Article>) articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "/articles/index";
    }

    @DeleteMapping("/articles/{id}")
    public String deleteArticle(@PathVariable long id) {
        articleRepository.deleteById(id);
        return "/articles/index";
    }
}
