package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KelurahanModel {
	private String id, id_kecamatan, kode_kelurahan, nama_kelurahan, kode_pos;
}
