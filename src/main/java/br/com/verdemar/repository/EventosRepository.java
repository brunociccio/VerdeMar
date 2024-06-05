package br.com.verdemar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.verdemar.model.Eventos;

@Repository
public interface EventosRepository extends JpaRepository<Eventos, Long> {
    
}
