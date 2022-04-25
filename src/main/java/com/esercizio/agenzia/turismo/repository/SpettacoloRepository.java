package com.esercizio.agenzia.turismo.repository;

import com.esercizio.agenzia.turismo.domain.Spettacolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpettacoloRepository extends JpaRepository<Spettacolo, Long> {
}
