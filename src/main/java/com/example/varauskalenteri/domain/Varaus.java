package com.example.varauskalenteri.domain;

import java.time.Duration;
import java.time.LocalDateTime;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Varaus {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime alku, loppu;
	private String varaaja, selitys;
	private double kesto;

	@ManyToOne
	@JoinColumn(name = "categoryid")
	@JsonManagedReference
	private Kategoria kategoria;
	
	public Kategoria getKategoria() {
		return kategoria;
	}

	public void setKategoria(Kategoria kategoria) {
		this.kategoria = kategoria;
	}

	public Varaus() {}

	public Varaus(LocalDateTime alku, LocalDateTime loppu, String varaaja, String selitys, Kategoria kategoria) {
		super();
		this.alku = alku;
		this.loppu = loppu;
		Duration dur = Duration.between(alku, loppu);
		this.setKesto((double)dur.toMinutes() / 60);
		this.varaaja = varaaja;
		this.selitys = selitys;
		this.kategoria = kategoria;
	}
	
	public Varaus(LocalDateTime alku, LocalDateTime loppu, String varaaja, String selitys) {
		super();
		this.alku = alku;
		this.loppu = loppu;
		Duration dur = Duration.between(alku, loppu);
		this.setKesto((double)dur.toMinutes() / 60);
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

	public LocalDateTime getAlku() {
		return alku;
	}

	public void setAlku(LocalDateTime alku) {
		this.alku = alku;
	}

	public LocalDateTime getLoppu() {
		return loppu;
	}

	public void setLoppu(LocalDateTime loppu) {
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
	
	public double getKesto() {
		return kesto;
	}

	public void setKesto(double kesto) {
		this.kesto = kesto;
	}

	@Override
	public String toString() {
		if (this.kategoria != null) {
			return "Varaus id=" + id + ", alku=" + alku + ",loppu=" + loppu + ",kesto=" + kesto + ",varaaja=" + varaaja + ",selitys=" + selitys + ",kategoria=" + this.getKategoria();
		} else {
			return "Varaus id=" + id + ", alku=" + alku + ",loppu=" + loppu + ",kesto=" + kesto + ",varaaja=" + varaaja + ",selitys=" + selitys;
		}
	}


}