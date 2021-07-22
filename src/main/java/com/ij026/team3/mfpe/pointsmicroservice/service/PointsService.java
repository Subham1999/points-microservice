package com.ij026.team3.mfpe.pointsmicroservice.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ij026.team3.mfpe.pointsmicroservice.feignclient.OfferMicroserviceFeign;
import com.ij026.team3.mfpe.pointsmicroservice.model.Like;
import com.ij026.team3.mfpe.pointsmicroservice.model.Offer;

@Service
public class PointsService implements GenericPointsMicroservice {

	@Autowired
	private OfferMicroserviceFeign offerMicroserviceFeign;
	private long UPPER_LIMIT = 3;
	private long LOWER_LIMIT = 2;

	@Override
	public long numberOfLikesInFirstTwoDays(Offer offer) {
		if (offer != null) {
			List<Like> likes = offer.getLikes();
			LocalDate startDate = offer.getCreatedAt();
			LocalDate endDate = startDate.plusDays(2);
			long count = countLikesInBetween(startDate, endDate, likes);
			return count;
		}
		return -1;
	}

	@Override
	public long countLikesInBetween(LocalDate startDate, LocalDate endDate, List<Like> likes) {
		return likes.stream()
				.filter(like -> like.getLikedDate().isAfter(startDate) && like.getLikedDate().isBefore(endDate))
				.count();

	}

	@Override
	public int calculatePointsOfEmployee(String empId) {
		// Incomplete
		// 1st arg pass length
		List<Offer> offers = (List<Offer>) offerMicroserviceFeign.getOfferDetailsByAuthor(empId).getBody();
		int points = 0;
		for (Offer o : offers) {
			long n = numberOfLikesInFirstTwoDays(o);

			if (n >= UPPER_LIMIT) {
				points += 50; // Rule #1
			} else if (n >= LOWER_LIMIT) {
				points += 10; // Rule #2
			}
			// calculate engaged date
			LocalDate start = o.getCreatedAt();
			LocalDate end = o.getClosedAt();
			// if end - start <= 2 days
			Period period = Period.between(start, end);
			if (period.getDays() <= 2) {
				points += 100; // Rule #3
			}
		}
		return points;

	}

}
