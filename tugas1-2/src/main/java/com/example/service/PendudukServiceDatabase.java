package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PendudukMapper;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService
{
    @Autowired
    private PendudukMapper mapper;

	@Override
	public PendudukModel getPenduduk(String nik) {
		return mapper.getPenduduk(nik);
	}

	@Override
	public KeluargaModel getKeluarga(String nkk) {
		// TODO Auto-generated method stub
		log.info ("get keluarga with nkk {}", nkk);
		return mapper.getKeluarga(nkk);
	}

	@Override
	public void addPenduduk(PendudukModel penduduk) {
		// TODO Auto-generated method stub
		log.info ("add penduduk with nama {}", penduduk.getNama());
		mapper.addPenduduk(penduduk);
	}

	@Override
	public KeluargaModel getKeluargaByID(String id_keluarga) {
		// TODO Auto-generated method stub
		return mapper.getKeluargaByID(id_keluarga);
	}

	@Override
	public int cekNik(String cekNik) {
		// TODO Auto-generated method stub
		return mapper.cekNik(cekNik);
	}

	@Override
	public String getKodeKelurahan(String kelurahan) {
		// TODO Auto-generated method stub
		return mapper.getKodeKelurahan(kelurahan);
	}

	@Override
	public String getKodeKecamatan(String kecamatan) {
		// TODO Auto-generated method stub
		return mapper.getKodeKecamatan(kecamatan);
	}

	@Override
	public String getKodeKota(String kota) {
		// TODO Auto-generated method stub
		return mapper.getKodeKota(kota);
	}

	@Override
	public String getIDKelurahan(String kelurahan) {
		// TODO Auto-generated method stub
		return mapper.getIDKelurahan(kelurahan);
	}

	@Override
	public void addKeluarga(KeluargaModel keluarga) {
		// TODO Auto-generated method stub
		mapper.addKeluarga(keluarga);
	}

	@Override
	public int cekNkk(String nkk) {
		// TODO Auto-generated method stub
		return mapper.cekNKK(nkk);
	}

	@Override
	public void deletePenduduk(PendudukModel penduduk) {
		// TODO Auto-generated method stub
		log.info("delete penduduk dengan nik {}" + penduduk.getNik());
		mapper.deletePenduduk(penduduk);
	}

	@Override
	public void deleteKeluarga(KeluargaModel keluarga) {
		// TODO Auto-generated method stub
		mapper.deleteKeluarga(keluarga);
	}

	@Override
	public void updatePenduduk(PendudukModel penduduk) {
		// TODO Auto-generated method stub
		mapper.updatePenduduk(penduduk);
	}

	@Override
	public void updateKeluarga(KeluargaModel keluarga) {
		// TODO Auto-generated method stub
		mapper.updateKeluarga(keluarga);
	}

	@Override
	public void updateStatusKematian(PendudukModel penduduk) {
		// TODO Auto-generated method stub
		mapper.updateStatusKematian(penduduk);
	}

	@Override
	public void nonaktifKeluarga(String nkk) {
		// TODO Auto-generated method stub
		mapper.nonaktifKeluarga(nkk);
	}

	@Override
	public List<KotaModel> getKotas() {
		// TODO Auto-generated method stub
		return mapper.getKotas();
	}

	@Override
	public List<KecamatanModel> getKecamatans(String id_kota) {
		// TODO Auto-generated method stub
		return mapper.getKecamatans(id_kota);
	}

	@Override
	public KotaModel getSelectedKota(String id_kota) {
		// TODO Auto-generated method stub
		return mapper.getSelectedKota(id_kota);
	}

	@Override
	public KecamatanModel getSelectedKecamatan(String id_kecamatan) {
		// TODO Auto-generated method stub
		return mapper.getSelectedKecamatan(id_kecamatan);
	}

	@Override
	public List<KelurahanModel> getKelurahans(String id_kecamatan) {
		// TODO Auto-generated method stub
		return mapper.getKelurahans(id_kecamatan);
	}

	@Override
	public List<PendudukModel> searchForPenduduk(String id_kelurahan) {
		// TODO Auto-generated method stub
		return mapper.searchForPenduduk(id_kelurahan);
	}

	@Override
	public KelurahanModel getSelectedKelurahan(String id_kelurahan) {
		// TODO Auto-generated method stub
		return mapper.getSelectedKelurahan(id_kelurahan);
	}
	
	
}
