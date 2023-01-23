package com.example.huntinboh.model


data class Poi(
    var id: Int,
    var idOverpass: Int?,
    var name: String,
    var tags: HashMap<String,String>,
    var rank: Int,
    var description: String?,
    var operational_status: String?,
    //location
    var lat: String,
    var long: String,
    var added_by: Int,
    var category: String,
    var distance: Float?,
    var duration: Float?
)