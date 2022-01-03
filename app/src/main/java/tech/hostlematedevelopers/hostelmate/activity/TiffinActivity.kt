package tech.hostlematedevelopers.hostelmate.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.sort_tiffin_centers.view.*
import tech.hostlematedevelopers.hostelmate.adapter.TiffinRecyclerAdapter
import tech.hostlematedevelopers.hostelmate.databinding.ActivityTiffinBinding
import tech.hostlematedevelopers.hostelmate.model.Tiffin
import tech.hostlematedevelopers.hostelmate.util.ConnectionManager
import java.util.*
import tech.hostlematedevelopers.hostelmate.R
import kotlin.Comparator
import kotlin.collections.ArrayList

class TiffinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTiffinBinding
    private lateinit var recyclerAdapter: TiffinRecyclerAdapter
    private lateinit var radioButtonTiffinView: View
    private lateinit var tiffinList: ArrayList<Tiffin>
    private lateinit var db: FirebaseFirestore

    private var ratingComparator = Comparator<Tiffin> { tiffin1, tiffin2 ->
        if (tiffin1.Rating!!.compareTo(tiffin2.Rating!!, true) == 0) {
            tiffin1.Name!!.compareTo(tiffin2.Name!!, true)
        } else {
            tiffin1.Rating!!.compareTo(tiffin2.Rating!!, true)
        }
    }
    private var costComparator = Comparator<Tiffin> { tiffin1, tiffin2 ->
        tiffin1.Fee!!.compareTo(tiffin2.Fee!!, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTiffinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tiffinList = arrayListOf()

        binding.recyclerTiffinCenters.layoutManager = LinearLayoutManager(this)
        binding.recyclerTiffinCenters.setHasFixedSize(true)
        recyclerAdapter = TiffinRecyclerAdapter(this, tiffinList)
        binding.recyclerTiffinCenters.adapter = recyclerAdapter

        //-------progress bar functionality-------
        binding.progressLayout.visibility = View.VISIBLE

        //----- Add Tiffin Centers Button Function-------
        binding.btnAddTiffinCenters.setOnClickListener {
            val intent = Intent(this, AddTiffin::class.java)
            startActivity(intent)
        }

        //------ Search function------------
        binding.etSearchTiffin.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(searchText: Editable?) {
                searchTiffinCenterByName(searchText.toString())
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
        binding.btnSortTiffin.setOnClickListener {
            sortListTiffin()
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortListTiffin() {
        radioButtonTiffinView = View.inflate(this, R.layout.sort_tiffin_centers, null)
        AlertDialog.Builder(this)
            .setTitle("Sort By?")
            .setView(radioButtonTiffinView)
            .setPositiveButton("OK") { _, _ ->
                if (radioButtonTiffinView.radio_high_to_low_tiffin.isChecked) {
                    Collections.sort(tiffinList, costComparator)
                    tiffinList.reverse()
                    recyclerAdapter.notifyDataSetChanged()
                }
                if (radioButtonTiffinView.radio_low_to_high_tiffin.isChecked) {
                    Collections.sort(tiffinList, costComparator)
                    recyclerAdapter.notifyDataSetChanged()
                }
                if (radioButtonTiffinView.radio_rating_tiffin.isChecked) {
                    Collections.sort(tiffinList, ratingComparator)
                    tiffinList.reverse()
                    recyclerAdapter.notifyDataSetChanged()
                }
            }
            .setNegativeButton("CANCEL") { _, _ ->

            }
            .create()
            .show()
    }

    private fun showData() {
        db = FirebaseFirestore.getInstance()
        db.collection("TiffinCenter")
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
                    for (TiffinCentersData: DocumentChange in value?.documentChanges!!) {
                        if (TiffinCentersData.type == DocumentChange.Type.ADDED) {
                            tiffinList.add(TiffinCentersData.document.toObject(Tiffin::class.java))
                        }
                    }
                    recyclerAdapter.notifyDataSetChanged()
                    binding.progressLayout.visibility = View.GONE
                }

            })
    }

    private fun searchTiffinCenterByName(searchText: String) {
        val filteredList = arrayListOf<Tiffin>()
        for (item in tiffinList) {
            if (item.Name?.lowercase()?.contains(searchText.lowercase()) == true
            ) {
                filteredList.add(item)
            }
        }
        if (filteredList.size == 0) {
            binding.rLNotFoundTiffin.visibility = View.VISIBLE
        } else {
            binding.rLNotFoundTiffin.visibility = View.INVISIBLE
        }
        recyclerAdapter =
            TiffinRecyclerAdapter(this, tiffinList)
        binding.recyclerTiffinCenters.adapter = recyclerAdapter
        binding.recyclerTiffinCenters.layoutManager = LinearLayoutManager(this)
        recyclerAdapter.filterList(filteredList)
    }

    override fun onResume() {
        if (ConnectionManager().checkConnectivity(this)) {
            if (tiffinList.isEmpty())
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

}