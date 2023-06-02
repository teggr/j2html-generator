package com.robintegg.j2html.spring;

import com.robintegg.j2html.templates.J2HtmlTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnClass(J2HtmlTemplateEngine.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@Slf4j
public class J2HtmlAutoConfiguration implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

        log.debug("Registering view resolver");
        registry.viewResolver(j2HtmlViewResolver());

    }

    private ViewResolver j2HtmlViewResolver() {
        J2HtmlViewResolver j2HtmlViewResolver = new J2HtmlViewResolver();
        j2HtmlViewResolver.setTemplateEngine(getJ2HtmlTemplateEngine());
        return j2HtmlViewResolver;
    }

    private static J2HtmlTemplateEngine getJ2HtmlTemplateEngine() {
        J2HtmlTemplateEngine j2HtmlTemplateEngine = new J2HtmlTemplateEngine();
        j2HtmlTemplateEngine.setBasePackage("com.robintegg.j2html.generator.web.pages");
        return j2HtmlTemplateEngine;
    }

}
