package com.ij026.team3.mfpe.pointsmicroservice.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ij026.team3.mfpe.pointsmicroservice.feignclient.OfferMicroserviceFeign;
import com.ij026.team3.mfpe.pointsmicroservice.model.Like;
import com.ij026.team3.mfpe.pointsmicroservice.model.Offer;

@Service
public class PointsService implements GenericPointsMicroservice {

	@Autowired
	private OfferMicroserviceFeign offerMicroserviceFeign;
	private final long UPPER_LIMIT = 3;
	private final long LOWER_LIMIT = 2;

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
		long count = 0l;
		for (Like like : likes) {
			LocalDate likedDate = like.getLikedDate();
			if (likedDate.isAfter(endDate)) {
				continue;
			} else if (likedDate.equals(endDate)) {
				count++;
			} else if (likedDate.isAfter(startDate)) {
				count++;
			} else if (likedDate.equals(startDate)) {
				count++;
			}
		}

		return count;
	}

	@Override
	public int calculatePointsOfEmployee(String jwtToken, String empId) {
		ResponseEntity<List<Offer>> offerDetailsByAuthor = offerMicroserviceFeign.getOfferDetailsByAuthor(jwtToken,
				empId);
		List<Offer> offers = offerDetailsByAuthor.getBody();
		int points = 0;
		for (Offer o : offers) {
			long n = numberOfLikesInFirstTwoDays(o);

			System.err.println("Number of likes in first two days " + n);

			if (n >= UPPER_LIMIT) {
				// Rule #1
				points += 50;
			} else if (n >= LOWER_LIMIT) {
				// Rule #2
				points += 10;
			}

			// calculate engaged date
			LocalDate start = o.getCreatedAt();
			LocalDate end = o.getClosedAt();

			if (start != null && end != null) {
				Period period = Period.between(start, end);
				if (period.getDays() <= 2) {
					// Rule #3
					points += 100;
				}
			}
		}
		return points;
	}

}
