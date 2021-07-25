package com.ij026.team3.mfpe.pointsmicroservice.controller;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ij026.team3.mfpe.pointsmicroservice.feignclient.AuthFeign;
import com.ij026.team3.mfpe.pointsmicroservice.service.PointsService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@CrossOrigin
public class PointsServiceController {
	@Autowired
	private PointsService pointsService;

	@Autowired
	private AuthFeign authFeign;
	private ConcurrentHashMap<String, Object> empIdCache = new ConcurrentHashMap<>();

	public PointsServiceController() {
		empIdCache.put("guru", new Object());
		empIdCache.put("nikky", new Object());
		empIdCache.put("subsa", new Object());
		empIdCache.put("rish", new Object());
		empIdCache.put("ujjw", new Object());
	}

	private boolean isAuthorized(String jwtToken) {
		try {
			ResponseEntity<String> authorizeToken = authFeign.authorizeToken(jwtToken);
			boolean ok = (authorizeToken.getStatusCodeValue() == 200);
			if (ok) {
				System.err.println("Authorized");
			} else {
				System.err.println("Not Authorized");
			}
			return ok;
		} catch (Exception e) {
			System.err.println("Connection failure");
			return false;
		}
	}

	@GetMapping("/test")
	public String test() {
		return "aaa";
	}

	@GetMapping("/getPointsOfEmployee/{empId}")
	public ResponseEntity<Integer> getPointsOfEmployee(@RequestHeader(name = "Authorization") String jwtToken,
			@PathVariable String empId) {
		if (isAuthorized(jwtToken)) {
			if (empIdCache.containsKey(empId)) {
				return ResponseEntity.ok(pointsService.calculatePointsOfEmployee(jwtToken, empId));
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		} else {
			log.debug("jwtToken invalid");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<String> handleMissingRequestHeaderException(
			MissingRequestHeaderException missingRequestHeaderException) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header doesn't exist");
	}
}
