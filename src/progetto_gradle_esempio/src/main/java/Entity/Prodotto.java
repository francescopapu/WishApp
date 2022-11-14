package Entity;

public class Prodotto {
    private Integer Id;
    private String Nome;

    public Prodotto(Integer id, String nome) {
        Id = id;
        Nome = nome;
    }

    public Integer getId() {
        return Id;
    }

    public String getNome() {
        return Nome;
    }
}
