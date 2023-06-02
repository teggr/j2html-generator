package com.robintegg.j2html.generator.web.controllers;

import com.robintegg.j2html.generator.generator.J2HtmlGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
class HomeController {

    private final J2HtmlGenerator j2HtmlGenerator;

    @GetMapping("/")
    public String get() {
        return "HomePage";
    }

    @PostMapping("/generate")
    public String generate(@RequestParam("html") String html, Model model) {
        model.addAttribute("j2html", j2HtmlGenerator.generateFromHtml(html));
        return "J2HtmlResponse";
    }

}