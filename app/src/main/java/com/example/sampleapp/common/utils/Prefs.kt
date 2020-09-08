package com.example.sampleapp.common.utils

import com.chibatching.kotpref.KotprefModel

object Prefs : KotprefModel() {
    var username by nullableStringPref()
    var isLoaded by booleanPref(false)
}
