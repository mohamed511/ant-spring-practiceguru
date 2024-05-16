package com.ant.springpracticeguru.controller;

import com.ant.springpracticeguru.service.AuthorService;
import com.ant.springpracticeguru.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping("/authors")
    public String getAuthor(Model model) {
        model.addAttribute("authors", this.authorService.findAll());
        return "authors";
    }
}
