package br.com.verdemar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.verdemar.model.LoginApple;

@Repository
public interface LoginAppleRepository extends JpaRepository<LoginApple, Long> {
    
}
