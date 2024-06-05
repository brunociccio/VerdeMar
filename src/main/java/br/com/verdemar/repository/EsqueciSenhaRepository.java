package br.com.verdemar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.verdemar.model.EsqueciSenha;

@Repository
public interface EsqueciSenhaRepository extends JpaRepository<EsqueciSenha, Long> {
    
}
