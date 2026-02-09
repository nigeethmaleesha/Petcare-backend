package com.donation_ms.repository;

import com.donation_ms.dto.ShelterDistributionDTO;
import com.donation_ms.dto.ShelterDistributionProjection;
import com.donation_ms.entity.Donation;
import com.donation_ms.entity.DonationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Integer> {

    List<Donation> findByDonationRequestId(Integer donationRequestId);

    List<Donation> findByStatus(DonationStatus status);

    @Query("SELECT COALESCE(SUM(d.amount), 0) FROM Donation d WHERE d.status = 'SUCCESS'")
    BigDecimal getTotalReceived();

    @Query("SELECT COALESCE(SUM(d.amount), 0) FROM Donation d WHERE d.status = 'SUCCESS' AND MONTH(d.createdAt) = MONTH(CURRENT_DATE) AND YEAR(d.createdAt) = YEAR(CURRENT_DATE)")
    BigDecimal getThisMonthReceived();

    List<Donation> findByDonorNameContainingIgnoreCase(String donorName);

    List<Donation> findByStatusAndDonorNameContainingIgnoreCase(DonationStatus status, String donorName);

    // Get shelter distribution with shelter_name from donation_requests
    @Query(value = """
        SELECT 
            dr.shelter_id as shelterId,
            dr.shelter_name as shelterName,
            COALESCE(SUM(d.amount), 0) as totalReceived,
            MAX(d.created_at) as lastTransaction,
            COUNT(DISTINCT dr.id) as campaignCount
        FROM donations d
        JOIN donation_requests dr ON d.donation_request_id = dr.id
        WHERE d.status = 'SUCCESS'
        GROUP BY dr.shelter_id, dr.shelter_name
        ORDER BY totalReceived DESC
        """, nativeQuery = true)
    List<ShelterDistributionProjection> getShelterDistribution();

    // Get donations by shelter ID - UPDATED to use shelter_id from donations table
    @Query(value = """
    SELECT d.* FROM donations d
    WHERE d.shelter_id = :shelterId
    ORDER BY d.created_at DESC
    """, nativeQuery = true)
    List<Donation> findByShelterId(@Param("shelterId") Integer shelterId);

    // Get total received by shelter ID - UPDATED
    @Query(value = """
    SELECT COALESCE(SUM(d.amount), 0) FROM donations d
    WHERE d.status = 'SUCCESS' AND d.shelter_id = :shelterId
    """, nativeQuery = true)
    BigDecimal getTotalReceivedByShelter(@Param("shelterId") Integer shelterId);

    // Get this month's total by shelter ID - UPDATED
    @Query(value = """
    SELECT COALESCE(SUM(d.amount), 0) FROM donations d
    WHERE d.status = 'SUCCESS' 
    AND d.shelter_id = :shelterId
    AND MONTH(d.created_at) = MONTH(CURRENT_DATE) 
    AND YEAR(d.created_at) = YEAR(CURRENT_DATE)
    """, nativeQuery = true)
    BigDecimal getThisMonthReceivedByShelter(@Param("shelterId") Integer shelterId);

    // Get donations by shelter ID with filtering options - UPDATED
    @Query(value = """
    SELECT d.* FROM donations d
    WHERE d.shelter_id = :shelterId
    AND (:search IS NULL OR 
         d.donor_name LIKE CONCAT('%', :search, '%') OR
         d.donation_request_id LIKE CONCAT('%', :search, '%'))
    ORDER BY d.created_at DESC
    """, nativeQuery = true)
    List<Donation> findByShelterIdWithSearch(
            @Param("shelterId") Integer shelterId,
            @Param("search") String search
    );
}