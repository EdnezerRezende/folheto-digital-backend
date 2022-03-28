package br.com.pic.folheto.controllers;


import javax.jms.JMSException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import br.com.pic.folheto.controllers.exceptions.ControllerExceptionHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.pic.folheto.dto.EmailDTO;
import br.com.pic.folheto.dto.NewPasswordDTO;
import br.com.pic.folheto.security.JWTUtil;
import br.com.pic.folheto.security.UserSS;
import br.com.pic.folheto.services.AuthService;
import br.com.pic.folheto.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	@Operation(summary = "Refresh Token", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<Void> refreshToken(final HttpServletResponse response) {
		final UserSS user = UserService.authenticated();
		final String token = jwtUtil.generateToken(user.getUsername(), user.getAuthorities());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	@Operation(summary = "Forgot", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<Void> forgot(@Valid @RequestBody final EmailDTO objDto) {
		try {
			service.sendNewPassword(objDto.getEmail());
		} catch (JMSException e) {
			new ControllerExceptionHandler();
		}
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot/newPassword", method = RequestMethod.POST)
	@Operation(summary = "New Password", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<Void> forgotNewPassword(@Valid @RequestBody final NewPasswordDTO objDto) {
		try {
			service.trocaSenha(objDto);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return ResponseEntity.noContent().build();
	}
}