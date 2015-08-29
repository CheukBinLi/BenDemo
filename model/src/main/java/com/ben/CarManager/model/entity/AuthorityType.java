package com.ben.CarManager.model.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class AuthorityType {

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	@Column(columnDefinition = "CHAR(32)")
	private String id;
	private byte[] authorities;// 权限列表
	private String directions;// 说明

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getAuthorities() {
		return authorities;
	}

	public void setAuthorities(byte[] authorities) {
		this.authorities = authorities;
	}

	public String getDirections() {
		return directions;
	}

	public void setDirections(String directions) {
		this.directions = directions;
	}

}
