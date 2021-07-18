package com.alphateam.banqueenligne.Data_Access.DAO;


import com.alphateam.banqueenligne.Data_Access.Models.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<Users, Long> {



	Optional<Users> findByUsername(String username);

    Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);



}
