package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.ConsultaGemini;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.OptionalDouble;

@Getter
@Setter
@ToString
public class Serie {

    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    private CategoriaEnun genero;
    private String atores;
    private String poster;
    private String sinopse;

    public Serie(DadosSerie dadosSerie) {
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.parseDouble(dadosSerie.avaliacao())).orElse(0.0);
        this.genero = CategoriaEnun.fromString(dadosSerie.genero().split(",")[0].trim());
        this.atores = dadosSerie.atores();
        this.poster = dadosSerie.poster();
        this.sinopse = ConsultaGemini.obterTraducao(dadosSerie.sinopse());
    }

}
