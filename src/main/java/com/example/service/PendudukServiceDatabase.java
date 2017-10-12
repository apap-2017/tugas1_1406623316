package com.example.service;

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
public class PendudukServiceDatabase implements PendudukService {

	@Autowired
    private PendudukMapper pendudukMapper;
	
	 @Override
	 public PendudukModel selectPenduduk (String nik)
	 {
	   log.info ("select penduduk with nik {}", nik);
	   return pendudukMapper.selectPenduduk (nik);
	 }
	 
	 @Override
	 public KeluargaModel selectKeluargaId (String id) 
	 {
		 log.info ("select keluarga with id {}", id); 
		 return pendudukMapper.selectKeluargaId (id);
	 }

	@Override
	public KelurahanModel selectKelurahanId(String id) {
		log.info ("select kelurahan with id {}", id); 
		return pendudukMapper.selectKelurahanId (id);
	}

	@Override
	public KecamatanModel selectKecamatanId(String id) {
		log.info ("select kecamatan with id {}", id);
		return pendudukMapper.selectKecamatanId (id);
	}

	@Override
	public KotaModel selectKotaId(String id) {
		log.info ("select kota with id {}", id);
		return pendudukMapper.selectKotaId(id);
	}

	@Override
	public KeluargaModel selectKeluargaNkk(String nkk) {
		log.info ("select keluarga with nkk {}", nkk); 
		 return pendudukMapper.selectKeluargaNkk (nkk);
	}

	@Override
	public String setIdKota(String idKecamatan) {
		// TODO Auto-generated method stub
		return pendudukMapper.selectIdKota (idKecamatan);
	}

	@Override
	public String setIdKecamatan(String id_keluarga) {
		// TODO Auto-generated method stub
		return pendudukMapper.selectIdKecamatan (id_keluarga);
	}

	@Override
	public String setNik(String idKecamatan, String idKota, String tanggal_lahir, String jenis_kelamin) {
		// TODO Auto-generated method stub
		String[] date = tanggal_lahir.split("-");
		String tanggal = date[2];
		if(jenis_kelamin.equalsIgnoreCase("1")) {
			int tanggal2 = Integer.parseInt(tanggal);
			tanggal2 = tanggal2 + 40;
			tanggal = "" + tanggal2;
		}
		
		String bulan = date[1];
		String tahun = date[0].substring(2,4);
		String halfNIK = "'31" + idKota + idKecamatan + tanggal + bulan + tahun + "%'";
		System.out.println(halfNIK);
		String nikAvail = pendudukMapper.selectNikAvail(halfNIK);
		if(nikAvail != null) {
			System.out.println(nikAvail);
			Long nik1 = Long.parseLong(nikAvail);
			nik1 = nik1 + 1;
			String nikFinal = Long.toString(nik1);
			System.out.println(nikFinal);
			return nikFinal;
		} else {
			String nikFinal = "31" + idKota + idKecamatan + tanggal + bulan + tahun + "0001";
			System.out.println(nikFinal);
			return nikFinal;
		}
		
	}

	@Override
	public void addPenduduk(int id, String nama, String tempat_lahir, String tanggal_lahir, int jenis_kelamin, String golongan_darah, String agama,
			String status_perkawinan, String pekerjaan, int is_wni, String status_dalam_keluarga, int is_wafat,
			String id_keluarga, String nik) {
		// TODO Auto-generated method stub
		String print = id + " " + nama + " " + tempat_lahir + " " +  tanggal_lahir + " " + jenis_kelamin + " " +  golongan_darah + " " + agama + " " + 
    			status_perkawinan + " " + pekerjaan +  " " + is_wni + " " + status_dalam_keluarga + " " + is_wafat + " " + id_keluarga + " " + nik;
		System.out.println(print);
		
		pendudukMapper.addPenduduk(id, nama, tempat_lahir,tanggal_lahir,jenis_kelamin, golongan_darah, agama, 
    			status_perkawinan, pekerjaan, is_wni, status_dalam_keluarga, is_wafat, id_keluarga, nik);
		
	}

	@Override
	public String setIdPenduduk() {
		return pendudukMapper.setIdPenduduk();
	}

	
}
