package com.robintegg.j2html.generator.openai;

import com.robintegg.j2html.app.generator.J2HtmlGenerator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OpenAIJ2HtmlGenerator implements J2HtmlGenerator {

    private final OpenAIApiClient openAIApiClient;

    @SneakyThrows
    @Override
    public String generateFromHtml(String htmlText) {

        return openAIApiClient.completeText(
                """
                convert the following html into j2html. return a j2html object that can be assigned to a variable and don't include any descriptive text
                
                %s
                """
                .formatted(htmlText)
        );
    }

}
