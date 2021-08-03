package com.ij026.team3.mfpe.pointsmicroservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MissingRequestHeaderException;

import com.ij026.team3.mfpe.pointsmicroservice.feignclient.AuthFeign;
import com.ij026.team3.mfpe.pointsmicroservice.service.PointsService;

//@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@WebMvcTest
class PointsServiceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthFeign authFeign;

	@MockBean
	private PointsService pointsService;

	@Autowired
	@InjectMocks
	private PointsServiceController pointsServiceController;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testTest() throws Exception {
		assertEquals("aaa", this.pointsServiceController.test());
	}

	@Test
	void testGetPointsOfEmployee_whenValidEmpIdGiven() {
		String jwtToken = "invalidTestJwtToken_forEmpId_subsa";

		when(authFeign.authorizeToken(jwtToken)).thenReturn(ResponseEntity.ok("subsa"));
		when(pointsService.calculatePointsOfEmployee(jwtToken, "subsa")).thenReturn(2);
		assertEquals(2, pointsServiceController.getPointsOfEmployee(jwtToken, "subsa").getBody());
	}

	@Test
	void testGetPointsOfEmployee_whenInValidEmpIdGiven() {
		String jwtToken = "invalidTestJwtToken_forEmpId_xyz";

		when(authFeign.authorizeToken(jwtToken)).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
		assertEquals(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(),
				pointsServiceController.getPointsOfEmployee(jwtToken, "xyz"));
	}

	@Test
	void testGetPointsOfEmployee_whenInValidEmpId_invalidJwtToken() {
		String jwtToken = "validJwtToken_forValidEmpId_xyz";

		when(authFeign.authorizeToken(jwtToken)).thenReturn(ResponseEntity.status(HttpStatus.OK).build());
		assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).build(),
				pointsServiceController.getPointsOfEmployee(jwtToken, "xyz"));
	}

	@Test
	void testHandleMissingRequestHeaderException() throws Exception {
		MissingRequestHeaderException missingRequestHeaderException = new MissingRequestHeaderException("Authorization",
				null);

		assertEquals(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header doesn't exist"),
				pointsServiceController.handleMissingRequestHeaderException(missingRequestHeaderException));
	}

}
