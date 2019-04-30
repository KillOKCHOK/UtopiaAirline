package com.utopia.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "role")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "roleId")
public class Role {
	//USER, ADMIN, AGENT, ANAUTHORIZED, COUNTER_IN_CHARGE, TRAVELLER;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "role_id")
	private Long roleId;
	
	@Column(name = "role_name")
	private String roleName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="create_by")
	private User createUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="update_by")
	private User updateUser;
	
	@Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public User getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(User updateUser) {
		this.updateUser = updateUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	
}
