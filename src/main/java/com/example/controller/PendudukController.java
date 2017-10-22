package com.example.controller;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.service.PendudukService;


@Controller
public class PendudukController
{
    @Autowired
    PendudukService pendudukDAO;


    @RequestMapping("/")
    public String index ()
    {
        return "lihat-data";
    }


    @RequestMapping("/penduduk/tambah")
    public String add ()
    {
        return "form-add";
    }
//
//
    @RequestMapping(value="/penduduk/tambah/submit", method = RequestMethod.POST)
    public String addSubmit ( Model model, 
            @RequestParam(value = "nama", required = false) String nama,
            @RequestParam(value = "tempat_lahir", required = false) String tempat_lahir,
            @RequestParam(value = "tanggal_lahir", required = false) String tanggal_lahir,
            @RequestParam(value = "jenis_kelamin", required = false) String jenis_kelamin,
            @RequestParam(value = "golongan_darah", required = false) String golongan_darah,
            @RequestParam(value = "agama", required = false) String agama,
            @RequestParam(value = "status_perkawinan", required = false) String status_perkawinan,
            @RequestParam(value = "pekerjaan", required = false) String pekerjaan,
            @RequestParam(value = "is_wni", required = false) String is_wni,
            @RequestParam(value = "status_dalam_keluarga", required = false) String status_dalam_keluarga,
            @RequestParam(value = "is_wafat", required = false) String is_wafat,
            @RequestParam(value = "id_keluarga", required = false) String id_keluarga)
    {
        //StudentModel student = new StudentModel (npm, name, gpa);
        //studentDAO.addStudent (student);
    	
    	String idKecamatan = pendudukDAO.setIdKecamatan(id_keluarga);
    	String idKota = pendudukDAO.setIdKota(idKecamatan);
    	
    	
    	int idKecamatan2 = Integer.parseInt(idKecamatan);
    	if(idKecamatan2 < 10) {
    		idKecamatan = "0" + idKecamatan;
    	}
    	
    	int idKota2 = Integer.parseInt(idKota);
    	if(idKota2 < 10) {
    		idKota = "0" + idKota;
    	}

    	
    	String nik = pendudukDAO.setNik(idKecamatan,idKota,tanggal_lahir,jenis_kelamin);
        String id = pendudukDAO.setIdPenduduk();
    	
    	int jenis_kelamin2 = Integer.parseInt(jenis_kelamin);
    	int is_wni2 = Integer.parseInt(is_wni);
    	int is_wafat2 = Integer.parseInt(is_wafat);
    	int id2 = Integer.parseInt(id);
    	
    	pendudukDAO.addPenduduk(id2, nama, tempat_lahir, tanggal_lahir ,jenis_kelamin2, golongan_darah, agama, 
    			status_perkawinan, pekerjaan, is_wni2, status_dalam_keluarga, is_wafat2, id_keluarga, nik);
    	
    	model.addAttribute("nik", nik);
    	return "success-add";
    }
    
    @RequestMapping("/keluarga/tambah")
    public String addKeluarga ()
    {
        return "form-add-keluarga";
    }
    
    @RequestMapping(value="/keluarga/tambah/submit", method = RequestMethod.POST)
    public String addKeluargaSubmit ( Model model, 
    		 @RequestParam(value = "alamat", required = false) String alamat,						
    		 @RequestParam(value = "RT", required = false) String RT,
    		 @RequestParam(value = "RW", required = false) String RW,
    		 @RequestParam(value = "nama_kelurahan", required = false) String nama_kelurahan
    		)
    {
    	String date = pendudukDAO.setDate();
    	String id_kelurahan = pendudukDAO.setKelurahanNama(nama_kelurahan);
    	String idKecamatan = pendudukDAO.setKecamatanIdKelurahan(id_kelurahan);
    	String idKota = pendudukDAO.setIdKota(idKecamatan);
    	
    	int idKecamatan2 = Integer.parseInt(idKecamatan);
    	if(idKecamatan2 < 10) {
    		idKecamatan = "0" + idKecamatan;
    	}
    	
    	int idKota2 = Integer.parseInt(idKota);
    	if(idKota2 < 10) {
    		idKota = "0" + idKota;
    	}
    	
    	String nkk = pendudukDAO.setNkk(date, idKecamatan, idKota);
    	String id = pendudukDAO.setIdKeluarga();
    	
    	int id2 = Integer.parseInt(id);
    	int id_kelurahan2 = Integer.parseInt(id_kelurahan);
    	
    	pendudukDAO.addKeluarga(alamat,RT,RW,id_kelurahan2,nkk,id);
    	
    	model.addAttribute("nkk", nkk);
    	return "success-add-keluarga";
    }


