package com.example.jetareader.model

data class MUser(val id:String?,
    val userId:String,
    val displayName:String,
    val avatarUrl:String,
    val quote:String,
    val profession:String
){
    fun toMap():MutableMap<String,Any>{
        val map = mutableMapOf<String,Any>()
        map["user_id"] = userId
        map["display_name"] = displayName
        map["quote"] = quote
        map["profession"] = profession
        map["avatar_url"] = avatarUrl
        return map
    }
}
