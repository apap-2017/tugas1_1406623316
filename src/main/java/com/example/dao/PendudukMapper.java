package com.example.dao;


import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

@Mapper
public interface PendudukMapper {
	
	@Select("select id, nik, nama, tempat_lahir, id_keluarga, " + 
			"agama, pekerjaan, status_perkawinan, status_dalam_keluarga, " + 
			"golongan_darah, tanggal_lahir, is_wni,jenis_kelamin,is_wafat from penduduk where nik = #{nik}")
    PendudukModel selectPenduduk (@Param("nik") String nik);
	
	@Select("select id, nomor_kk, alamat, RT, RW, id_kelurahan, is_tidak_berlaku " +
			"from keluarga where id = #{id}")
	@Results(value = {
    		@Result(property="id", column="id"),
    		@Result(property="nomor_kk", column="nomor_kk"),
    		@Result(property="alamat", column="alamat"),
    		@Result(property="RT", column="RT"),
    		@Result(property="RW", column="RW"),
    		@Result(property="id_kelurahan", column="id_kelurahan"),
    		@Result(property="is_tidak_berlaku", column="is_tidak_berlaku"),
    		@Result(property="penduduks", column="id",
    		javaType = List.class,
    		many=@Many(select="selectPenduduks"))
    })
	KeluargaModel selectKeluargaId(@Param("id") String id);

	@Select("select id, id_kecamatan, kode_kelurahan, nama_kelurahan, kode_pos " + 
			"from kelurahan where id = #{id}")	
	@Results(value= {
			@Result(property="id", column="id"),
			@Result(property="id_kecamatan", column="id_kecamatan"),
			@Result(property="kode_kelurahan", column="kode_kelurahan"),
			@Result(property="nama_kelurahan", column="nama_kelurahan"),
			@Result(property="kode_pos", column="kode_pos"),
			@Result(property="keluargas", column="id", 
			javaType = List.class,
			many=@Many(select="selectKeluargas"))
	})
	KelurahanModel selectKelurahanId(@Param("id") String id);
	
	@Select("select id, nomor_kk, alamat, RT, RW, id_kelurahan, is_tidak_berlaku " +
			"from keluarga where id_kelurahan = #{id_kelurahan}")
	List<KeluargaModel> selectKeluargas(@Param("id_kelurahan") String id_kelurahan);
	
	@Select("select id, id_kota, kode_kecamatan, nama_kecamatan " + 
			"from kecamatan where id = #{id}")
	@Results(value= {
			@Result(property="id", column="id"),
			@Result(property="id_kota", column="id_kota"),
			@Result(property="kode_kecamatan", column="kode_kecamatan"),
			@Result(property="nama_kecamatan", column="nama_kecamatan"), 
			@Result(property="kelurahans", column="id", 
			javaType = List.class,
			many=@Many(select="selectKelurahans"))
	})
	KecamatanModel selectKecamatanId (@Param("id") String id);
	
	@Select("select id, id_kecamatan, kode_kelurahan, nama_kelurahan, kode_pos " + 
			"from kelurahan where id_kecamatan = #{id_kecamatan}")
	List<KelurahanModel> selectKelurahans (@Param("id_kecamatan") String id_kecamatan);
	
