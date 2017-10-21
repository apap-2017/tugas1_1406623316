package com.example.service;

import java.util.List;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
//import java.util.List;
import com.example.model.PendudukModel;

public interface PendudukService {

	PendudukModel selectPenduduk (String nik);
	KeluargaModel selectKeluargaId (String id); 
	KelurahanModel selectKelurahanId (String id);
	KecamatanModel selectKecamatanId (String id);
	KotaModel selectKotaId (String id);
	KeluargaModel selectKeluargaNkk(String nkk);
	
	String setIdKota (String idKecamatan);
	String setIdKecamatan (String id_keluarga);
	String setNik(String idKecamatan,String idKota, String tanggal_lahir, String jenis_kelamin);
	void addPenduduk(int id, String nama, String tempat_lahir,String tanggal_lahir,int jenis_kelamin, String golongan_darah, String agama,
			String status_perkawinan, String pekerjaan, int is_wni, String status_dalam_keluarga, int is_wafat,
			String id_keluarga, String nik);
	
	String setIdPenduduk();
	String setDate();
	String setKelurahanNama(String nama_kelurahan);
	String setKecamatanIdKelurahan(String id_kelurahan);
	String setNkk(String date, String idKecamatan, String idKota);
	String setIdKeluarga();
	void addKeluarga(String alamat, String RT, String RW, int id_kelurahan2, String nkk, String id);
	void updatePenduduk(PendudukModel penduduk2);
	String cekNamaKelurahan(String id_kelurahan);
	void updateNIK(String nik, String id);
	void updateKeluarga(KeluargaModel keluarga2);
<<<<<<< HEAD
	List<KeluargaModel> setKeluarga(KelurahanModel kelurahan);
	List<PendudukModel> selectPenduduks2(String kl);
	
=======
>>>>>>> 509a3bd1340f626c793b0e2b3c7dd69929753307
	
}
