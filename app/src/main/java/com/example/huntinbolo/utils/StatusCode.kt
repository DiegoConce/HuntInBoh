package com.example.huntinbolo.utils

enum class StatusCode(val code: Int) {
    OK(200),
    Created(201),
    BadRequest(400),
    NotFound(404),
    Conflict(409),
    ServiceUnavailable(503)
}