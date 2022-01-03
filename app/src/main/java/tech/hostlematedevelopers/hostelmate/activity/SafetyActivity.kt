package tech.hostlematedevelopers.hostelmate.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import tech.hostlematedevelopers.hostelmate.databinding.ActivitySafetyBinding

class SafetyActivity : AppCompatActivity() {
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var binding: ActivitySafetyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySafetyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreference = getSharedPreferences("SECURITY_DATA", Context.MODE_PRIVATE)

        binding.btnSave.setOnClickListener {

            val mobile1: Int = binding.etMobileNumber1.text.toString().toInt()
            val mobile2: Int = binding.etMobileNumber2.text.toString().toInt()
            val mobile3: Int = binding.etMobileNumber3.text.toString().toInt()

            val editor: SharedPreferences.Editor = sharedPreference.edit()
            editor.putInt("MOBILE_NUM1", mobile1)
            editor.putInt("MOBILE_NUM2", mobile2)
            editor.putInt("MOBILE_NUM3", mobile3)
            editor.apply()

            Toast.makeText(this, "Information Saved!", Toast.LENGTH_LONG).show()
        }
    }
}