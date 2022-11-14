package Entity;

import java.util.ArrayList;

public class Utente {
    private Integer Id;
    private String Nome;
    private String Cognome;
    private ArrayList<Partecipazione> Partecipazione;
    private ArrayList<Lista> ListeDesideri;

    public Utente(Integer id) {
        Id = id;
    }

    public Utente(Integer id, String nome, String cognome) {
        Id = id;
        Nome = nome;
        Cognome = cognome;
    }

    public Integer getId() {
        return Id;
    }

    public String getNome() {
        return Nome;
    }

    public String getCognome() {
        return Cognome;
    }

    public void setPartecipazione(ArrayList<Entity.Partecipazione> partecipazione) {
        Partecipazione = partecipazione;
    }

    public void addPartecipazione(Partecipazione partecipazione){
        Partecipazione.add(partecipazione);
    }

    public void addLista(Lista lista){
        ListeDesideri.add(lista);
    }

    // Overriding equals() to compare two Complex objects
    @Override
    public boolean equals(Object o) {
        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Utente)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Utente u2 = (Utente) o;

        // Compare the data members and return accordingly
        return this.Id == u2.Id;
    }

    @Override
    public int hashCode() {
        return Id.hashCode();
    }
}
