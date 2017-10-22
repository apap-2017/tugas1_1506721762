package com.example.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class KeluargaModel {
	private String nomor_kk;
	private String nkkLama;
	private String alamat;
	private String rt;
	private String rw;
	private String kelurahan;
	private String kecamatan;
	private String kota;
	private String kode_kelurahan;
	private String kode_kecamatan;
	private String kode_kota;
	private List<PendudukModel> anggotas;
}
