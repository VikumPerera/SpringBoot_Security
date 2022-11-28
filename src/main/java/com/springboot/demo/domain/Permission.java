package com.springboot.demo.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Permission {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String code;
	private Long createdDate;
	private Long modifiedDate;
	
	@ManyToOne
    @JoinColumn(name = "componentId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_COMPONENT"))
    private Component component;
	
	@ManyToOne
    @JoinColumn(name = "actionId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_ACTION"))
    private Action action;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "permission")
    private List<RolePermission> rolePermissions;
}
