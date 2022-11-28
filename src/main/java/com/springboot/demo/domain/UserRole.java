package com.springboot.demo.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserRole {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long createdDate;
	private Long modifiedDate;
	
	@ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER"))
    private User user;
	
	@ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_ROLE_2"))
    private Role role;
	
	
}
