package com.example.model;

import java.util.Comparator;
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

class PendudukComparatorByAge implements Comparator<PendudukModel> {
    @Override
    public int compare(PendudukModel o1, PendudukModel o2) {
        return o1.getTanggal_lahir().compareTo(o2.getTanggal_lahir());
    }
}
