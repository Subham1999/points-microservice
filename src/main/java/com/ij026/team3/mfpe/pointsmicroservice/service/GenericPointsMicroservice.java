package com.ij026.team3.mfpe.pointsmicroservice.service;

import java.time.LocalDate;
import java.util.List;

import com.ij026.team3.mfpe.pointsmicroservice.model.Like;
import com.ij026.team3.mfpe.pointsmicroservice.model.Offer;

public interface GenericPointsMicroservice {
	long numberOfLikesInFirstTwoDays(Offer offer);

	long countLikesInBetween(LocalDate startDate, LocalDate endDate, List<Like> likes);

	//int calculatePointsOfEmployee(String empId);

	int calculatePointsOfEmployee(String jwtToken, String empId);
}
