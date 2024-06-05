package br.com.verdemar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.verdemar.model.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
    
}
