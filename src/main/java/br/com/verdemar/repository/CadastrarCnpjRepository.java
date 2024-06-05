package br.com.verdemar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.verdemar.model.CadastrarCnpj;

@Repository
public interface CadastrarCnpjRepository extends JpaRepository<CadastrarCnpj, Long>{
    
}
