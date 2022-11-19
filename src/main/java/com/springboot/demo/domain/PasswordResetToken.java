package com.springboot.demo.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.springboot.demo.util.SaasUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {
	//Expiration time 10 minutes
	private static final Long EXPIRATION_TIME = 600000L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String token;
	private Long expirationTime;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_PASSWORD_RESET_TOKEN"))
	private User user;
	
	public PasswordResetToken(User user, String token) {
		this.user = user;
		this.token = token;
		this.expirationTime = SaasUtil.timeStampGenerator()+EXPIRATION_TIME;
	}
	
	public PasswordResetToken(String token) {
		this.token = token;
		this.expirationTime = SaasUtil.timeStampGenerator()+EXPIRATION_TIME;
	}

}
