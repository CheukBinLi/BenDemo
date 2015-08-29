package com.ben.CarManager.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/***
 * 接口权限信息
 * 
 * @author Administrator
 *
 */
@Entity
public class Authority {

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	@Column(columnDefinition = "CHAR(32)")
	private String id;
	private String code;
	private String directions;// 说明
	private boolean status;// 开关
	private String errorMsg;// 不能通过回调个消息

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDirections() {
		return directions;
	}

	public void setDirections(String directions) {
		this.directions = directions;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
