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
			"golongan_darah, tanggal_lahir from penduduk where nik = #{nik}")
    PendudukModel selectPenduduk (@Param("nik") String nik);
	
	@Select("select id, nomor_kk, alamat, RT, RW, id_kelurahan, is_tidak_berlaku " +
			"from keluarga where id = #{id}")
	KeluargaModel selectKeluargaId(@Param("id") String id);

	@Select("select id, id_kecamatan, kode_kelurahan, nama_kelurahan, kode_pos " + 
			"from kelurahan where id = #{id}")	
	KelurahanModel selectKelurahanId(@Param("id") String id);
	
	@Select("select id, id_kota, kode_kecamatan, nama_kecamatan " + 
			"from kecamatan where id = #{id}")
	KecamatanModel selectKecamatanId (@Param("id") String id);
	
	@Select("select id, kode_kota, nama_kota " + 
			"from kota where id = #{id}")
	KotaModel selectKotaId (@Param("id") String id);
	
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
	
	@Select("select id, nik, nama, tempat_lahir, jenis_kelamin, " + 
			"agama, pekerjaan, status_perkawinan, status_dalam_keluarga, " + 
			"golongan_darah, tanggal_lahir from penduduk where id_keluarga = #{id_keluarga}")
	List<PendudukModel> selectPenduduks(@Param("id_keluarga") String id_keluarga);
	
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
}
