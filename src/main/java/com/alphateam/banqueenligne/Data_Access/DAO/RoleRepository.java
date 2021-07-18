package com.alphateam.banqueenligne.Data_Access.DAO;


import com.alphateam.banqueenligne.Data_Access.Models.ERole;
import com.alphateam.banqueenligne.Data_Access.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
