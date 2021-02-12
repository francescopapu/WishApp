DELETE FROM partecipazione AS par WHERE par.GruppoID = '5' AND par.UtenteID = '6';

CREATE TRIGGER after_delete_partecipazione
AFTER DELETE
ON partecipazione FOR EACH ROW
DELETE FROM gruppo_listadesideri AS gl WHERE gl.GruppoID = OLD.GruppoID
AND EXISTS(SELECT * FROM listadesideri AS lista WHERE gl.ListaDesideriID = lista.ID AND lista.UtenteID = OLD.UtenteID);