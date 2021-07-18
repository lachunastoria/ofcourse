package com.alphateam.banqueenligne.Services;

import com.alphateam.banqueenligne.Data_Access.DAO.UserRepository;
import com.alphateam.banqueenligne.Data_Access.Models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServices implements  IusersServices{
    @Autowired
    private UserRepository userrepo;

    @Override
    public Optional<Users> findByUsername(String username) {
     return   userrepo.findByUsername(username);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userrepo.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userrepo.existsByEmail(email);
    }

    @Override
    public Users saveUser(Users user) {
        return userrepo.save(user);
    }

    @Override
    public List<Users> getUsers() {
        return userrepo.findAll();
    }

    @Override
    public Users getone(long id) {
        return userrepo.findById((long) id).get();
    }

    @Override
    public void deleteUsers(long id) {
        userrepo.deleteById(id);
    }
}
