package br.com.leuxam.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.leuxam.data.vo.v1.security.AccountCredentialsVO;
import br.com.leuxam.data.vo.v1.security.TokenVO;
import br.com.leuxam.repositories.UserRepository;
import br.com.leuxam.security.jwt.JwtTokenProvider;

@Service
public class AuthServices {
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository repository;
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity signin(AccountCredentialsVO data){
		try {
			var username = data.getUsername();
			var password = data.getPassword();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
			var user = repository.findByUserName(username);
			
			var tokenResponse = new TokenVO();
			if(user != null) {
				tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
			}else {
				throw new UsernameNotFoundException("Usuario " + username + " não encontrado!");
			}
			return ResponseEntity.ok(tokenResponse);
		} catch (Exception e) {
			throw new BadCredentialsException("Usuario ou senha estão incorretos!");
		}
	}
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity refreshToken(String username, String refreshToken){
		var user = repository.findByUserName(username);
		
		var tokenResponse = new TokenVO();
		if(user != null) {
			tokenResponse = tokenProvider.refreshToken(refreshToken);
		}else {
			throw new UsernameNotFoundException("Usuario " + username + " não encontrado!");
		}
		return ResponseEntity.ok(tokenResponse);
	}
}
