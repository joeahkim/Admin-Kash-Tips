package com.joeahkim.admin_kashtips

data class TodaysTip(
    var id: String = "",
    val country: String = "",
    val league: String ="",
    val homeTeam: String = "",
    val awayTeam: String = "",
    val time: String = "",
    val prediction: String = ""
)
