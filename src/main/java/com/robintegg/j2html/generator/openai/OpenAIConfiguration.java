package com.robintegg.j2html.generator.openai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OpenAIConfiguration {

    @Bean
    OpenAIApiClient openAIApiClient(@Value("${openai.api.key}") String apiKey) {
        return new OpenAIApiClient(apiKey);
    }

}
