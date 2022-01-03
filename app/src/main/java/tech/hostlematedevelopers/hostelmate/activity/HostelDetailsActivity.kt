package tech.hostlematedevelopers.hostelmate.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tech.hostlematedevelopers.hostelmate.databinding.ActivityHostelDetailsBinding


class HostelDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHostelDetailsBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostelDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvHostelName.text = intent.getStringExtra("hostel_name")
        //binding.tvGirlsOrBoys.text = intent.getStringExtra("hostel_type")
        binding.tvNumber.text = intent.getStringExtra("hostel_mobile")
        binding.tvHostelAddress.text = intent.getStringExtra("hostel_address")
        binding.tvHostelFee.text = "Rs. ${intent.getStringExtra("hostel_fee")}/Month"
        binding.tvHostelInTime.text = "⏰ ${intent.getStringExtra("hostel_inTime")} PM"
        //binding.tvHostelRating.text = "${intent.getStringExtra("hostel_rating")}/5"*/

    }
}