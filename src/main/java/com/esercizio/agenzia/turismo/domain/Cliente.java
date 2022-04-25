package com.esercizio.agenzia.turismo.domain;

import java.io.Serializable;

public class Cliente implements Serializable {
    private String nominativo;
    private String numero_telefonico;

    public Cliente(){}

    public Cliente(String nominativo, String numero_telefonico) {
        this.nominativo = nominativo;
        this.numero_telefonico = numero_telefonico;
    }

    public String getNominativo() {
        return nominativo;
    }

    public void setNominativo(String nominativo) {
        this.nominativo = nominativo;
    }

    public String getNumero_telefonico() {
        return numero_telefonico;
    }

    public void setNumero_telefonico(String numero_telefonico) {
        this.numero_telefonico = numero_telefonico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cliente cliente = (Cliente) o;

        if (nominativo != null ? !nominativo.equals(cliente.nominativo) : cliente.nominativo != null) return false;
        return numero_telefonico != null ? numero_telefonico.equals(cliente.numero_telefonico) : cliente.numero_telefonico == null;
    }
}
