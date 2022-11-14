package Entity;

public class Partecipazione {
    private Integer Id;
    private Boolean UtenteProprietario;
    private Gruppo GruppoAssociato;
    private Utente UtenteAssociato;

    public Partecipazione(Integer id, Boolean utenteProprietario, Gruppo gruppoAssociato, Utente utenteAssociato) {
        Id = id;
        UtenteProprietario = utenteProprietario;
        GruppoAssociato = gruppoAssociato;
        UtenteAssociato = utenteAssociato;
    }

    public Integer getId() {
        return Id;
    }

    public Boolean getUtenteProprietario() {
        return UtenteProprietario;
    }

    public Gruppo getGruppoAssociato() {
        return GruppoAssociato;
    }

    public Utente getUtenteAssociato() {
        return UtenteAssociato;
    }
}
