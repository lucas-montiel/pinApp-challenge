package com.pinApp.microservicio.repository;

import com.pinApp.microservicio.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Integer > {
}
