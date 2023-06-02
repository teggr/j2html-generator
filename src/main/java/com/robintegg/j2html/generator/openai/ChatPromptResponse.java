package com.robintegg.j2html.generator.openai;

import lombok.Data;

import java.util.List;

@Data
public class ChatPromptResponse {

    private List<Choice> choices;

}
