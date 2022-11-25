package com.example.huntinbolo.model

data class Poi(
    var id: Int,
    var name: String,
    var tags: HashMap<String,String>?, // list
    var rank: Int,
    var description: String?,
    var operational_status: String?,
    //location
    var lat: String,
    var long: String,
    var added_by: Int,
    var category: String
)