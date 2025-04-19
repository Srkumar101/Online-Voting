package com.aaen.OnlineVotingSystem.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

/**
 * @author Mohammad Enayatullah
 *
 */
@Builder
@Data
@Document(collection = "reservation")
public class Reservation {

	@Id
	private String id;
	private String resourceId;
	private Integer resourceQuantiny;
	private String requestBy;
	private String approvedBy;
	private String deniedBy;
	private String returnApprovedBy;
	private String state;
	private LocalDateTime aprovedDateTime;
	private LocalDateTime deniedDateTime;
	private LocalDateTime cancelDateTime;
	private LocalDateTime returnRequestDateTime;
	private LocalDateTime returnRequestApprovedDateTime;
	private Boolean isReturn;
	private Boolean isCancelled;
}
