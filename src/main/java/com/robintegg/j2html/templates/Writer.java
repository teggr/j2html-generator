package com.robintegg.j2html.templates;

import java.io.IOException;

public interface Writer {

    java.io.Writer writeTo(java.io.Writer out)
            throws IOException;

}
