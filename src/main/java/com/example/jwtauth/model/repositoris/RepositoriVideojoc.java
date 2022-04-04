package com.example.jwtauth.model.repositoris;

import com.example.jwtauth.model.entitats.Videojoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoriVideojoc extends JpaRepository<Videojoc, Long> {
}