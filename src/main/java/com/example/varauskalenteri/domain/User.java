package com.example.varauskalenteri.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="usertable")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
// Username with unique constraint
	@Column(name = "username", nullable = false, unique = true)
	private String username;
	@JsonIgnore
	@Column(name = "password", nullable = false)
	private String passwordHash;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "phone", nullable = false)
	private String phone;
	@Column(name = "etunimi", nullable = false)
	private String etunimi;
	@Column(name = "sukunimi", nullable = false)
	private String sukunimi;
	@Column(name = "role", nullable = false)
	private String role;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Varaus> varaukset;

	
    public User() {
    }

	public User(String username, String etunimi, String sukunimi, String passwordHash, String email, String phone, String role) {
		super();
		this.username = username;
		this.etunimi = etunimi;
		this.sukunimi = sukunimi;
		this.passwordHash = passwordHash;
		this.email = email;
		this.phone = phone;
		this.role = role;
	}

	public List<Varaus> getVaraukset() {
		return varaukset;
	}

	public void setVaraukset(List<Varaus> varaukset) {
		this.varaukset = varaukset;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEtunimi() {
		return etunimi;
	}

	public void setEtunimi(String etunimi) {
		this.etunimi = etunimi;
	}

	public String getSukunimi() {
		return sukunimi;
	}

	public void setSukunimi(String sukunimi) {
		this.sukunimi = sukunimi;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}