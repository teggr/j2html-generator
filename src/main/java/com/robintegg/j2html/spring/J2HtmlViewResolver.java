package com.robintegg.j2html.spring;

import com.robintegg.j2html.templates.J2HtmlTemplateEngine;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import java.util.Locale;

public class J2HtmlViewResolver extends AbstractTemplateViewResolver {

    private J2HtmlTemplateEngine engine = null;

    public J2HtmlViewResolver() {
        setViewClass(requiredViewClass());
    }

    public void setTemplateEngine(J2HtmlTemplateEngine engine) {
        this.engine = engine;
    }

    @Override
    protected Class<?> requiredViewClass() {
        return J2HtmlView.class;
    }

    @Override
    protected AbstractUrlBasedView instantiateView() {
        return (getViewClass() == J2HtmlView.class ? new J2HtmlView() : super.instantiateView());
    }

    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        J2HtmlView view = (J2HtmlView) super.buildView(viewName);
        view.setTemplateEngine(engine);
        return view;
    }

    /**
     * This resolver supports i18n, so cache keys should contain the locale.
     */
    @Override
    protected Object getCacheKey(String viewName, Locale locale) {
        return viewName + '_' + locale;
    }

}
