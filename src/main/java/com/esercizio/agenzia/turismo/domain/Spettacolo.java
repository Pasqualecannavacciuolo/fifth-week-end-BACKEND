package com.esercizio.agenzia.turismo.domain;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "spettacoli")
public class Spettacolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generating an auto-increment ID
    private Long id;

    private Cliente[] prenotazioni;
    private int n_prenotazione;
    private ArrayList<Cliente> attesa;

    public Spettacolo() {
        this.prenotazioni = new Cliente[n_prenotazione];
        this.attesa = new ArrayList<Cliente>();
    }

    public Spettacolo(int n) {
        this.n_prenotazione = n;
        this.prenotazioni = new Cliente[n];
        this.attesa = new ArrayList<Cliente>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente[] getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(Cliente[] prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    public int getN_prenotazione() {
        return n_prenotazione;
    }

    public void setN_prenotazione(int n_prenotazione) {
        this.n_prenotazione = n_prenotazione;
    }

    public ArrayList<Cliente> getAttesa() {
        return attesa;
    }

    public void setAttesa(ArrayList<Cliente> attesa) {
        this.attesa = attesa;
    }
}