    @RequestMapping("/penduduk")
    public String pendudukView (Model model,
            @RequestParam(value = "nik", required = false) String nik)
    {
        PendudukModel penduduk = pendudukDAO.selectPenduduk (nik);
        
        if (penduduk != null) {
        	KeluargaModel keluarga = pendudukDAO.selectKeluargaId(penduduk.getId_keluarga());
            KelurahanModel kelurahan = pendudukDAO.selectKelurahanId(keluarga.getId_kelurahan());
            KecamatanModel kecamatan = pendudukDAO.selectKecamatanId(kelurahan.getId_kecamatan());
            KotaModel kota = pendudukDAO.selectKotaId(kecamatan.getId_kota());
        	model.addAttribute ("penduduk", penduduk);
            model.addAttribute("keluarga", keluarga);
            model.addAttribute("kelurahan", kelurahan);
            model.addAttribute("kecamatan", kecamatan);
            model.addAttribute("kota", kota);
            return "view";
        } else {
            model.addAttribute ("nik", nik);
            return "not-found";
        }
    }
    

    @RequestMapping("/penduduk/{nik}")
    public String pendudukViewPath (Model model,
            @PathVariable(value = "nik") String nik)
    {
    	PendudukModel penduduk = pendudukDAO.selectPenduduk (nik);
        KeluargaModel keluarga = pendudukDAO.selectKeluargaId(penduduk.getId_keluarga());
        KelurahanModel kelurahan = pendudukDAO.selectKelurahanId(keluarga.getId_kelurahan());
        KecamatanModel kecamatan = pendudukDAO.selectKecamatanId(kelurahan.getId_kecamatan());
        KotaModel kota = pendudukDAO.selectKotaId(kecamatan.getId_kota());
        
        if (penduduk != null) {
        	model.addAttribute ("penduduk", penduduk);
            model.addAttribute("keluarga", keluarga);
            model.addAttribute("kelurahan", kelurahan);
            model.addAttribute("kecamatan", kecamatan);
            model.addAttribute("kota", kota);
            return "view";
        } else {
            model.addAttribute ("nik", nik);
            return "not-found";
        }
    }
    
    @RequestMapping("/keluarga")
    public String keluargaView (Model model,
            @RequestParam(value = "nkk", required = false) String nkk)
    {
    	KeluargaModel keluarga = pendudukDAO.selectKeluargaNkk(nkk);
    	KelurahanModel kelurahan = pendudukDAO.selectKelurahanId(keluarga.getId_kelurahan());
        KecamatanModel kecamatan = pendudukDAO.selectKecamatanId(kelurahan.getId_kecamatan());
        KotaModel kota = pendudukDAO.selectKotaId(kecamatan.getId_kota());
    	
    	if (keluarga != null) {
    		keluarga.setPenduduks(pendudukDAO.cekIsTidakBerlaku(keluarga.getPenduduks()));
    		model.addAttribute("keluarga", keluarga);
            model.addAttribute("kelurahan", kelurahan);
            model.addAttribute("kecamatan", kecamatan);
            model.addAttribute("kota", kota);
            if(keluarga.getPenduduks() == null) {
            	return "keluarga-tidak-berlaku";
            } else {
            	return "viewKeluarga";
            }
        } else {
            model.addAttribute ("nkk", nkk);
            return "KeluargaNotFound";
        }
    }
    
    @RequestMapping("/penduduk/ubah/{nik}")
    public String update (Model model, @PathVariable(value="nik") String nik) {
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
    	if(penduduk != null) {
    		model.addAttribute("penduduk",penduduk);
    		return "form-update";
    	} else {
    		model.addAttribute("nik",nik);
    		return "not-found";
    	}
    }
    
