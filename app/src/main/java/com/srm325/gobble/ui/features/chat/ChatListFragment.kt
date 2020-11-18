package com.srm325.gobble.ui.features.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.srm325.gobble.R
import timber.log.Timber


private lateinit var mMap: GoogleMap

class ChatListFragment : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance() = ChatListFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fm: FragmentManager = childFragmentManager;
        var mapFragment = fm.findFragmentById(R.id.map) as SupportMapFragment?


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
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val settings: UiSettings = mMap.uiSettings
        settings.isZoomControlsEnabled = true
        val current = LatLng(43.1226686, -77.5901883)
        mMap.addMarker(MarkerOptions()
                .position(current)
                .title("Dinner location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current))
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


