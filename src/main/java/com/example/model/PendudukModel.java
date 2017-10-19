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

}
