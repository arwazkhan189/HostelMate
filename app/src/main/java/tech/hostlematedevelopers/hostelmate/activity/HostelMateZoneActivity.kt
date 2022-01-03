package tech.hostlematedevelopers.hostelmate.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import tech.hostlematedevelopers.hostelmate.databinding.ActivityHostelMateZoneBinding

class HostelMateZoneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostelMateZoneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostelMateZoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFeedback.setOnClickListener {
            sendFeedback()
        }
    }

    private fun sendFeedback() {
        val user = Firebase.auth.currentUser
        val database = Firebase.database
        val fname = binding.etFEmail.text.toString()
        val feedback = binding.etFeedback.text.toString()
        if (user != null) {
            val myRef = database.getReference(user.displayName.toString())
            myRef.setValue("$fname : $feedback")
            clearText()
        }
    }

    private fun clearText() {
        Toast.makeText(this, "Feedback Send Successfully...", Toast.LENGTH_SHORT).show()
        binding.etFEmail.text = null
        binding.etFeedback.text = null
    }
}