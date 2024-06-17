package com.example.projectpenelitian.api.response

import com.google.gson.annotations.SerializedName

data class BookmarkResponse(

	@field:SerializedName("data")
	val data: List<BookmarkDataItem>,

	@field:SerializedName("message")
	val message: String
)

data class BookmarkDataItem(

	@field:SerializedName("nama_tanaman")
	val namaTanaman: String,

	@field:SerializedName("jenis_penyakit")
	val jenisPenyakit: String,

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("timestamp")
	val timestamp: String,
)
