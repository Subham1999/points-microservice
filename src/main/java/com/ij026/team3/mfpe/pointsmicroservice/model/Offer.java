package com.ij026.team3.mfpe.pointsmicroservice.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Entity
public class Offer {
	//@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer offerId;

	private String authorId;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate createdAt;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate closedAt;

	//@Column(length = 100)
	private String details;

	@ElementCollection(fetch = FetchType.LAZY)
	@Builder.Default
	private List<Like> likes = new ArrayList<Like>();

	private OfferCategory offerCategory;

	private boolean isOpen;

	private String buyerId;

	public void like(String empId) {
		likes.add(new Like(empId, LocalDate.now()));
	}
}
