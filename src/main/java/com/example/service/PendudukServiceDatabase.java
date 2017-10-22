package com.example.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
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
		String halfNIK = "31" + idKota + idKecamatan + tanggal + bulan + tahun + "%";
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

	@Override
	public String setDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		String date = dtf.format(localDate);
		return date;
	}

	@Override
	public String setKelurahanNama(String nama_kelurahan) {
		String nama_kelurahan2 = "%" + nama_kelurahan + "%";
		return pendudukMapper.setKelurahanNama(nama_kelurahan2);
	}

	@Override
	public String setKecamatanIdKelurahan(String id_kelurahan) {
		return pendudukMapper.setKecamatanIdKelurahan(id_kelurahan);
	}

	@Override
	public String setNkk(String date, String idKecamatan, String idKota) {
		String[] date2 = date.split("/");
		String tahun = date2[0].substring(2,4);
		String bulan = date2[1];
		String tanggal = date2[2];
		String halfNKK = "31" + idKota + idKecamatan + tanggal + bulan + tahun + "%";
		System.out.println(halfNKK);
		String nkkAvail = pendudukMapper.selectNkkAvail(halfNKK);
		if(nkkAvail != null) {
			System.out.println(nkkAvail);
			Long nkk1 = Long.parseLong(nkkAvail);
			nkk1 = nkk1 + 1;
			String nkkFinal = Long.toString(nkk1);
			System.out.println(nkkFinal);
			return nkkFinal;
		} else {
			String nkkFinal = "31" + idKota + idKecamatan + tanggal + bulan + tahun + "0001";
			System.out.println(nkkFinal);
			return nkkFinal;
		}
	}

	@Override
	public String setIdKeluarga() {
		return pendudukMapper.setIdKeluarga();
	}

	@Override
	public void addKeluarga(String alamat, String RT, String RW, int id_kelurahan2, String nkk, String id) {
		// TODO Auto-generated method stub
		pendudukMapper.addKeluarga(alamat,RT,RW,id_kelurahan2,nkk,id);
		
	}

	@Override
	public void updatePenduduk(PendudukModel penduduk2) {
		// TODO Auto-generated method stub
		pendudukMapper.updatePenduduk(penduduk2);
	}

	@Override
	public String cekNamaKelurahan(String id_kelurahan) {
		// TODO Auto-generated method stub
		return pendudukMapper.cekNamaKelurahan(id_kelurahan);
	}

	@Override
	public void updateNIK(String nik, String id) {
		// TODO Auto-generated method stub
		pendudukMapper.updateNIK(nik,id);
	}

	@Override
	public void updateKeluarga(KeluargaModel keluarga2) {
		// TODO Auto-generated method stub
		pendudukMapper.updateKeluarga(keluarga2);
	}

	@Override
	public List<KeluargaModel> setKeluarga(KelurahanModel kelurahan) {
		// TODO Auto-generated method stub
		List<KeluargaModel> keluargas2 = kelurahan.getKeluargas();
		for(int ii =0; ii< keluargas2.size();ii++){
			String id = keluargas2.get(ii).getId();
			KeluargaModel klg = selectKeluargaId(id);
			keluargas2.get(ii).setPenduduks(klg.getPenduduks());
		}
		return keluargas2;
	}

	@Override
	public List<PendudukModel> selectPenduduks2(String kl) {
		// TODO Auto-generated method stub
		List<PendudukModel> penduduks= pendudukMapper.selectPenduduks2(kl);
		return penduduks;
	}

	@Override
	public void updateKematianPenduduk(PendudukModel penduduk) {
		// TODO Auto-generated method stub
		pendudukMapper.updateKematianPenduduk(penduduk);
	}

	@Override
	public List<PendudukModel> cekIsTidakBerlaku(List<PendudukModel> penduduks) {
		// TODO Auto-generated method stub
		Iterator<PendudukModel> i = penduduks.iterator();
		while (i.hasNext()) {
			PendudukModel s = i.next(); // must be called before you can call i.remove()
			// Do something
			if(s.getIs_wafat() == 1) {
				i.remove();
			}
		}
//		for(int i = 0; i < penduduks.size(); i++) {
//			System.out.println(penduduks.get(i-1).getNama());
//			if (penduduks.get(i).getIs_wafat() == 1) {
//				System.out.println(penduduks.get(i-1).getNama());
//				penduduks.remove(i);
//			}
//		}
		return penduduks;
	}

}
