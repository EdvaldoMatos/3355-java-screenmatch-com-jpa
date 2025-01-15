package br.com.alura.screenmatch.model;

public enum CategoriaEnun {
    ACAO("Action", "Ação"),
    AVENTURA("Adventure", "Aventura"),
    COMEDIA("Comedy", "Comédia"),
    DRAMA("Drama", "Drama"),
    FANTASIA("Fantasy", "Fantasia"),
    FICCAO("Sci-Fi","Ficção Científica"),
    MUSICAL("Musical", "Musical"),
    SUSPENSE("Thriller", "Suspense"),
    TERROR("Horror", "Terror"),
    ROMANCE("Romance", "Romance"),
    CRIME("Crime", "Crime"),;

    private String categoriaOmdb;
    private String categoriaPortugues;

    CategoriaEnun(String categoriaOmdb, String categoriaPortugues) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPortugues = categoriaPortugues;
    }

    public static CategoriaEnun fromString(String categoriaOmdb) {
        for (CategoriaEnun categoria : CategoriaEnun.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(categoriaOmdb)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Categoria não encontrada");
    }
    public static CategoriaEnun fromPortugues(String categoriaPortugues) {
        for (CategoriaEnun categoria : CategoriaEnun.values()) {
            if (categoria.categoriaPortugues.equalsIgnoreCase(categoriaPortugues)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Categoria não encontrada");
    }
}
