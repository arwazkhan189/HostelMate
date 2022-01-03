package tech.hostlematedevelopers.hostelmate.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tech.hostlematedevelopers.hostelmate.databinding.ActivityTiffinDetailsBinding

class TiffinDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTiffinDetailsBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTiffinDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTiffinCenterName.text = intent.getStringExtra("tiffin_name")
        binding.tvNumber.text = intent.getStringExtra("tiffin_mobile")
        binding.tvTiffinAddress.text = intent.getStringExtra("tiffin_address")
        binding.tvTiffinFee.text = "Rs. ${intent.getStringExtra("tiffin_fee")}"
        binding.tvTiffinRating.text = "${intent.getStringExtra("tiffin_rating")}/5"

    }
}