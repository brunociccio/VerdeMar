package br.com.verdemar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.verdemar.model.RelatorioPoluicao;

@Repository
public interface RelatorioPoluicaoRepository extends JpaRepository<RelatorioPoluicao, Long> {
    
}
