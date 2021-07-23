package com.ij026.team3.mfpe.pointsmicroservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ij026.team3.mfpe.pointsmicroservice.service.PointsService;

@RestController
public class PointsServiceController {
	@Autowired
	private PointsService pointsService;

	@GetMapping("/test")
	public String test(@RequestParam(required = false) Map<String, Object> map) {
		map.forEach((s, o) -> System.err.println(s + " : " + o));
		return "aaa";
	}

	@GetMapping("/getPointsOfEmployee/{empId}")
	public ResponseEntity<Integer> getPointsOfEmployee(@PathVariable String empId) {
		return ResponseEntity.ok(pointsService.calculatePointsOfEmployee(empId));
	}

}
