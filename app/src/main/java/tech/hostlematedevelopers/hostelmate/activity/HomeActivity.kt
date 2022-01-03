package tech.hostlematedevelopers.hostelmate.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import tech.hostlematedevelopers.hostelmate.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goToHostels.setOnClickListener {
            val intent = Intent(this, HostelActivity::class.java)
            startActivity(intent)
        }

        binding.goToTiffinCenters.setOnClickListener {
            val intent = Intent(this, TiffinActivity::class.java)
            startActivity(intent)
        }

        binding.goToSafetyService.setOnClickListener {
            val intent = Intent(this, SafetyActivity::class.java)
            startActivity(intent)
        }

        binding.goToHostelMateZone.setOnClickListener {
            val intent = Intent(this, HostelMateZoneActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignOut.setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(this, "Sign Out Successfully...", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}