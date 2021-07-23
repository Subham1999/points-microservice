package com.ij026.team3.mfpe.pointsmicroservice.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ij026.team3.mfpe.pointsmicroservice.exception.NoSuchEmpIdException;
import com.ij026.team3.mfpe.pointsmicroservice.service.PointsService;

@RestController
public class PointsServiceController {
	@Autowired
	private PointsService pointsService;
	private ConcurrentHashMap<String, Object> empIdCache = new ConcurrentHashMap<>();

	public PointsServiceController() {
		empIdCache.put("guru", new Object());
		empIdCache.put("nikky", new Object());
		empIdCache.put("subsa", new Object());
		empIdCache.put("rish", new Object());
		empIdCache.put("ujjw", new Object());
	}

	@GetMapping("/test")
	public String test(@RequestParam(required = false) Map<String, Object> map) {
		map.forEach((s, o) -> System.err.println(s + " : " + o));
		return "aaa";
	}

	@GetMapping("/getPointsOfEmployee/{empId}")
	public ResponseEntity<Integer> getPointsOfEmployee(@PathVariable String empId) {
		if(empIdCache.contains(empId)) {
		return ResponseEntity.ok(pointsService.calculatePointsOfEmployee(empId));
		}
		else {
			throw new NoSuchEmpIdException("empid " + empId + " is invalid");
		}
	}

}
