package com.joeahkim.admin_kashtips

data class Tip(
    val country: String = "",
    val league: String = "",
    val homeTeam: String = "",
    val time: String = "",
    val awayTeam: String = "",
    val prediction: String = ""
)
