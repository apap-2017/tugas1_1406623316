package com.example.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {
	
	private String id, nomor_kk, alamat, RT, RW, id_kelurahan;
	private int is_tidak_berlaku;
	private List<PendudukModel> penduduks;
}
