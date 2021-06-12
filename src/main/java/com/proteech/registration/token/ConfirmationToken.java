package com.proteech.registration.token;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.proteech.appuser.AppUser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {
@SequenceGenerator(	name = "confirmation_token_sequence",
			sequenceName = "confirmation_token_sequence",
			allocationSize = 1
			)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "confirmation_token_sequence"
			)
private Long id;
@Column(nullable = false)
private String token;
private LocalDateTime  createdAt;
@Column(nullable = false)

private LocalDateTime expiresAt;

@ManyToOne
@JoinColumn(nullable = false,
name = "app_user_id"
		)
AppUser appUser;

private LocalDateTime confirmedAt;
public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt,AppUser appUser) {
	super();
	this.token = token;
	this.createdAt = createdAt;
	this.expiresAt = expiresAt;
	this.appUser=appUser;
}

}