    @RequestMapping(value="/penduduk/ubah/submit", method = RequestMethod.POST)
    public String updateSubmit(
    		@RequestParam(value = "nik", required = false) String nik,
    		@RequestParam(value = "id", required = false) String id,
    		@RequestParam(value = "nama", required = false) String nama,
    		@RequestParam(value = "tempat_lahir", required = false) String tempat_lahir,
    		@RequestParam(value = "tanggal_lahir", required = false) String tanggal_lahir,
    		@RequestParam(value = "jenis_kelamin", required = false) String jenis_kelamin,
    		@RequestParam(value = "golongan_darah", required = false) String golongan_darah,
    		@RequestParam(value = "agama", required = false) String agama,
    		@RequestParam(value = "status_perkawinan", required = false) String status_perkawinan,
    		@RequestParam(value = "pekerjaan", required = false) String pekerjaan,
    		@RequestParam(value = "is_wni", required = false) String is_wni,
    		@RequestParam(value = "status_dalam_keluarga", required = false) String status_dalam_keluarga,
    		@RequestParam(value = "is_wafat", required = false) String is_wafat,
    		@RequestParam(value = "id_keluarga", required = false) String id_keluarga)
    {
    	PendudukModel penduduk = pendudukDAO.selectPenduduk (nik); 
    	if( !(penduduk.getId_keluarga().equalsIgnoreCase(id_keluarga) && penduduk.getTanggal_lahir().equalsIgnoreCase(tanggal_lahir)) ){

    		String idKecamatan = pendudukDAO.setIdKecamatan(id_keluarga);
    		String idKota = pendudukDAO.setIdKota(idKecamatan);

    		int idKecamatan2 = Integer.parseInt(idKecamatan);
    		if(idKecamatan2 < 10) {
    			idKecamatan = "0" + idKecamatan;
    		}

    		int idKota2 = Integer.parseInt(idKota);
    		if(idKota2 < 10) {
    			idKota = "0" + idKota;
    		}

    		nik = pendudukDAO.setNik(idKecamatan,idKota,tanggal_lahir,jenis_kelamin);
    	} 

    	int jenis_kelamin2 = Integer.parseInt(jenis_kelamin);
    	int is_wni2 = Integer.parseInt(is_wni);
    	int is_wafat2 = Integer.parseInt(is_wafat);
    	
    	PendudukModel penduduk2 = new PendudukModel(id, nik, nama, tempat_lahir, id_keluarga, 
    			agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wni2,
    			jenis_kelamin2, is_wafat2, tanggal_lahir);
    	
    	pendudukDAO.updatePenduduk(penduduk2);
    	return "success-update";
    }

    @RequestMapping("/keluarga/ubah/{nkk}")
    public String updateKeluarga (Model model, @PathVariable(value="nkk") String nkk) {
		KeluargaModel keluarga = pendudukDAO.selectKeluargaNkk(nkk);
    	if(keluarga != null) {
    		String namaKelurahan = pendudukDAO.cekNamaKelurahan(keluarga.getId_kelurahan());
    		model.addAttribute("namaKelurahan",namaKelurahan);
    		model.addAttribute("keluarga",keluarga);
    		return "form-update-keluarga";
    	} else {
    		model.addAttribute("nkk",nkk);
    		return "KeluargaNotFound";
    	}
    }
    
