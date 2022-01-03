package tech.hostlematedevelopers.hostelmate.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.sort_hostels.view.*
import tech.hostlematedevelopers.hostelmate.adapter.HostelRecyclerAdapter
import tech.hostlematedevelopers.hostelmate.model.Hostel
import kotlin.collections.ArrayList
import kotlin.Comparator
import tech.hostlematedevelopers.hostelmate.R
import tech.hostlematedevelopers.hostelmate.databinding.ActivityHostelBinding
import tech.hostlematedevelopers.hostelmate.util.ConnectionManager
import java.util.*

class HostelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHostelBinding
    private lateinit var recyclerAdapter: HostelRecyclerAdapter
    private lateinit var radioButtonView: View
    private lateinit var hostelList: ArrayList<Hostel>
    private lateinit var db: FirebaseFirestore

    private var ratingComparator = Comparator<Hostel> { hostel1, hostel2 ->
        if (hostel1.Rating!!.compareTo(hostel2.Rating!!, true) == 0) {
            hostel1.Name!!.compareTo(hostel2.Name!!, true)
        } else {
            hostel1.Rating!!.compareTo(hostel2.Rating!!, true)
        }
    }
    private var costComparator = Comparator<Hostel> { hostel1, hostel2 ->
        hostel1.Fee!!.compareTo(hostel2.Fee!!, true)
    }
    /*private var genderComparator = Comparator<Hostel> { hostel1, hostel2 ->
        hostel1.Type!!.compareTo(hostel2.Type!!, true)
    }*/

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hostelList = arrayListOf()


        binding.recyclerHostels.layoutManager = LinearLayoutManager(this)
        binding.recyclerHostels.setHasFixedSize(true)
        recyclerAdapter = HostelRecyclerAdapter(this, hostelList)
        binding.recyclerHostels.adapter = recyclerAdapter

        //-------progress bar functionality-------
        binding.progressLayout.visibility = View.VISIBLE

        //----- Add Hostel Button Function-------
        binding.btnAddHostels.setOnClickListener {
            val intent = Intent(this, AddHostel::class.java)
            startActivity(intent)
        }

        //------ Search function------------
        binding.etSearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(searchText: Editable?) {
                searchHostelByName(searchText.toString())
            }

            override fun beforeTextChanged(
                searchText: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                searchText: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }
        })

        //------- Sort Function------------
        binding.btnSort.setOnClickListener {
            sortList()
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortList() {
        radioButtonView = View.inflate(this, R.layout.sort_hostels, null)
        AlertDialog.Builder(this)
            .setTitle("Sort By?")
            .setView(radioButtonView)
            .setPositiveButton("OK") { _, _ ->
                if (radioButtonView.checkbox_high_to_low.isChecked) {
                    Collections.sort(hostelList, costComparator)
                    hostelList.reverse()
                    recyclerAdapter.notifyDataSetChanged()
                }
                if (radioButtonView.checkbox_low_to_high.isChecked) {
                    Collections.sort(hostelList, costComparator)
                    recyclerAdapter.notifyDataSetChanged()
                }
                if (radioButtonView.checkbox_rating.isChecked) {
                    Collections.sort(hostelList, ratingComparator)
                    hostelList.reverse()
                    recyclerAdapter.notifyDataSetChanged()
                }
                val filteredList = arrayListOf<Hostel>()
                var radioCheck: Boolean
                if (radioButtonView.radio_boysHostel.isChecked) {
                    for (item in hostelList) {
                        if (item.Type?.lowercase()?.contains("boys") == true
                        ) {
                            filteredList.add(item)
                        }
                    }
                    radioCheck = true
                    recyclerAdapter.filterList(filteredList)
                    recyclerAdapter.notifyDataSetChanged()

                }
                if (radioButtonView.radio_girlsHostel.isChecked) {
                    for (item in hostelList) {
                        if (item.Type?.lowercase()?.contains("girls") == true
                        ) {
                            filteredList.add(item)
                        }
                    }
                    recyclerAdapter.filterList(filteredList)
                    recyclerAdapter.notifyDataSetChanged()
                    radioButtonView.radio_girlsHostel.isChecked
                }
            }
            .setNegativeButton("CANCEL")
            { _, _ ->

            }
            .create()
            .show()
    }

    private fun showData() {
        db = FirebaseFirestore.getInstance()
        db.collection("Hostels")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Toast.makeText(
                            applicationContext,
                            "Some unexpected error occurred!!! ",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }
                    for (HostelsData: DocumentChange in value?.documentChanges!!) {
                        if (HostelsData.type == DocumentChange.Type.ADDED) {
                            hostelList.add(HostelsData.document.toObject(Hostel::class.java))
                        }
                    }
                    recyclerAdapter.notifyDataSetChanged()
                    binding.progressLayout.visibility = View.GONE
                }
            })
    }

    private fun searchHostelByName(searchText: String) {
        val filteredList = arrayListOf<Hostel>()
        for (item in hostelList) {
            if (item.Name?.lowercase()?.contains(searchText.lowercase()) == true
            ) {
                filteredList.add(item)
            }
        }
        if (filteredList.size == 0) {
            binding.rLNotFound.visibility = View.VISIBLE
        } else {
            binding.rLNotFound.visibility = View.INVISIBLE
        }
        recyclerAdapter =
            HostelRecyclerAdapter(this, hostelList)
        binding.recyclerHostels.adapter = recyclerAdapter
        binding.recyclerHostels.layoutManager = LinearLayoutManager(this)
        recyclerAdapter.filterList(filteredList)
    }

    override fun onResume() {
        if (ConnectionManager().checkConnectivity(this)) {
            if (hostelList.isEmpty())
                showData()
        } else {
            checkInternet()
        }
        super.onResume()
    }

    private fun checkInternet() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Error")
        dialog.setMessage("Internet Connection is not Found")
        dialog.setPositiveButton("Open Settings") { _, _ ->
            val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            startActivity(settingsIntent)
        }
        dialog.setNegativeButton("Exit") { _, _ ->
            ActivityCompat.finishAffinity(this)
        }
        dialog.create()
        dialog.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkbox_rating -> {
                    if (checked) {
                        Collections.sort(hostelList, ratingComparator)
                        hostelList.reverse()
                        recyclerAdapter.notifyDataSetChanged()
                    } else {
                        recyclerAdapter =
                            HostelRecyclerAdapter(this, hostelList)
                        binding.recyclerHostels.adapter = recyclerAdapter
                        binding.recyclerHostels.layoutManager =
                            LinearLayoutManager(this)
                        recyclerAdapter.notifyDataSetChanged()
                    }
                }
                R.id.checkbox_low_to_high -> {
                    if (checked) {
                        Collections.sort(hostelList, costComparator)
                        recyclerAdapter.notifyDataSetChanged()
                    } else {
                        recyclerAdapter =
                            HostelRecyclerAdapter(this, hostelList)
                        binding.recyclerHostels.adapter = recyclerAdapter
                        binding.recyclerHostels.layoutManager =
                            LinearLayoutManager(this)
                        recyclerAdapter.notifyDataSetChanged()
                    }
                }
                R.id.checkbox_high_to_low -> {
                    if (checked) {
                        Collections.sort(hostelList, costComparator)
                        hostelList.reverse()
                        recyclerAdapter.notifyDataSetChanged()
                    } else {
                        recyclerAdapter =
                            HostelRecyclerAdapter(this, hostelList)
                        binding.recyclerHostels.adapter = recyclerAdapter
                        binding.recyclerHostels.layoutManager =
                            LinearLayoutManager(this)
                        recyclerAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

}

