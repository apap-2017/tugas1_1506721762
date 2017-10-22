package com.example.model;

import java.util.List;

public class KotaModel {
	private String kode_kota;
	private String nama_kota;
	private String id;
	
	public String getId() {
		return id;
	}
	
	public String getKode_kota() {
		return kode_kota;
	}
	public void setKode_kota(String kode_kota) {
		this.kode_kota = kode_kota;
	}
	public String getNama_kota() {
		return nama_kota;
	}
	public void setNama_kota(String nama_kota) {
		this.nama_kota = nama_kota;
	}
}
