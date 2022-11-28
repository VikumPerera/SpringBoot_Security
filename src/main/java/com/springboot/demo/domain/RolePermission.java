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
public class RolePermission {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long createdDate;
	private Long modifiedDate;
	
	@ManyToOne
    @JoinColumn(name = "permissionId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_PERMISSION"))
    private Permission permission;
	
	@ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_ROLE_1"))
    private Role role;
	
	
}
