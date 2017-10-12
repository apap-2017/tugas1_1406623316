package com.example.controller;

import java.util.List;

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
        return "index";
    }


    @RequestMapping("/keluarga/tambah")
    public String add ()
    {
        return "form-add";
    }
//
//
    @RequestMapping(value="/keluarga/tambah/submit", method = RequestMethod.POST)
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


    @RequestMapping("/penduduk/view")
    public String pendudukView (Model model,
            @RequestParam(value = "nik", required = false) String nik)
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


    @RequestMapping("/penduduk/view/{nik}")
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
            model.addAttribute ("keluarga", keluarga);
            model.addAttribute("kelurahan", kelurahan);
            model.addAttribute("kecamatan", kecamatan);
            model.addAttribute("kota", kota);
            return "viewKeluarga";
        } else {
            model.addAttribute ("nkk", nkk);
            return "KeluargaNotFound";
        }
    }

}