	@Select("select id, kode_kota, nama_kota " + 
			"from kota where id = #{id}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="kode_kota", column="kode_kota"),
			@Result(property="nama_kota", column="nama_kota"),
			@Result(property="kecamatans", column="id", 
			javaType = List.class,
			many=@Many(select="selectKecamatans"))
	})
	KotaModel selectKotaId (@Param("id") String id);
	
	@Select("select id, id_kota, kode_kecamatan, nama_kecamatan " + 
			"from kecamatan where id_kota = #{id_kota}")
	List<KecamatanModel> selectKecamatans(@Param("id_kota") String id_kota);
	
	@Select("select id, nomor_kk, alamat, RT, RW, id_kelurahan, is_tidak_berlaku " +
			"from keluarga where nomor_kk = #{nkk}")
	@Results(value = {
    		@Result(property="id", column="id"),
    		@Result(property="nomor_kk", column="nomor_kk"),
    		@Result(property="alamat", column="alamat"),
    		@Result(property="RT", column="RT"),
    		@Result(property="RW", column="RW"),
    		@Result(property="id_kelurahan", column="id_kelurahan"),
    		@Result(property="is_tidak_berlaku", column="is_tidak_berlaku"),
    		@Result(property="penduduks", column="id",
    		javaType = List.class,
    		many=@Many(select="selectPenduduks"))
    })
	KeluargaModel selectKeluargaNkk(@Param("nkk") String nkk);
	
	@Select("select * from penduduk where id_keluarga = #{id_keluarga}")
	List<PendudukModel> selectPenduduks(@Param("id_keluarga") String id_keluarga);
	
	@Select("SELECT * from penduduk where id_keluarga IN (select id from keluarga where id_kelurahan = #{id_kelurahan})")
	List<PendudukModel> selectPenduduks2(@Param("id_kelurahan") String id_kelurahan);

	@Select("SELECT id_kecamatan from kelurahan WHERE id IN (SELECT id_kelurahan as id from keluarga where id = #{id_keluarga})")
	String selectIdKecamatan(@Param("id_keluarga") String id_keluarga);
	
	@Select("select id_kota from kecamatan where id = #{idKecamatan}")
	String selectIdKota(@Param("idKecamatan") String idKecamatan);
	
	@Select("select nik from penduduk where nik LIKE #{halfNIK} ORDER BY nik DESC LIMIT 1")
	String selectNikAvail(@Param("halfNIK") String halfNIK);
	
	@Insert("INSERT INTO penduduk(id,nama,tempat_lahir,tanggal_lahir,jenis_kelamin,golongan_darah,agama,status_perkawinan,pekerjaan,is_wni,status_dalam_keluarga,"+
			"is_wafat,id_keluarga,nik) VALUES (#{id},#{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{golongan_darah}, #{agama}, #{status_perkawinan}, #{pekerjaan}, #{is_wni}, #{status_dalam_keluarga}, "+
			"#{is_wafat}, #{id_keluarga}, #{nik})")
	void addPenduduk(@Param("id") int id,
					 @Param("nama") String nama, 
					 @Param("tempat_lahir") String tempat_lahir, 
					 @Param("tanggal_lahir") String tanggal_lahir,
					 @Param("jenis_kelamin") int jenis_kelamin, 
					 @Param("golongan_darah") String golongan_darah, 
					 @Param("agama") String agama, 
					 @Param("status_perkawinan") String status_perkawinan, 
					 @Param("pekerjaan") String pekerjaan, 
					 @Param("is_wni") int is_wni, 
					 @Param("status_dalam_keluarga") String status_dalam_keluarga,
					 @Param("is_wafat") int is_wafat, 
					 @Param("id_keluarga") String id_keluarga, 
					 @Param("nik") String nik);
	
	@Select("SELECT MAX(id)+1 FROM penduduk")
	String setIdPenduduk();

	@Select("SELECT id FROM kelurahan WHERE nama_kelurahan LIKE #{nama_kelurahan2}")
	String setKelurahanNama(@Param("nama_kelurahan2") String nama_kelurahan2);

	@Select("SELECT id_kecamatan FROM kelurahan WHERE id = #{id_kelurahan}")
	String setKecamatanIdKelurahan(@Param("id_kelurahan") String id_kelurahan);
	
	@Select("select nomor_kk from keluarga where nomor_kk LIKE #{halfNKK} ORDER BY nomor_kk DESC LIMIT 1")
	String selectNkkAvail(@Param("halfNKK")String halfNKK);
	
	@Select("SELECT MAX(id)+1 FROM keluarga")
	String setIdKeluarga();

	@Insert("insert into keluarga (alamat,RT,RW,id_kelurahan,nomor_kk,id) values ("+
			"#{alamat},#{RT},#{RW},#{id_kelurahan2},#{nkk},#{id})")
	void addKeluarga(@Param("alamat") String alamat, 
					 @Param("RT") String RT,
					 @Param("RW") String RW,
					 @Param("id_kelurahan2") int id_kelurahan2,
					 @Param("nkk") String nkk,
					 @Param("id") String id);
	
	@Update("UPDATE penduduk SET nama=#{nama}, nik=#{nik}, tempat_lahir=#{tempat_lahir}, tanggal_lahir=#{tanggal_lahir}, " +
			"jenis_kelamin=#{jenis_kelamin}, is_wni=#{is_wni}, id_keluarga=#{id_keluarga}, agama=#{agama}, pekerjaan=#{pekerjaan}, " + 
			"status_perkawinan=#{status_perkawinan}, status_dalam_keluarga=#{status_dalam_keluarga}, golongan_darah=#{golongan_darah}, " + 
			"is_wafat=#{is_wafat} WHERE id=#{id}")
	void updatePenduduk(PendudukModel penduduk2);
	
	@Select("select nama_kelurahan from kelurahan where id = #{id_kelurahan}")
	String cekNamaKelurahan(@Param("id_kelurahan") String id_kelurahan);
	
	@Update("update penduduk set nik = #{nik} where id=#{id}")
	void updateNIK(@Param("nik") String nik, @Param("id") String id);
	
	@Update("update keluarga set nomor_kk=#{nomor_kk}, alamat=#{alamat}, RT=#{RT}, RW=#{RW}, id_kelurahan=#{id_kelurahan} "+ 
			"where id=#{id}")
	void updateKeluarga(KeluargaModel keluarga2);

	@Update("update penduduk set is_wafat = 1 where nik = #{nik}")
	void updateKematianPenduduk(PendudukModel penduduk);
}
