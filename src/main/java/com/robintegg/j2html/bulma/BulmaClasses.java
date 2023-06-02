package com.robintegg.j2html.bulma;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BulmaClasses {
    public static final String container = "container";

    public static String classes(String... classNames) {
        return Stream.of(classNames).collect(Collectors.joining(" "));
    }

}
