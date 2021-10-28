package com.example.varauskalenteri.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Varaus {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String alku, loppu, varaaja, selitys;

	@ManyToOne
	@JoinColumn(name = "categoryid")
	@JsonManagedReference
	private Kategoria kategoria;
	
	public Kategoria getCategory() {
		return kategoria;
	}

	public void setCategory(Kategoria kategoria) {
		this.kategoria = kategoria;
	}

	public Varaus() {}

	public Varaus(String alku, String loppu, String varaaja, String selitys, Kategoria kategoria) {
		super();
		this.alku = alku;
		this.loppu = loppu;
		this.varaaja = varaaja;
		this.selitys = selitys;
		this.kategoria = kategoria;
	}
	
	public Varaus(String alku, String loppu, String varaaja, String selitys) {
		super();
		this.alku = alku;
		this.loppu = loppu;
		this.varaaja = varaaja;
		this.selitys = selitys;
		this.kategoria = null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAlku() {
		return alku;
	}

	public void setAlku(String alku) {
		this.alku = alku;
	}

	public String getLoppu() {
		return loppu;
	}

	public void setLoppu(String loppu) {
		this.loppu = loppu;
	}

	public String getVaraaja() {
		return varaaja;
	}

	public void setVaraaja(String varaaja) {
		this.varaaja = varaaja;
	}

	public String getSelitys() {
		return selitys;
	}

	public void setSelitys(String selitys) {
		this.selitys = selitys;
	}

	@Override
	public String toString() {
		if (this.kategoria != null) {
			return "Varaus id=" + id + ", alku=" + alku + ",loppu=" + loppu + ",varaaja=" + varaaja + ",selitys=" + selitys + ",kategoria=" + this.getCategory();
		} else {
			return "Varaus id=" + id + ", alku=" + alku + ",loppu=" + loppu + ",varaaja=" + varaaja + ",selitys=" + selitys;
		}
	}
}