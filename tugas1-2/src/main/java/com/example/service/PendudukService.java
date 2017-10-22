package com.example.service;

import com.example.model.PendudukModel;

import java.util.List;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;

public interface PendudukService {
	PendudukModel getPenduduk(String nik);
	KeluargaModel getKeluarga(String nkk);
	void addPenduduk(PendudukModel penduduk);
	KeluargaModel getKeluargaByID(String id_keluarga);
	int cekNik (String cekNik);
	String getKodeKelurahan(String kelurahan);
	String getKodeKecamatan(String kecamatan);
	String getKodeKota(String kota);
	String getIDKelurahan(String kelurahan);
	void addKeluarga(KeluargaModel keluarga);
	int cekNkk(String nkk);
	void deletePenduduk(PendudukModel penduduk);
	void deleteKeluarga(KeluargaModel keluarga);
	void updatePenduduk(PendudukModel penduduk);
	void updateKeluarga(KeluargaModel keluarga);
	void updateStatusKematian(PendudukModel penduduk);
	void nonaktifKeluarga(String nkk);
	List<KotaModel> getKotas();
	List<KecamatanModel> getKecamatans(String id_kota);
	KotaModel getSelectedKota(String id_kota);
	KecamatanModel getSelectedKecamatan(String id_kecamatan);
	List<KelurahanModel> getKelurahans(String id_kecamatan);
	List<PendudukModel> searchForPenduduk(String id_kelurahan);
	KelurahanModel getSelectedKelurahan(String id_kelurahan);
}
