package com.example.service;

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
	
}