package com.robintegg.j2html.generator.openai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
class OpenAIApiClient {

    private static final String endpoint = "https://api.openai.com/v1/completions";
    private final String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();

    public String completeText(String prompt) throws URISyntaxException, IOException, InterruptedException {

        // Set the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        /*
        {
          "model": "text-davinci-003",
          "prompt": "create a java method that converts the following html snippet into java code that return the html as j2html object and don't include any descriptive text\n\n<div class=\"field\">\n  <label class=\"label\">Label</label>\n  <div class=\"control\">\n    <input class=\"input\" type=\"text\" placeholder=\"Text input\">\n  </div>\n  <p class=\"help\">This is a help text</p>\n</div>\n\n\npublic static ContainerTag convertToJ2HTML() {\n  return div(\n    attrs(\".field\"),\n    label(\n      attrs(\".label\"),\n      txt(\"Label\")\n    ),\n    div(\n      attrs(\".control\"),\n      input(\n        attrs(\".input\"),\n        attrs(\"type=\\\"text\\\"\"),\n        attrs(\"placeholder=\\\"Text input\\\"\")\n      )\n    ),\n    p(\n      attrs(\".help\"),\n      txt(\"This is a help text\")\n    )\n  );\n}",
          "temperature": 1,
          "max_tokens": 256,
          "top_p": 1,
          "frequency_penalty": 0,
          "presence_penalty": 0
        }
         */

        // Set the request body
        ChatPrompt chatPrompt = new ChatPrompt();
        chatPrompt.setModel("text-davinci-003");
        chatPrompt.setPrompt("create a java method that converts the following html snippet into java code that return the html as j2html object and don't include any descriptive text\n\n<div class=\"field\">\n  <label class=\"label\">Label</label>\n  <div class=\"control\">\n    <input class=\"input\" type=\"text\" placeholder=\"Text input\">\n  </div>\n  <p class=\"help\">This is a help text</p>\n</div>");
        chatPrompt.setTemperature(1);
        chatPrompt.setMaxTokens(256);
        chatPrompt.setTopP(1);
        chatPrompt.setFrequencyPenalty(0);
        chatPrompt.setPresencePenalty(0);

        // Create the HttpEntity with headers and body
        HttpEntity<ChatPrompt> requestEntity = new HttpEntity<>(chatPrompt, headers);

        // Send the POST request
        ResponseEntity<ChatPromptResponse> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, requestEntity, ChatPromptResponse.class);

        log.debug("{}", responseEntity.getBody());

        return responseEntity.getBody().getChoices().stream().findFirst().map(Choice::getText).orElse("No response");

    }

}

