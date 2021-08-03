package com.ij026.team3.mfpe.pointsmicroservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ij026.team3.mfpe.pointsmicroservice.controller.PointsServiceController;
import com.ij026.team3.mfpe.pointsmicroservice.feignclient.AuthFeign;
import com.ij026.team3.mfpe.pointsmicroservice.feignclient.OfferMicroserviceFeign;
import com.ij026.team3.mfpe.pointsmicroservice.service.PointsService;

@SpringBootTest
class PointsMicroserviceApplicationTests {

	@Autowired
	private PointsServiceController pointsServiceController;
	@Autowired
	private PointsService pointsService;
	@Autowired
	private AuthFeign authFeign;
	@Autowired
	private OfferMicroserviceFeign offerMicroserviceFeign;

	@Test
	void contextLoads() {
		assertThat(pointsService).isNotNull();
		assertThat(pointsServiceController).isNotNull();
		assertThat(authFeign).isNotNull();
		assertThat(offerMicroserviceFeign).isNotNull();
	}

}
