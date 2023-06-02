package com.robintegg.j2html.generator.openai;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatPrompt {

    private String model;
    private String prompt;
    private Integer temperature;

    @JsonProperty("max_tokens")
    private Integer maxTokens;
    @JsonProperty("top_p")
    private Integer topP;
    @JsonProperty("frequency_penalty")
    private Integer frequencyPenalty;
    @JsonProperty("presence_penalty")
    private Integer presencePenalty;

}
