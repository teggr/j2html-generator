package com.robintegg.j2html.app.generator;

import org.jsoup.nodes.Element;

class Handler {

  private StringBuilder b = new StringBuilder();

  private int indentation = 0;

  public void startElement(Element element) {
    String name = element.normalName();
    b.append(indentation() + name + "(\n");
    indentation = indentation + 2;
  }

  private String indentation() {
    String i = "";
    for (int j = 0; j < indentation; j++) {
      i += " ";
    }
    return i;
  }

  public void endElement(Element child) {
    indentation = indentation - 2;
    b.append( indentation() +  ")\n");
  }

  public String getJ2HtmlCode() {
    return b.toString();
  }

}
