package com.robintegg.j2html.spring;

import com.robintegg.j2html.templates.J2HtmlTemplate;
import com.robintegg.j2html.templates.J2HtmlTemplateEngine;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.AbstractTemplateView;

import java.io.BufferedWriter;
import java.util.Locale;
import java.util.Map;

@RequiredArgsConstructor
public class J2HtmlView extends AbstractTemplateView {

    private J2HtmlTemplateEngine engine;

    public void setTemplateEngine(J2HtmlTemplateEngine engine) {
        this.engine = engine;
    }

    @Override
    protected void initApplicationContext() throws BeansException {
        super.initApplicationContext();
        if (this.engine == null) {
            throw new BeanCreationException("No J2HtmlTemplateEngine set");
        }
    }

    @Override
    public boolean checkResource(Locale locale) throws Exception {
        Assert.state(this.engine != null, "No J2HtmlTemplateEngine set");
        try {
            this.engine.resolveTemplate(getUrl());
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String url = getUrl();
        Assert.state(url != null, "'url' not set");

        J2HtmlTemplate template = getTemplate(url);

        template.make(model)
                .writeTo(new BufferedWriter(response.getWriter()))
                .flush();
    }

    protected J2HtmlTemplate getTemplate(String viewUrl) throws Exception {
        Assert.state(this.engine != null, "No MarkupTemplateEngine set");
        try {
            return this.engine.createTemplateByPath(viewUrl);
        } catch (ClassNotFoundException ex) {
            Throwable cause = (ex.getCause() != null ? ex.getCause() : ex);
            throw new ServletException(
                    "Could not find class while rendering Groovy Markup view with name '" +
                            getUrl() + "': " + ex.getMessage() + "'", cause);
        }
    }
}
