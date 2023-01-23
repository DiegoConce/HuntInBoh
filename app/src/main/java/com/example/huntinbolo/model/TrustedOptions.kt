package com.example.huntinbolo.model

data class TrustedOptions(
    var msgNo: Int,
    var anonimityLevel: Int,
    var spatialToleranceX: Int,
    var spatialToleranceY: Int,
    var temporalTolerance: Int,
    var requestsTolerance: Int
) {
    constructor() : this(
        1,
        1,
        1,
        1,
        1,
        1,
    )
}