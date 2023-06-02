package com.robintegg.j2html.templates;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class J2HtmlTemplateEngine {

    private String basePackage = "";

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void resolveTemplate(String url) {
        try {
            resolveClassForUrl(url);
        } catch(ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Class<?> resolveClassForUrl(String url) throws ClassNotFoundException {
        String className = basePackage + "." + url;
        log.debug("url={},className={}", url, className);
        return Class.forName(className);
    }

    @SneakyThrows
    public J2HtmlTemplate createTemplateByPath(String viewUrl) throws ClassNotFoundException {
        return ((J2HtmlTemplate) resolveClassForUrl(viewUrl)
                .getDeclaredConstructor().newInstance());
    }

}
