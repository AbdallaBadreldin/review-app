package com.fstech.review.model

data class User(
    val userId: String?,
    val firstName:String, val secondName:String,
    val email:String,
    val profileImage:String,
    val position:String,
    val age: Long
)
