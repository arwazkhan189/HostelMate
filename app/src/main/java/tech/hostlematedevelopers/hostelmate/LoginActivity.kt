package tech.hostlematedevelopers.hostelmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tech.hostlematedevelopers.hostelmate.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)




    }
}