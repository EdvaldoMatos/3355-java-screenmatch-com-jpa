package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.ConsultaGemini;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Getter
@Setter
@ToString
@Entity
@Table(name = "series")
@NoArgsConstructor
public class Serie {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 150)
    private String titulo;

    @Column(name = "total_temporadas")
    private Integer totalTemporadas;

    @Column(name = "avaliacao", precision = 1)
    private Double avaliacao;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private CategoriaEnun genero;

    @Column(length = 100)
    private String atores;

    @Column(length = 150)
    private String poster;

    @Column(length = 2000)
    private String sinopse;

    @Transient
    //@OneToMany(mappedBy = "serie")
    private List<Episodio> episodios = new ArrayList<>();

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
