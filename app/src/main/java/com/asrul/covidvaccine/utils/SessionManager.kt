package com.asrul.covidvaccine.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.asrul.covidvaccine.data.api.response.UserData
import java.util.*

class SessionManager(context: Context?) {

    private val sharedPreferences: SharedPreferences? = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor: SharedPreferences.Editor? = sharedPreferences?.edit()

    companion object {
        const val IS_LOGGED_IN = "isLoggedIn"
        const val ID = "id"
        const val NAME = "name"
        const val EMAIL = "email"
        const val PHONE = "phone"
        const val NIK = "nik"
        const val BIRTHDAY = "birthday"
        const val GENDER = "gender"
        const val PHOTO_PATH = "photo_path"
    }

    fun createLoginSession(user: UserData) {
        editor!!.putBoolean(IS_LOGGED_IN, true)
        editor.putString(ID, user.id)
        editor.putString(NAME, user.name)
        editor.putString(EMAIL, user.email)
        editor.putString(PHONE, user.phone)
        editor.putString(NIK, user.nik)
        editor.putString(BIRTHDAY, user.birthday)
        editor.putString(GENDER, user.gender)
        editor.putString(PHOTO_PATH, user.photo_path)
        editor.commit()
    }

    fun getUserDetail(): HashMap<String, String?> {
        val user = HashMap<String, String?>()
        user[ID] = sharedPreferences!!.getString(ID, null)
        user[NAME] = sharedPreferences.getString(NAME, null)
        user[EMAIL] = sharedPreferences.getString(EMAIL, null)
        user[PHONE] = sharedPreferences.getString(PHONE, null)
        user[NIK] = sharedPreferences.getString(NIK, null)
        user[BIRTHDAY] = sharedPreferences.getString(BIRTHDAY, null)
        user[GENDER] = sharedPreferences.getString(GENDER, null)
        user[PHOTO_PATH] = sharedPreferences.getString(PHOTO_PATH, null)
        return user
    }

    fun logoutSession() {
        editor!!.clear()
        editor.commit()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences!!.getBoolean(IS_LOGGED_IN, false)
    }
}