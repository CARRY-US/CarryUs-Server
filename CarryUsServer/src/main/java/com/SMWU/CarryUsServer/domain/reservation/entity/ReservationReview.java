package com.SMWU.CarryUsServer.domain.reservation.entity;

import com.SMWU.CarryUsServer.domain.reservation.exception.ReviewException;
import com.SMWU.CarryUsServer.global.entity.AuditingTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.SMWU.CarryUsServer.domain.reservation.exception.ReviewExceptiontype.ILLEGAL_REVIEW_RATING;

@Entity
@Table(name = "RESERVATIONS_REVIEWS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationReview extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationReviewId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private double reviewRating;

    private String reviewContent;

    public void updateReview(final Double reviewRating, final String reviewContent) {
        if(reviewContent != null && !reviewContent.isBlank()){
            this.reviewContent = reviewContent;
        }
        if(reviewRating!=null){
            validateReviewRating(reviewRating);
            this.reviewRating = reviewRating;
        }
    }

    private void validateReviewRating(final double reviewRating) {
        if(reviewRating < 0 || reviewRating > 5) {
            throw new ReviewException(ILLEGAL_REVIEW_RATING);
        }
    }
}
