package com.maaria.kirsi.photobrowser.photo

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.android.volley.toolbox.NetworkImageView
import com.maaria.kirsi.photobrowser.volley.CustomVolleyRequestQueue

import java.util.ArrayList

/**
 * Created by Kirsikka on 12/08/2017.
 */

class PhotosAdapter(private val mContext: Context, private val thumbnails: ArrayList<Photo>) : BaseAdapter() {

    override fun getCount(): Int {
        return thumbnails.size
    }

    override fun getItem(position: Int): Any {
        return thumbnails[position]
    }

    override fun getItemId(position: Int): Long {
        return thumbnails[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val mImageLoader = CustomVolleyRequestQueue.getInstance(parent.context).imageLoader
        val networkImageView: NetworkImageView
        val layoutParamsG = ViewGroup.LayoutParams(150, 150)


        if (convertView == null) {
            networkImageView = NetworkImageView(mContext)
            networkImageView.layoutParams = layoutParamsG
            networkImageView.scaleType = ImageView.ScaleType.CENTER_CROP
            networkImageView.setPadding(4, 4, 4, 4)

        } else {
            networkImageView = convertView as NetworkImageView
        }

        val thumbnailUrl = thumbnails[position].thumbnailUrl
        networkImageView.setImageUrl(thumbnailUrl, mImageLoader)
        return networkImageView
    }
}
