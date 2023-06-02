package com.robintegg.j2html.templates;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public interface J2HtmlTemplate {
    Writer make(Map<String, Object> model);

}
