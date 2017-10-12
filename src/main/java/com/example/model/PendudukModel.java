package com.example.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel {
	
	private String id, nik, nama, tempat_lahir, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah; 
	private int is_wni,jenis_kelamin,is_wafat;
	private String tanggal_lahir;
	private String is_wni2,is_wafat2;
	
	public void changeWNI() {
		if(is_wni == 1) {
			is_wni2 = "WNI";
		} else {
			is_wni2 = "WNA";
		}
	}
	
	public void statusWafat() {
		if(is_wafat == 0) {
			is_wafat2 = "Still Alive and Kickin'";
		} else {
			is_wafat2 = "Modyar";
		}
	}
	

}
