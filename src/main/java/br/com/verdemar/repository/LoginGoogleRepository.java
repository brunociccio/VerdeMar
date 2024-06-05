package br.com.verdemar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.verdemar.model.LoginGoogle;

@Repository
public interface LoginGoogleRepository extends JpaRepository<LoginGoogle, Long> {
    
}
