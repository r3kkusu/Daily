package com.daily.app.domain.model

data class AppConfig(
    var topic: String,
    var country: String,
    var city: String,
    var language: String,
    var filter_limit: String
)