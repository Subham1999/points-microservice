package com.ij026.team3.mfpe.pointsmicroservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.ij026.team3.mfpe.pointsmicroservice.feignclient.OfferMicroserviceFeign;
import com.ij026.team3.mfpe.pointsmicroservice.model.Like;
import com.ij026.team3.mfpe.pointsmicroservice.model.Offer;
import com.ij026.team3.mfpe.pointsmicroservice.model.OfferCategory;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class PointsServiceTest {
	@Mock
	private OfferMicroserviceFeign offerMicroserviceFeign;

	@Autowired
	@InjectMocks
	private PointsService pointsService;

	private Offer offer1;
	private Offer offer2;

	private Like like_onSameDay;
	private Like likeAfter_2Days_1;
	private Like likeAfter_2Days_2;
	private Like likeAfter_3Days_1;
	private Like likeAfter_1Days_1;
	private Like likeAfter_1Days_2;

	private List<Like> likes1;
	private List<Like> likes2;

	@BeforeEach
	void setUp() throws Exception {
		like_onSameDay = new Like("ujjw", LocalDate.now());
		likeAfter_3Days_1 = new Like("ujjw", LocalDate.now().plusDays(3));

		likeAfter_2Days_1 = new Like("nikky", LocalDate.now().plusDays(2));
		likeAfter_2Days_2 = new Like("guru", LocalDate.now().plusDays(2));

		likeAfter_1Days_1 = new Like("subsa", LocalDate.now().plusDays(1));
		likeAfter_1Days_2 = new Like("rish", LocalDate.now().plusDays(1));

		likes1 = new ArrayList<>();
		likes2 = new ArrayList<>();

		likes1.add(like_onSameDay);
		likes1.add(likeAfter_1Days_1);
		likes1.add(likeAfter_2Days_1);
		likes1.add(likeAfter_3Days_1);

		likes2.add(likeAfter_1Days_1);
		likes2.add(likeAfter_1Days_2);
		likes2.add(likeAfter_2Days_2);

		offer1 = new Offer(0, "subsa", LocalDate.now(), null, "test1", likes1, OfferCategory.COMPUTER_ACCESORIES, true,
				null);

		offer2 = new Offer(0, "guru", LocalDate.now(), null, "test2", likes2, OfferCategory.COMPUTER_ACCESORIES, true,
				null);

		System.err.println(likes1);
		System.err.println(likes2);

	}

	@AfterEach
	void tearDown() throws Exception {
		offer1 = null;
		offer2 = null;

		like_onSameDay = null;
		likeAfter_2Days_1 = null;
		likeAfter_2Days_2 = null;
		likeAfter_3Days_1 = null;
		likeAfter_1Days_1 = null;
		likeAfter_1Days_2 = null;

		likes1 = null;
		likes2 = null;
	}

	@Test
	void testNumberOfLikesInFirstTwoDays() {
		long numberOfLikesInFirstTwoDays_1 = this.pointsService.numberOfLikesInFirstTwoDays(offer1);
		long numberOfLikesInFirstTwoDays_2 = this.pointsService.numberOfLikesInFirstTwoDays(offer2);
		assertEquals(3, numberOfLikesInFirstTwoDays_1);
		assertEquals(3, numberOfLikesInFirstTwoDays_2);

		assertEquals(-1, this.pointsService.numberOfLikesInFirstTwoDays(null));
	}

	@Test
	void testCountLikesInBetween() {
		assertEquals(4, this.pointsService.countLikesInBetween(LocalDate.now(), LocalDate.now().plusDays(3), likes1));
		assertEquals(3, this.pointsService.countLikesInBetween(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2),
				likes2));
	}

}
