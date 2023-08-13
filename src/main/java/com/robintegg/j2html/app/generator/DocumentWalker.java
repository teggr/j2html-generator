package com.robintegg.j2html.app.generator;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class DocumentWalker {

  public void walk(Document document, Handler h) {
    recursivelyWalkDocumentElements(document, h);
  }

  private static void recursivelyWalkDocumentElements(Element element, Handler h) {
    Elements children = element.children();
    int size = children.size();
    for (int i = 0; i < size; i++) {
      Element child = children.get(i);
      h.startElement(child);
      recursivelyWalkDocumentElements(child, h);
      h.endElement(child);
    }
  }

}
