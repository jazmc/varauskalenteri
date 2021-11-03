package com.example.varauskalenteri.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Kategoria {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long categoryid;
	private String name;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kategoria")
	private List<Varaus> varaus;

	public List<Varaus> getVaraukset() {
		return varaus;
	}

	public void setVaraukset(List<Varaus> varaus) {
		this.varaus = varaus;
	}

	public Kategoria() {
	}

	public Kategoria(String name) {
		super();
		this.name = name;
	}

	public Long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Category id=" + categoryid + ", name=" + name;
	}
}