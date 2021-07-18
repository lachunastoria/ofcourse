package com.alphateam.banqueenligne.Data_Access.DAO;

import com.alphateam.banqueenligne.Data_Access.Models.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgenceRepositery extends JpaRepository<Agence, String> {


}
