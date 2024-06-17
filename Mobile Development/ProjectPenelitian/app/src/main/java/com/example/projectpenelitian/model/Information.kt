package com.example.projectpenelitian.model

import java.io.Serializable

data class Information(
    val infoName: String,
    val infoImg: Int,
    val infoImg2: Int,
    val infoDesc1: String,
    val infoDesc2: String,
    val infoDesc3: String
): Serializable
