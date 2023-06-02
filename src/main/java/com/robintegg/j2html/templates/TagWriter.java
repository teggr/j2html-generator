package com.robintegg.j2html.templates;

import j2html.tags.Tag;

import java.io.IOException;
import java.io.PrintWriter;

public class TagWriter implements Writer {

    private final Tag tag;

    public TagWriter(Tag tag) {
        this.tag = tag;
    }

    @Override
    public java.io.Writer writeTo(java.io.Writer out) throws IOException {
        PrintWriter pw = new PrintWriter(out);
        pw.write(tag.render());
        pw.flush();
        return out;
    }
}
