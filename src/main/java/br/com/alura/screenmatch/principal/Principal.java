package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.CategoriaEnun;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Slf4j
public class Principal {


    private SerieRepository serieRepository;
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private static final String ENDERECO = "https://www.omdbapi.com/?t=";
    private static final String API_KEY = "&apikey=6585022c";
    private List<Serie> series = new ArrayList<>();
    private Optional<Serie> serieBusca;


    public Principal(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;

    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios                    
                    3 - Listar Séries Listadas
                    4 - Buscar Série por Título
                    5 - Buscar Séries por Ator
                    6 - Top 5 Séries
                    7 - Buscar Séries por Gênero
                    8 - Buscar Séries por Temporada e Avaliação
                    9 - Buscar Episódio por trecho
                    10 - Top 5 Episódios por Série
                    11 - Buscar Episódio por data de lançamento
                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();

            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarPorGenero();
                    break;
                case 8:
                    buscarSeriePorTemporadaEAvaliacao();
                    break;
                case 9:
                    buscarEpisodioPorTrecho();
                    break;
                case 10:
                    topEpisodiosPorSerie();
                    break;
                case 11:
                    buscarEpisodioDepoisDeUmaData();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarEpisodioDepoisDeUmaData() {
        buscarSeriePorTitulo();
        serieBusca.ifPresent(s -> {
            System.out.println("Digite a data para busca");
            var anoLancamento = leitura.nextInt();
            leitura.nextLine();
            List<Episodio> episodios = serieRepository.episodiosPorSerieEAno(s, anoLancamento);

            episodios.forEach(e -> System.out.printf("Serie: %s - temporada: %s - Titulo do episodio: %s - Avaliacao: %s - Ano: %s%n",
                    e.getSerie().getTitulo(), e.getTemporada(), e.getTitulo(), e.getAvaliacao(), e.getDataLancamento()));
        });
    }

    private void topEpisodiosPorSerie() {
        buscarSeriePorTitulo();
        serieBusca.ifPresent(s -> {
            List<Episodio> topEpisodios = serieRepository.topEpisodiosPorSerie(s);
            topEpisodios.forEach(e -> System.out.printf("Serie: %s - temporada: %s - Titulo do episodio: %s - Avaliacao: %s%n",
                    e.getSerie().getTitulo(), e.getTemporada(), e.getTitulo(), e.getAvaliacao()));
        });

    }

    private void buscarEpisodioPorTrecho() {
        System.out.println("Digite o trecho do episodio para busca");
        var trecho = leitura.nextLine();
        var episodios = serieRepository.episodiosPorTecho(trecho);
        episodios.forEach(e -> System.out.printf("Serie: %s - temporada: %s - Titulo do episodio: %s%n",
                e.getSerie().getTitulo(), e.getTemporada(), e.getTitulo()));
    }

    private void buscarSeriePorTemporadaEAvaliacao() {
        System.out.println("Digite a temporada para busca");
        var temporada = leitura.nextInt();
        System.out.println("Digite a avaliação mínima");
        var avaliacao = leitura.nextDouble();
        var series = serieRepository.seriesPorTemporadaEAvaliacao(temporada, avaliacao);
        System.out.println("Séries encontradas com a temporada: " + temporada + " e avaliação maior ou igual a " + avaliacao);
        series.forEach(s -> System.out.printf(" %s - avaliacao: %s%n", s.getTitulo(), s.getAvaliacao()));
    }

    private void buscarPorGenero() {
        System.out.println("Digite o gêner/categoria para busca");
        var genero = leitura.nextLine();
        var series = serieRepository.findByGeneroOrderByAvaliacaoDesc(CategoriaEnun.fromPortugues(genero));
        System.out.println("Séries encontradas com o gênero: " + genero);
        series.forEach(s -> System.out.printf(" %s - avaliacao: %s%n", s.getTitulo(), s.getAvaliacao()));
        System.out.println("\n");
    }

    private void buscarTop5Series() {
        var top5 = serieRepository.findTop5ByOrderByAvaliacaoDesc();
        System.out.println("Top 5 Séries");
        top5.forEach(serie -> System.out.printf(" %s - avaliacao: %s%n", serie.getTitulo(), serie.getAvaliacao()));
        System.out.println("\n");
    }

    private void buscarSeriePorAtor() {
        System.out.println("Digite o nome do ator para busca");
        var nomeAtor = leitura.nextLine();
        System.out.println("Digite a avaliação mínima");
        var avaliacao = leitura.nextDouble();
        var series = serieRepository.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);
        System.out.println("Séries encontradas com o ator: " + nomeAtor);
        series.forEach(s -> System.out.printf(" %s - avaliacao: %s%n", s.getTitulo(), s.getAvaliacao()));
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var serie = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);
        serie.ifPresentOrElse(s -> {
            System.out.println(s);
            this.serieBusca = serie;
        }, () -> System.out.println("Série não encontrada"));
    }

    private void listarSeriesBuscadas() {
        this.series = serieRepository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        var serieSalvo = serieRepository.save(serie);
        System.out.println(serieSalvo);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        return conversor.obterDados(json, DadosSerie.class);

    }

    private void buscarEpisodioPorSerie() {
        listarSeriesBuscadas();
        System.out.println("Escolha a série para buscar episódios");
        var nomeSerie = leitura.nextLine();


        Optional<Serie> serie = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(t -> log.info(t.toString()));
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios()
                            .stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .toList();
            serieEncontrada.setEpisodios(episodios);
            serieRepository.save(serieEncontrada);
        } else {
            System.out.println("Série não encontrada");
        }
    }
}