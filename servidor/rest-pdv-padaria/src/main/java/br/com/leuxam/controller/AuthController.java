package br.com.leuxam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.leuxam.data.vo.v1.security.AccountCredentialsVO;
import br.com.leuxam.services.AuthServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(name = "/auth")
@Tag(name = "Autenticação end point")
public class AuthController {
	
	@Autowired
	AuthServices services;
	
	@SuppressWarnings("rawtypes")
	@Operation(summary = "Login de Usuarios")
	@PostMapping(value = "/signin")
	public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
		if (checkIfParamIsNotNull(data)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalida requisição de Cliente");
		var token = services.signin(data);
		if(token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalida requisição de Cliente");
		return token;
	}

	private boolean checkIfParamIsNotNull(AccountCredentialsVO data) {
		return data == null || data.getUsername() == null || data.getUsername().isBlank()
				|| data.getPassword() == null || data.getPassword().isBlank();
	}
	
	@SuppressWarnings("rawtypes")
	@Operation(summary = "Refresh token para autenticação de Usuarios")
	@PutMapping(value = "/refresh/{username}")
	public ResponseEntity refreshToken(@PathVariable("username") String username) {
		if (checkIfParamIsNotNull(data)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalida requisição de Cliente");
		var token = services.refreshToken(data);
		if(token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalida requisição de Cliente");
		return token;
	}

	private boolean checkIfParamIsNotNull(AccountCredentialsVO data) {
		return data == null || data.getUsername() == null || data.getUsername().isBlank()
				|| data.getPassword() == null || data.getPassword().isBlank();
	}
}
