package com.example.projectpenelitian.api.response

import com.google.gson.annotations.SerializedName

data class DetailPlantResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("data")
	val data: List<DataPlant?>? = null

)

data class DataPlant(

	@field:SerializedName("ciri_ciri")
	val ciriCiri: CiriCiri? = null,

	@field:SerializedName("nama_tanaman")
	val namaTanaman: String? = null,

	@field:SerializedName("jenis_penyakit")
	val jenisPenyakit: String? = null,

	@field:SerializedName("cara_pengobatan")
	val caraPengobatan: CaraPengobatan? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("cara_pengobatan_alami")
	val caraPengobatanAlami: CaraPengobatanAlami? = null,

	@field:SerializedName("cara_perawatan")
	val caraPerawatan: CaraPerawatan? = null
)

data class CiriCiri(

	@field:SerializedName("0")
	val jsonMember0: String? = null,

	@field:SerializedName("1")
	val jsonMember1: String? = null,

	@field:SerializedName("2")
	val jsonMember2: String? = null
)


data class CaraPerawatan(

	@field:SerializedName("0")
	val jsonMember0: String? = null,

	@field:SerializedName("1")
	val jsonMember1: String? = null,

	@field:SerializedName("2")
	val jsonMember2: String? = null

)

data class CaraPengobatan(

	@field:SerializedName("0")
	val jsonMember0: String? = null,

	@field:SerializedName("1")
	val jsonMember1: String? = null,

	@field:SerializedName("2")
	val jsonMember2: String? = null
)

data class CaraPengobatanAlami(

	@field:SerializedName("0")
	val jsonMember0: String? = null,

	@field:SerializedName("1")
	val jsonMember1: String? = null,

	@field:SerializedName("2")
	val jsonMember2: String? = null
)
