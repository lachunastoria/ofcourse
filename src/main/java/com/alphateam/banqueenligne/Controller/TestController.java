package com.alphateam.banqueenligne.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/test")
public class TestController {


	@GetMapping("/all")
	public ResponseEntity<String> allAccess() {
		return ResponseEntity.ok( " welcome for everyBody !!!! ");
	}


	@GetMapping("/user")
	@PreAuthorize("hasRole('ROLE_USER')")
	public String userAccess() {
		return "welcome  User !!!!";
	}


	@GetMapping("/admin")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String adminAccess() {
		return "welcome Admin !!!!";
	}
}
