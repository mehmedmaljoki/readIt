package com.mehmedmaljoki.readit.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByNameAndEmail(String name, String email);
}
