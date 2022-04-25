package com.esercizio.agenzia.turismo.service;

import com.esercizio.agenzia.turismo.domain.Cliente;
import com.esercizio.agenzia.turismo.domain.Spettacolo;
import com.esercizio.agenzia.turismo.repository.SpettacoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SpettacoloService {

    @Autowired
    SpettacoloRepository spettacoloRepository;

    /**
     * Find a spettacolo by a given ID
     * @param id -> the ID of the Spettacolo to search
     * @return -> the founded Spettacolo
     */
    public Spettacolo findById(Long id) {
        return spettacoloRepository.findById(id).orElse(null);
    }

    /**
     * Finding all the Spettacoli in the database
     * @return -> a List of all the Spettacoli
     */
    public List<Spettacolo> findAll() {
        return spettacoloRepository.findAll();
    }

    /**
     * Adding a Spettacolo to the database
     * @param n_prenotazioni -> the number of prenotazioni for that Spettacolo
     * @return -> the new entity added
     */
    public Spettacolo saveOne(int n_prenotazioni) {
        Spettacolo spettacolo = new Spettacolo(n_prenotazioni);
        return spettacoloRepository.save(spettacolo);
    }

    /**
     * This method chef there is at least 1 empty space in the array prenotazioni
     * @param id -> the id of the Spettacolo
     * @return -> TRUE if there is at least 1 empty space in the Spettacolo selected by ID
     */
    public boolean libero(Long id){
        Spettacolo spettacolo = this.findById(id);
        Cliente[] array_prenotazioni = spettacolo.getPrenotazioni();

        int at_least_one = 0;
        for(int i=0; i<array_prenotazioni.length; i++) {
            if(spettacolo.getPrenotazioni()[i] == null){
                at_least_one += 1;
            }
        }
        if(at_least_one==0){
            return false;
        }
        return true;
    }


    /**
     *
     * @param id -> the id of the Spettacolo to find
     * @param nome -> name of the Client to find
     * @param tel -> telephone number of the Client to find
     * @return ->
     *              [0] if we find the client in the array Prenotazioni
     *              [1] if we find the client in the ArrayList Attese
     *              [-1] if we didn't found the client
     */
    public int trova(Long id, String nome, String tel){
        Spettacolo spettacolo = this.findById(id);

        // Finding Cliente in Array prenotazioni
        for(int i=0; i<spettacolo.getN_prenotazione(); i++) {
            if(spettacolo.getPrenotazioni()[i].getNominativo().equals(nome) && spettacolo.getPrenotazioni()[i].getNumero_telefonico().equals(tel)) {
                return 0;
            }
        }

        // Finding Cliente in ArrayList attesa
        for(int i=0; i<spettacolo.getAttesa().size(); i++) {
            if(spettacolo.getAttesa().get(i).getNominativo().equals(nome) && spettacolo.getAttesa().get(i).getNumero_telefonico().equals(tel)) {
                return 1;
            }
        }

        return -1;
    }


    /**
     * This method add a client to the Prenotazione array if there is an empty space otherwise adds the client to the ArrayList Attese
     * @param id -> the id of the Spettacolo to find
     * @param nome -> name of the Client to find
     * @param tel -> telephone number of the Client to find
     */
    public void prenota(Long id, String nome, String tel){
        Spettacolo spettacolo = this.findById(id);
        Cliente cliente = new Cliente(nome, tel);

        Cliente[] array_prenotazioni = spettacolo.getPrenotazioni();
        ArrayList<Cliente> lista_attesa = spettacolo.getAttesa();

        if(libero(id)) { // If prenotazione have at least 1 position empty add to PRENOTAZIONI
            for(int i=0; i<spettacolo.getN_prenotazione(); i++) {
                if(array_prenotazioni[i] == null) {
                    System.out.println(array_prenotazioni[i]);
                    array_prenotazioni[i]=cliente;
                    spettacolo.setPrenotazioni(array_prenotazioni);
                    spettacoloRepository.save(spettacolo);
                    return;
                }
            }
        } else { // If prenotazione is FULL add to ATTESA
            lista_attesa.add(cliente);
            spettacolo.setAttesa(lista_attesa);
            spettacoloRepository.save(spettacolo);
        }
    }


    /**
     * This method search a client if finds the client in one of the two list deelte it and add the first client in the Attese to the Prenotazioni
     * @param id -> the id of the Spettacolo to find
     * @param nome -> name of the Client to find
     * @param tel -> telephone number of the Client to find
     */
    public void disdici(Long id, String nome, String tel){
        Spettacolo spettacolo = this.findById(id);
        Cliente cliente = new Cliente(nome, tel);

        int clienteTrovato = trova(id, nome, tel);

        Cliente[] array_prenotazioni = spettacolo.getPrenotazioni();
        ArrayList<Cliente> lista_attesa = spettacolo.getAttesa();

        for(int i=0; i<spettacolo.getN_prenotazione(); i++) { // Removing from PRENOTAZIONI
            if(clienteTrovato==0) {
                array_prenotazioni[i] = null;
                spettacoloRepository.save(spettacolo);
                break;
            } else { // Removing from ATTESE
                for(int j=0; j<lista_attesa.size(); j++) {
                    if(clienteTrovato==1) {
                        lista_attesa.remove(i);
                        spettacoloRepository.save(spettacolo);
                        return;
                    }
                }
            }
        } // Fine remove

        // Adding the first client that is waiting to the prenotazione array if there is an empty space
        Cliente clienteDaSpostare = lista_attesa.get(0);
        if(libero(id)) {
            for(int i=0; i<spettacolo.getN_prenotazione(); i++) {
                if(array_prenotazioni[i] == null) { // If an empty space is found in prenotazioni add the client
                    array_prenotazioni[i]=clienteDaSpostare;
                    spettacolo.setPrenotazioni(array_prenotazioni);
                    lista_attesa.remove(clienteDaSpostare);
                    spettacolo.setAttesa(lista_attesa);
                    spettacoloRepository.save(spettacolo);
                    return;
                }
            }
        }// Fine adding the first client that is in the ArrayList Attesa


    }


    public boolean incompleto(Long id){
        Spettacolo spettacolo = this.findById(id);

        for(int i=0; i<spettacolo.getPrenotazioni().length; i++) {
            String nominativo = spettacolo.getPrenotazioni()[i].getNominativo();
            String numero_telefonico = spettacolo.getPrenotazioni()[i].getNumero_telefonico();
            for (int j = 0; j < spettacolo.getAttesa().size(); j++) {
                if (Objects.equals(nominativo, spettacolo.getAttesa().get(j).getNominativo())
                        && Objects.equals(numero_telefonico, spettacolo.getAttesa().get(j).getNumero_telefonico())) {
                    return true;
                }
            }
        }
        return false;
    }
}
