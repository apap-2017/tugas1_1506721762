package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Many;

import com.example.model.PendudukModel;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;


@Mapper
public interface PendudukMapper {
	@Select("select * from penduduk pen, keluarga kel, kecamatan kec, kota ko, kelurahan kl where pen.id_keluarga = kel.id and kel.id_kelurahan = kl.id and kl.id_kecamatan = kec.id and kec.id_kota = ko.id and nik=#{nik}")
	@Results(value = {
	@Result(property="kelurahan", column="nama_kelurahan"),
	@Result(property="kecamatan", column="nama_kecamatan"),
	@Result(property="kota", column="nama_kota")
	})
	PendudukModel getPenduduk(String nik);
	
	@Select("select * from keluarga kel, kecamatan kec, kota ko, kelurahan kl where kel.id_kelurahan = kl.id and kl.id_kecamatan = kec.id and kec.id_kota = ko.id and nomor_kk=#{nkk}")
	@Results(value = {
	@Result(property="kelurahan", column="nama_kelurahan"),
	@Result(property="kecamatan", column="nama_kecamatan"),
	@Result(property="kota", column="nama_kota"),
	@Result(property="anggotas", column="id",
	javaType = List.class,
	many=@Many(select="getPendudukByKeluarga"))
	})
	KeluargaModel getKeluarga(String nkk);
	
	@Select("select * from keluarga kel, kecamatan kec, kota ko, kelurahan kl where kel.id_kelurahan = kl.id and kl.id_kecamatan = kec.id and kec.id_kota = ko.id and kel.id=#{id_keluarga}")
	@Results(value = {
	@Result(property="kelurahan", column="nama_kelurahan"),
	@Result(property="kecamatan", column="nama_kecamatan"),
	@Result(property="kota", column="nama_kota")})
	KeluargaModel getKeluargaByID(String id_keluarga);
	
	@Select("select * from penduduk where id_keluarga=#{id_keluarga}")
	List<PendudukModel> getPendudukByKeluarga(String id_keluarga);
	
	@Insert("insert into penduduk VALUES (DEFAULT, #{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, #{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
	void addPenduduk(PendudukModel penduduk);
	
	@Select("select count(*) from penduduk where nik LIKE CONCAT(#{nik}, '%')")
	int cekNik(String cekNik);
	
	@Select("select count(*) from keluarga where nomor_kk LIKE CONCAT(#{nomor_kk}, '%')")
	int cekNKK(String nomor_kk);
	
	@Select("select kode_kelurahan from kelurahan where nama_kelurahan=#{nama_kelurahan}")
	String getKodeKelurahan(String nama_kelurahan);
	
	@Select("select id from kelurahan where kode_kelurahan=#{kode_kelurahan}")
	String getIDKelurahan(String kode_kelurahan);
	
	@Select("select kode_kecamatan from kecamatan where nama_kecamatan=#{nama_kecamatan}")
	String getKodeKecamatan(String nama_kecamatan);
	
	@Select("select kode_kota from kota where nama_kota=#{nama_kota}")
	String getKodeKota(String kota);
	
	@Insert("insert into keluarga VALUES (DEFAULT, #{nomor_kk}, #{alamat}, #{rt}, #{rw}, #{kelurahan}, 0)")
	void addKeluarga(KeluargaModel keluarga);
	
	@Delete("delete from penduduk where nik = #{nik}")
	void deletePenduduk(PendudukModel penduduk);
	
	@Delete("delete from keluarga where nomor_kk = #{nomor_kk}")
	void deleteKeluarga(KeluargaModel keluarga);
	
	@Update("update penduduk SET nik=#{nik}, nama=#{nama}, tempat_lahir=#{tempat_lahir}, tanggal_lahir=#{tanggal_lahir}, jenis_kelamin=#{jenis_kelamin}, is_wni=#{is_wni}, id_keluarga=#{id_keluarga}, agama=#{agama}, pekerjaan=#{pekerjaan}, status_perkawinan=#{status_perkawinan}, status_dalam_keluarga=#{status_dalam_keluarga}, golongan_darah=#{golongan_darah}, is_wafat=#{is_wafat} where nik=#{nikLama}")
	void updatePenduduk(PendudukModel penduduk);
	
	@Update("update keluarga SET nomor_kk=#{nomor_kk}, alamat=#{alamat}, rt=#{rt}, rw=#{rw}, id_kelurahan=#{kelurahan} where nomor_kk=#{nkkLama}")
	void updateKeluarga(KeluargaModel keluarga);
	
	@Update("update penduduk SET is_wafat='1' where nik=#{nik}")
	void updateStatusKematian(PendudukModel penduduk);
	
	@Update("update keluarga SET is_tidak_berlaku='1' where nomor_kk=#{nkk}")
	void nonaktifKeluarga(String nkk);
	
	@Select("select * from kota")
	List<KotaModel> getKotas();
	
	@Select("select * from kecamatan where id_kota=#{id_kota}")
	List<KecamatanModel> getKecamatans(String id_kota);
	
	@Select("select * from kota where id=#{id_kota}")
	KotaModel getSelectedKota(String id_kota);
	
	@Select("select * from kecamatan where id=#{id_kecamatan}")
	KecamatanModel getSelectedKecamatan(String id_kecamatan);
	
	@Select("select * from kelurahan where id_kecamatan=#{id_kecamatan}")
	List<KelurahanModel> getKelurahans(String id_kecamatan);
	
	@Select("select * from penduduk p, keluarga k, kelurahan kl where p.id_keluarga = k.id and k.id_kelurahan = kl.id and kl.id=#{id_kelurahan}")
	List<PendudukModel> searchForPenduduk(String id_kelurahan);
	
	@Select("select * from kelurahan where id=#{id_kelurahan}")
	KelurahanModel getSelectedKelurahan(String id_kelurahan);
}
