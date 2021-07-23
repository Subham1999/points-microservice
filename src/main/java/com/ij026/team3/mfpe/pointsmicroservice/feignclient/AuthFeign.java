package com.ij026.team3.mfpe.pointsmicroservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ij026.team3.mfpe.pointsmicroservice.model.AuthRequest;
import com.ij026.team3.mfpe.pointsmicroservice.model.AuthResponse;


@FeignClient(name = "auth", url = "${AUTH_URL:http://localhost:6666/auth}")
public interface AuthFeign {

	@PostMapping(value = "/authenticate")
	public ResponseEntity<AuthResponse> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest)
			throws Exception;

	@PostMapping(value = "/authorize-token")
	public ResponseEntity<String> authorizeToken(
			@RequestHeader(value = "Authorization", required = true) String authorizationHeaderVal);
}
