package Entity;

import java.util.ArrayList;

public class Gruppo {
    private Integer Id;
    private String Nome;
    private ArrayList<Partecipazione> Partecipazione;
    private ArrayList<Lista> ListeCondivise;

    public Gruppo(Integer id, String nome) {
        Id = id;
        Nome = nome;
    }

    public Integer getId() {
        return Id;
    }

    public String getNome() {
        return Nome;
    }

    public void setPartecipazione(ArrayList<Entity.Partecipazione> partecipazione) {
        Partecipazione = partecipazione;
    }

    public void setListeCondivise(ArrayList<Lista> listeCondivise) {
        ListeCondivise = listeCondivise;
    }

    public ArrayList<Lista> getListeCondivise() {
        return ListeCondivise;
    }

    public ArrayList<Partecipazione> getPartecipazione() {
        return Partecipazione;
    }
}
