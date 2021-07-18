package com.alphateam.banqueenligne.Data_Access.Models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Agence {

    @Id
    private String CodeAgence;
    private String Adresse;

}