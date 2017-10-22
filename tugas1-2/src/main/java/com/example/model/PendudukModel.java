package com.example.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel
{
	private String nik;
	private String nikLama;
	private String nama;
	private String tempat_lahir;
	private String tanggal_lahir;
	private String alamat;
	private String rt;
	private String rw;
	private String kelurahan;
	private String kecamatan;
	private String kota;
	private String golongan_darah;
	private String agama;
	private String status_perkawinan;
	private String pekerjaan;
	private String is_wni;
	private String is_wafat;
	private String status_dalam_keluarga;
	private String jenis_kelamin;
	private String id_keluarga;
	
	public void setWNI(String wni) {
		this.is_wni = wni;
	}
	
	public void setWafat(String wafat) {
		this.is_wafat = wafat;
	}
}
