package br.com.verdemar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.verdemar.model.Contato;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long>{
    
}
