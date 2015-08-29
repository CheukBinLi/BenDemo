package com.ben.CarManager.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/***
 * 用户
 * 
 * @author Administrator
 *
 */
@Entity
public class User {

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	@Column(columnDefinition = "CHAR(32)")
	private String id;
	private String userName;
	private String tel;// 电话
	private String password;
	private String AuthorityType;// 用户类型(权限有关)
	private String remark;// 备注

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthorityType() {
		return AuthorityType;
	}

	public void setAuthorityType(String authorityType) {
		AuthorityType = authorityType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
