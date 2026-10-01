package com.upeu.pharmabackend.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
    Optional<Cliente> findByDni(String dni);
    Optional<Cliente> findByEmail(String email);
}
