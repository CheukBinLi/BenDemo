package com.ben.CarManager.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

/***
 * 用户详细信息
 * 
 * @author Administrator
 *
 */
@Entity
public class UserData {

	@Id
	private String userID;
	private String realName;
	private String sex;
	private String address;
	private String nickName;// 别名
	private String tel;// 电话
	private String EmergencyContact;// 紧急联系人电话
	private long integral;// 积分
	private long totalConsume;// 总销费
	private Date regDate;// 注册
	private byte[] idCard;// 身份信息(详细信息表:末确定类型)

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmergencyContact() {
		return EmergencyContact;
	}

	public void setEmergencyContact(String emergencyContact) {
		EmergencyContact = emergencyContact;
	}

	public long getIntegral() {
		return integral;
	}

	public void setIntegral(long integral) {
		this.integral = integral;
	}

	public long getTotalConsume() {
		return totalConsume;
	}

	public void setTotalConsume(long totalConsume) {
		this.totalConsume = totalConsume;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public byte[] getIdCard() {
		return idCard;
	}

	public void setIdCard(byte[] idCard) {
		this.idCard = idCard;
	}

}
