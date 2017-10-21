package com.example.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KelurahanModel {
	private String id, id_kecamatan, kode_kelurahan, nama_kelurahan, kode_pos;
	private List<KeluargaModel> keluargas;
}
