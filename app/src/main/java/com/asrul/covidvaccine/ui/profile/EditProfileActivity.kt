package com.asrul.covidvaccine.ui.profile

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.asrul.covidvaccine.R
import com.asrul.covidvaccine.data.api.ApiConfig
import com.asrul.covidvaccine.data.api.response.UserData
import com.asrul.covidvaccine.data.api.response.UserResponse
import com.asrul.covidvaccine.databinding.ActivityEditProfileBinding
import com.asrul.covidvaccine.utils.SessionManager
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : AppCompatActivity() {

    companion object {
        const val FILE_REQUEST = 102
    }

    private lateinit var binding: ActivityEditProfileBinding
    private var calendar = Calendar.getInstance()
    private lateinit var sessionManager: SessionManager
    private lateinit var gender: String
    private lateinit var nik: String
    private lateinit var birthday: String
    private lateinit var photo_path: String
    private lateinit var name: String
    private lateinit var phone: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Edit Profile"

        sessionManager = SessionManager(this)

        name = sessionManager.getUserDetail()[SessionManager.NAME].toString()
        phone = sessionManager.getUserDetail()[SessionManager.PHONE].toString()
        if (sessionManager.getUserDetail()[SessionManager.NIK].isNullOrEmpty()) nik = ""
        if (sessionManager.getUserDetail()[SessionManager.BIRTHDAY].isNullOrEmpty()) birthday = ""
        gender = sessionManager.getUserDetail()[SessionManager.GENDER].toString()
        photo_path = sessionManager.getUserDetail()[SessionManager.PHOTO_PATH].toString()

        binding.edtPhone.setText(phone)
        binding.edtName.setText(name)
        binding.edtNik.setText(nik)
        binding.edtBirthday.setText(birthday)

        if (photo_path == "") {
            Glide.with(this)
                .load(photo_path)
                .placeholder(R.drawable.ic_baseline_refresh_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .circleCrop()
                .into(binding.imgProfile)
        } else {
            Glide.with(this)
                .load(R.drawable.ic_baseline_account_circle_24)
                .circleCrop()
                .into(binding.imgProfile)
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.gender_list,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.genderSpinner.adapter = adapter
        }

        when(gender) {
            "Laki-laki" -> binding.genderSpinner.setSelection(1)
            "Perempuan" -> binding.genderSpinner.setSelection(2)
        }

        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val birthday = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            binding.edtBirthday.setText(sdf.format(calendar.time))
        }

        binding.edtBirthday.setOnClickListener {
            DatePickerDialog(
                this, birthday,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.btnSave.setOnClickListener{
            if (binding.edtNik.text?.length != 16 && binding.edtNik.text?.length != 0) {
                binding.edtNik.requestFocus()
                binding.edtNik.error = "NIK tidak valid!"
                return@setOnClickListener
            }
            if (binding.edtName.text.isNullOrEmpty()) {
                binding.edtName.requestFocus()
                binding.edtName.error = "Nama tidak boleh kosong!"
                return@setOnClickListener
            }
            if (binding.edtPhone.text.isNullOrEmpty()) {
                binding.edtPhone.requestFocus()
                binding.edtPhone.error = "Nomor Telepon tidak boleh kosong!"
                return@setOnClickListener
            }
            binding.btnSave.text = "menyimpan..."
            binding.progressBar.visibility = View.VISIBLE
            uploadFile()
        }

        binding.imgProfile.setOnClickListener {
            checkPermission()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == FILE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.let {
                val selectedFile = getRealPathFromUri(data.data!!, this)
                photo_path = selectedFile
                Glide.with(this)
                    .load(photo_path)
                    .placeholder(R.drawable.ic_baseline_refresh_24)
                    .error(R.drawable.ic_baseline_broken_image_24)
                    .into(binding.imgProfile)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun createPartFromString(orderString: String): RequestBody? {
        return orderString.toRequestBody(MultipartBody.FORM)
    }

    private fun getRealPathFromUri(uri: Uri, activity: Activity): String {
        val cursor: Cursor? = activity.contentResolver.query(uri, null, null, null, null)
        if (cursor==null) {
            return uri.path.toString()
        } else {
            cursor.moveToFirst()
            val id = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            return cursor.getString(id)
        }
    }

    private fun filePicker() {
        val intent = Intent(Intent.ACTION_PICK)
            .setType("image/*")
        startActivityForResult(Intent.createChooser(intent, "Pilih foto profile"), FILE_REQUEST)
    }

    private fun checkPermission() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    Log.e("Permission", "granted")
                    filePicker()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Log.e("Permission", "denied")
                    Toast.makeText(this@EditProfileActivity, "Permission denied!", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    Log.e("Permission", "rationalshouldbeshown")
                }

            })
            .check()
    }

    private fun uploadFile() {
        val uploadTask = UploadTask()
        uploadTask.execute(photo_path)
    }

    inner class UploadTask: AsyncTask<String, String, String>() {
        override fun doInBackground(vararg strings: String): String {
            return if (upload(strings[0])){
                "true"
            } else {
                "failed"
            }
        }

        private fun upload(path: String): Boolean {
            val file = File(path)
            try {
                if (binding.edtName.text != null) name = binding.edtName.text.toString()
                if (binding.edtPhone.text != null) phone = binding.edtPhone.text.toString()
                if (binding.edtNik.text != null) nik = binding.edtNik.text.toString()
                if (binding.edtBirthday.text != null) birthday = binding.edtBirthday.text.toString()

                if (binding.genderSpinner.selectedItemPosition != 0) {
                    gender = binding.genderSpinner.selectedItem.toString()
                }

                val part: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "photo_path",
                    file.name,
                    RequestBody.create("image/*".toMediaTypeOrNull(), file)
                )


                val profile = HashMap<String, RequestBody>()
                profile.put("name", createPartFromString(name)!!)
                profile.put("phone", createPartFromString(phone)!!)
                profile.put("nik", createPartFromString(nik)!!)
                profile.put("birthday", createPartFromString(birthday)!!)
                profile.put("gender", createPartFromString(gender)!!)

                sessionManager = SessionManager(this@EditProfileActivity)
                val userId = sessionManager.getUserDetail()[SessionManager.ID].toString()
                ApiConfig.getApiUser().postUser(userId, part, profile)
                    .enqueue(object : Callback<UserResponse> {
                        override fun onResponse(
                            call: Call<UserResponse>,
                            response: Response<UserResponse>
                        ) {
                            if (response.body()!!.status.toString() != "fail") {
                                val sessionManager = SessionManager(applicationContext)
                                val loginData: UserData? = response.body()!!.data
                                if (loginData != null) {
                                    sessionManager.createLoginSession(loginData)
                                }
                                finish()
                            } else {
                                Toast.makeText(
                                    this@EditProfileActivity,
                                    response.body()!!.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                            Toast.makeText(this@EditProfileActivity, "Pilih foto profil!", Toast.LENGTH_SHORT).show()
                            runOnUiThread {
                                binding.progressBar.visibility = View.GONE
                                binding.btnSave.text = "simpan"
                            }
                        }

                    })
                return true

            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        }

    }
}