    @RequestMapping(value="/keluarga/ubah/submit", method = RequestMethod.POST)
    public String updateSubmitKeluarga(
    		@RequestParam(value = "nomor_kk", required = false) String nomor_kk,
    		@RequestParam(value = "id", required = false) String id,
    		@RequestParam(value = "alamat", required = false) String alamat,
    		@RequestParam(value = "RT", required = false) String RT,
    		@RequestParam(value = "RW", required = false) String RW,
    		@RequestParam(value = "nama_kelurahan", required = false) String nama_kelurahan
    		)
    {
    	KeluargaModel keluarga = pendudukDAO.selectKeluargaNkk(nomor_kk); 
    	String id_kelurahan = pendudukDAO.setKelurahanNama(nama_kelurahan);
    	if( !(keluarga.getId_kelurahan().equalsIgnoreCase(id_kelurahan)) ){
    		String date = pendudukDAO.setDate();
    		String idKecamatan = pendudukDAO.setKecamatanIdKelurahan(id_kelurahan);
        	String idKota = pendudukDAO.setIdKota(idKecamatan);
        	
        	int idKecamatan2 = Integer.parseInt(idKecamatan);
        	if(idKecamatan2 < 10) {
        		idKecamatan = "0" + idKecamatan;
        	}
        	
        	int idKota2 = Integer.parseInt(idKota);
        	if(idKota2 < 10) {
        		idKota = "0" + idKota;
        	}
        	nomor_kk = pendudukDAO.setNkk(date, idKecamatan, idKota);
        	
        	ListIterator<PendudukModel> litr = keluarga.getPenduduks().listIterator();
        	while(litr.hasNext()) {
        		PendudukModel penduduk = litr.next();
        		String jenis_kelamin = "" + penduduk.getJenis_kelamin();
        		String nik = pendudukDAO.setNik(idKecamatan,idKota,penduduk.getTanggal_lahir(),jenis_kelamin); 
        		pendudukDAO.updateNIK(nik,penduduk.getId());
        	}
    	} 
    	
    	KeluargaModel keluarga2 = new KeluargaModel(id, nomor_kk, alamat, RT, RW, id_kelurahan,keluarga.getIs_tidak_berlaku(),
    												keluarga.getPenduduks());
    	
    	pendudukDAO.updateKeluarga(keluarga2);

    	return "success-update-keluarga";
    }
    
    
    @RequestMapping("/penduduk/cari")
    public String cariPendudukLengkap(Model model,
    		@RequestParam(value = "kt", required = false) String kt,
    		@RequestParam(value = "kc", required = false) String kc,
    		@RequestParam(value = "kl", required = false) String kl
    		) 
    {
    	if(kt != null) {
    		if(kc != null) {
    			if(kl != null) {
    				KotaModel kota = pendudukDAO.selectKotaId(kt);
        			model.addAttribute("kota", kota);
        			KecamatanModel kecamatan = pendudukDAO.selectKecamatanId(kc);
        			model.addAttribute("kecamatan",kecamatan);
        			KelurahanModel kelurahan = pendudukDAO.selectKelurahanId(kl);
        			//kelurahan.setKeluargas(pendudukDAO.setKeluarga(kelurahan));
        			model.addAttribute("kelurahan", kelurahan);
        			List<PendudukModel> penduduks = pendudukDAO.selectPenduduks2(kl);
        			model.addAttribute("penduduks", penduduks);
        			return "select-keluarga";
    			} else {
    				KotaModel kota = pendudukDAO.selectKotaId(kt);
        			model.addAttribute("kota", kota);
        			KecamatanModel kecamatan = pendudukDAO.selectKecamatanId(kc);
        			model.addAttribute("kecamatan",kecamatan);
        			return "select-kelurahan";
    			}
    		} else {
    			KotaModel kota = pendudukDAO.selectKotaId(kt);
    			model.addAttribute("kota", kota);
    			return "select-kecamatan";
    		} 
    	} else {
    		return "select-kota";
    	}
    }
    
    @RequestMapping(value="/penduduk/mati", method = RequestMethod.POST)
    public String matikanPenduduk(Model model,
    		@RequestParam(value = "nik", required = false) String nik) 
    {
    	System.out.println(nik);
    	
    	PendudukModel penduduk = pendudukDAO.selectPenduduk (nik); 
    	
    	System.out.println(penduduk.getIs_wafat());
    	
    	KeluargaModel keluarga = pendudukDAO.selectKeluargaId(penduduk.getId_keluarga());
        KelurahanModel kelurahan = pendudukDAO.selectKelurahanId(keluarga.getId_kelurahan());
        KecamatanModel kecamatan = pendudukDAO.selectKecamatanId(kelurahan.getId_kecamatan());
        KotaModel kota = pendudukDAO.selectKotaId(kecamatan.getId_kota());
        pendudukDAO.updateKematianPenduduk(penduduk);
    	
        model.addAttribute("penduduk", penduduk);
        model.addAttribute("keluarga", keluarga);
        model.addAttribute("kelurahan", kelurahan);
        model.addAttribute("kecamatan", kecamatan);
        model.addAttribute("kota", kota);
    	
        return "redirect:/penduduk/view?nik=" + nik; 
    }
}
