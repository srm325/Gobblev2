package com.srm325.gobble.ui.features.map

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory.newLatLngBounds
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.srm325.gobble.R
import com.srm325.gobble.data.Repository
import com.srm325.gobble.data.model.Post
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList


private lateinit var mMap: GoogleMap
private val repository = Repository()
private lateinit var viewModel: MapViewModel
val addressList: ArrayList<String> = ArrayList<String>(listOf(""))


class ChatListFragment : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance() = ChatListFragment()
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val fm: FragmentManager = childFragmentManager;
        var mapFragment = fm.findFragmentById(R.id.map) as SupportMapFragment?
        val user = Firebase.auth.currentUser
        val postList:MutableList<Post> = mutableListOf()
        val db = Firebase.firestore
        db.collection("posts")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val post = document.toObject(Post::class.java)
                        if (post.address != null) {
                            addressList.add(post.address)
                        }
                    }
                    Timber.e(addressList.size.toString())
                    Timber.e(addressList.toString())



                }
        addressList.removeAt(0)
        Timber.e(addressList.toString())

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, mapFragment).commit()
            Timber.e("I tried")
        }
        if (mapFragment == null) {
            Timber.e("Get fucked")
        }else{
            Timber.e("Map not null")
            mapFragment.getMapAsync(this)
        }

        return inflater.inflate(R.layout.chat_list_fragment, container, false)

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)

    }
    fun getCurrentUser() = repository.getCurrentUser()

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val settings: UiSettings = mMap.uiSettings
        settings.isZoomControlsEnabled = true
        val builder = LatLngBounds.Builder()
        for (i in addressList) {
            Timber.e(i)
            var address123 = getLocationFromAddress(activity, i) as LatLng
            Timber.e(address123.toString())
            mMap.addMarker(
                    MarkerOptions()
                            .position(address123)
                            .title("Dinner location")
            )
            builder.include(address123);
        }
        val bounds = builder.build()

        mMap.animateCamera(newLatLngBounds(bounds, 15))
    }

    private fun getLocationFromAddress(context: Context?, strAddress: String?): LatLng? {
        val coder = Geocoder(context)
        val address: List<Address>?
        var p1: LatLng = LatLng(0.0, 0.0)
        try {
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                Timber.e("Address not found")
            } else {
                val location: Address = address[0]
                p1 = LatLng(location.latitude, location.longitude)
                mMap.addMarker(MarkerOptions().position(p1).title("Dinner location"))
                Timber.e(p1.toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return p1
    }


}

/*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.GoogleMap
import com.srm325.gobble.R


class ChatListFragment : Fragment(){

    companion object {
        fun newInstance() = ChatListFragment()
    }

    private lateinit var viewModel: ChatListViewModel
   /* var locationRequest: LocationRequest? = null
    var locationCallback: LocationCallback? = null
    var locationProvider: FusedLocationProviderClient? = null
    var MAP: GoogleMap? = null

    */
    var lat = 43.1226686
    var lon = -77.5901883

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chat_list_fragment, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onMapReady(googleMap: GoogleMap) {
        try {
            val coder = Geocoder(activity)
            MAP = googleMap
            val area = LatLng(lat, lon);
            MAP!!.addMarker(MarkerOptions().position(area).title("Current location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
            MAP!!.moveCamera(CameraUpdateFactory.newLatLng(area))

        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("MAPEXCEPTION", e.message!!)
        }

    }



}
        */


