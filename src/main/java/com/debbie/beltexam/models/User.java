package com.debbie.beltexam.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.debbie.beltexam.models.Role;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Size(min=1)
	private String name;
	
	@Size(min=5)
	private String email;
	
	@Size(min=8)
	private String password;
	
	@Transient
	private String passwordConfirmation;
	
	@Size(min=1)
	private String description;
	
	private Date createdAt;
	private Date updatedAt;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(
		name = "users_roles",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private List<Role> roles;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(
		name="connections",
		joinColumns = @JoinColumn(name="user_id"),
		inverseJoinColumns = @JoinColumn(name="friend_id")
	)
	private List<User> friends_i_added;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(
		name="connections",
		joinColumns = @JoinColumn(name="friend_id"),
		inverseJoinColumns = @JoinColumn(name="user_id")
	)
	private List<User> friends_added_me;

	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(
		name="invitations",
		joinColumns = @JoinColumn(name="user_id"),
		inverseJoinColumns = @JoinColumn(name="friend_id")
	)
	private List<User> invites_i_sent;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(
		name="invitations",
		joinColumns = @JoinColumn(name="friend_id"),
		inverseJoinColumns = @JoinColumn(name="user_id")
	)
	private List<User> invites_to_me;
	
	public User() {
		
	}
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<User> getFriends_i_added() {
		return friends_i_added;
	}

	public void setFriends_i_added(List<User> friends_i_added) {
		this.friends_i_added = friends_i_added;
	}

	public List<User> getFriends_added_me() {
		return friends_added_me;
	}

	public void setFriends_added_me(List<User> friends_added_me) {
		this.friends_added_me = friends_added_me;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<User> getInvites_i_sent() {
		return invites_i_sent;
	}

	public void setInvites_i_sent(List<User> invites_i_sent) {
		this.invites_i_sent = invites_i_sent;
	}

	public List<User> getInvites_to_me() {
		return invites_to_me;
	}

	public void setInvites_to_me(List<User> invites_to_me) {
		this.invites_to_me = invites_to_me;
	}
	
	

}
