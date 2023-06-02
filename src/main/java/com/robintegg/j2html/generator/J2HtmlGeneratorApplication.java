package com.robintegg.j2html.generator;

import com.robintegg.j2html.spring.J2HtmlAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ImportAutoConfiguration(classes = J2HtmlAutoConfiguration.class)
public class J2HtmlGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(J2HtmlGeneratorApplication.class, args);
    }

}