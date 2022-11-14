package Entity;

import java.util.ArrayList;

public class Lista {
    private Integer Id;
    private String Nome;
    private ArrayList<Prodotto> Prodotti;
    private Utente Proprietario;

    public Lista(Integer id, String nome) {
        Id = id;
        Nome = nome;
    }

    public Lista(Integer id, String nome, ArrayList<Prodotto> prodotti) {
        Id = id;
        Nome = nome;
        Prodotti = prodotti;
    }

    public Lista(Integer id, String nome, Utente proprietario) {
        Id = id;
        Nome = nome;
        Proprietario = proprietario;
    }

    public Integer getId() {
        return Id;
    }

    public String getNome() {
        return Nome;
    }

    public ArrayList<Prodotto> getProdotti() {
        return Prodotti;
    }

    public Utente getProprietario() {
        return Proprietario;
    }

    public void setProprietario(Utente proprietario) {
        Proprietario = proprietario;
    }
}
