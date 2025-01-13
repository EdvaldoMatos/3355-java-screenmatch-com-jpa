package br.com.alura.screenmatch.service;


import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class ConsultaGemini {
    private static final String TOKEN_GOOGLE = "Chave";

    public static String obterTraducao(String texto) {


        ChatLanguageModel gemini = GoogleAiGeminiChatModel.builder()
                .apiKey(TOKEN_GOOGLE)
                .modelName("gemini-1.5-flash")
                .build();

        return gemini.generate("Traduza para português o texto: " + texto);
    }
    private ConsultaGemini() {
    }

}