package com.alphateam.banqueenligne.Controller;
import com.alphateam.banqueenligne.DTO.request.LoginRequest;
import com.alphateam.banqueenligne.DTO.request.SignupRequest;
import com.alphateam.banqueenligne.DTO.response.JwtResponse;
import com.alphateam.banqueenligne.DTO.response.MessageResponse;
import com.alphateam.banqueenligne.Data_Access.DAO.AgenceRepositery;
import com.alphateam.banqueenligne.Data_Access.DAO.RoleRepository;
import com.alphateam.banqueenligne.Data_Access.Models.Agence;
import com.alphateam.banqueenligne.Data_Access.Models.ERole;
import com.alphateam.banqueenligne.Data_Access.Models.Role;
import com.alphateam.banqueenligne.Data_Access.Models.Users;
import com.alphateam.banqueenligne.Fileconfig.FileUploadUtil;
import com.alphateam.banqueenligne.Services.IusersServices;
import com.alphateam.banqueenligne.security.jwt.JwtUtils;
import com.alphateam.banqueenligne.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	IusersServices userservice;

	@Autowired
	AgenceRepositery agencerepo;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Value( "${file.upload-dir}" )
	private String uploaddir;


	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping(value = "/signup",consumes =
			{"multipart/form-data","application/json"})
	public ResponseEntity<?> registerUser( SignupRequest signUpRequest,
										  @RequestParam("image") MultipartFile multipartFile) {
		if (userservice.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ooooooooooooo: Username is already taken!"));
		}

		if (userservice.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse(" Email is already in use!"));
		}

		Users user = new Users(signUpRequest.getUsername(),
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> Rolestable = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (Rolestable == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("oooooooooo: Role is not found."));
			roles.add(userRole);
		}else {
			Rolestable.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException(" Role is not found."));
					roles.add(adminRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException(" Role is not found."));
					roles.add(userRole);
				}
			});
		}
       // set user roles
		user.setRoles(roles);

		try {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	     	user.setPhotos(fileName);


			String uploadDir = uploaddir+"/USER";

			try {
				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			} catch (IOException e) {
				e.printStackTrace();
			}


		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("data", e.getMessage()));

		}

	  Agence a=	agencerepo.findById(signUpRequest.getAgence()).get();
         user.setAgence(a);
		userservice.saveUser(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}









}
