package com.alphateam.banqueenligne.Controller;

import com.alphateam.banqueenligne.Data_Access.Models.Users;
import com.alphateam.banqueenligne.Fileconfig.FileUploadUtil;
import com.alphateam.banqueenligne.Services.IusersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;


@RestController
public class UsersController {

    @Autowired
   private IusersServices userservice;
// get all users

    @GetMapping("/api/Users")
    @RolesAllowed("ROLE_ADMIN")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    private List<Users> getAllUsers()
    {
        return userservice.getUsers();
    }







    // get user by Id
    @GetMapping("/api/Users/{id}")
    private Users getoneUsers(@PathVariable Long id)
    {
        return userservice.getone(id);
    }






    // delete users
    @DeleteMapping("/api/Users/{id}")
    public String delete(@PathVariable int id)
    {
        userservice.deleteUsers(id);
         return "Users ciao !!!:";
    }

    // retreive image from server
    @GetMapping("/uploads/User/{filename}")
    public ResponseEntity getFile(@PathVariable("filename") String filename)
    {

        Resource file = FileUploadUtil.loadFile("USER",filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}
