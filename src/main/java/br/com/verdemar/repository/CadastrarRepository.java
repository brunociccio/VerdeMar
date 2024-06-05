package br.com.verdemar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.verdemar.model.Cadastrar;

@Repository
public interface CadastrarRepository extends JpaRepository<Cadastrar, Long> {
}

