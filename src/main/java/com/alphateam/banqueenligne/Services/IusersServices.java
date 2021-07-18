package com.alphateam.banqueenligne.Services;

import com.alphateam.banqueenligne.Data_Access.Models.Users;


import java.util.List;
import java.util.Optional;

public interface IusersServices {


      Optional<Users> findByUsername(String username);

     Boolean existsByUsername(String username);

      Boolean existsByEmail(String email);
      Users saveUser(Users user);
     List<Users> getUsers( );
     Users getone(long id);
     void deleteUsers(long id);


}
