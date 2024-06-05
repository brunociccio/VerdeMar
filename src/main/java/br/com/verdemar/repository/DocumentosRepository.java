package br.com.verdemar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.verdemar.model.Documentos;

@Repository
public interface DocumentosRepository extends JpaRepository<Documentos, Long> {
}

