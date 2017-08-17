package com.maaria.kirsi.photobrowser.photo

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.maaria.kirsi.photobrowser.R
import com.maaria.kirsi.photobrowser.util.JSONUtil
import com.maaria.kirsi.photobrowser.volley.CustomVolleyRequestQueue
import org.json.JSONException

/**
 * Created by Kirsikka on 12/08/2017.
 */

class PhotoGridviewActivity : Activity() {

    var GET_PHOTOS_URL = "https://jsonplaceholder.typicode.com/photos"

    private var gridview: GridView? = null
    private var progressDialog: ProgressDialog? = null
    private val TAG = "PhotoGridviewActivity"

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_grid)

        gridview = findViewById<View>(R.id.imagegridview) as GridView

        gridview?.onItemClickListener = AdapterView.OnItemClickListener { parent, v, position, id ->
            Toast.makeText(this@PhotoGridviewActivity, "" + position,
                    Toast.LENGTH_SHORT).show()
        }

        if (isNetworkConnected()) {
            progressDialog = ProgressDialog(this)
            progressDialog?.setMessage("Please wait...")
            progressDialog?.setCancelable(false)
            progressDialog?.show()

            makeRequestForPhotos(GET_PHOTOS_URL)
        } else {
            AlertDialog.Builder(this).setTitle("No connection")
                    .setMessage("Please check connection and try again")
                    .setPositiveButton("OK") {dialog, which ->}
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }

    fun downloadComplete(photos: ArrayList<Photo>) {
        gridview?.adapter = PhotosAdapter(this, photos)
        Log.v(TAG, "Download complete")
        if (progressDialog != null) {
            progressDialog?.hide()
        }
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager // 1
        val networkInfo = connectivityManager.activeNetworkInfo // 2
        return networkInfo != null && networkInfo.isConnected // 3
    }

    private fun makeRequestForPhotos(url: String) {
        Log.v(TAG, "Making request with URL: " + url)

        val queue = CustomVolleyRequestQueue.getInstance(this).requestQueue
        val stringRequest = StringRequest(
                Request.Method.GET,
                url,
                Response.Listener<String> {
                response -> try {
                    downloadComplete(JSONUtil.retrievePhotosFromResponse(response))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
        }, Response.ErrorListener {})
        queue.add(stringRequest)
    }
}
