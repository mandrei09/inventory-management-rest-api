package org.example.project.repository.security;

import org.example.project.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthorityRepository extends JpaRepository<Authority, Integer> {
}