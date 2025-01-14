package br.com.alura.screenmatch.service;


import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class ConsultaGemini {
    public static String obterTraducao(String texto) {

        ChatLanguageModel gemini = GoogleAiGeminiChatModel.builder()
                .apiKey(System.getenv("GOOGLE_API_KEY"))
                .modelName("gemini-1.5-flash")
                .build();

        return gemini.generate("Traduza para português o texto: " + texto);
    }

    private ConsultaGemini() {
    }

}