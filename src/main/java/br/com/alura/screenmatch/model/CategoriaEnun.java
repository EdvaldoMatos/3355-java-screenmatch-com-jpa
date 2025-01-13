package br.com.alura.screenmatch.model;

public enum CategoriaEnun {
    ACAO("Action"),
    AVENTURA("Adventure"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    FANTASIA("Fantasy"),
    FICCAO("Sci-Fi"),
    MUSICAL("Musical"),
    SUSPENSE("Thriller"),
    TERROR("Horror"),
    ROMANCE("Romance"),
    CRIME("Crime");

    private String categoriaOmdb;

    CategoriaEnun(String categoriaOmdb) {
        this.categoriaOmdb = categoriaOmdb;
    }

    public static CategoriaEnun fromString(String categoriaOmdb) {
        for (CategoriaEnun categoria : CategoriaEnun.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(categoriaOmdb)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Categoria não encontrada");
    }
}
