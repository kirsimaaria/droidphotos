package com.maaria.kirsi.photobrowser.util

import android.util.Log

import com.maaria.kirsi.photobrowser.photo.Photo

import org.json.JSONArray
import org.json.JSONException
import java.util.ArrayList

/**
 * Created by Kirsikka on 16/08/2017.
 */

object JSONUtil {

    private val TAG = "JSONUtilJava"
    private val ID = "id"
    private val ALBUM_ID = "albumId"
    private val TITLE = "title"
    private val THUMBNAIL_URL = "thumbnailUrl"
    private val URL = "url"

    @Throws(JSONException::class)
    fun retrievePhotosFromResponse(response: String?): ArrayList<Photo> {
        if (response == null) {
            return ArrayList()
        }
        val jsonArray = JSONArray(response)
        val photos = ArrayList<Photo>()

        for (i in 0..jsonArray.length() - 1) {
            val jsonObject = jsonArray.getJSONObject(i)

            val photo = Photo(0, 0, "", "", "")

            if (jsonObject.has(ID)) {
                photo.id = Integer.parseInt(jsonObject.getString(ID))
            }
            if (jsonObject.has(ALBUM_ID)) {
                photo.id = Integer.parseInt(jsonObject.getString(ALBUM_ID))
            }
            if (jsonObject.has(TITLE)) {
                photo.title = jsonObject.getString(TITLE)
            }
            if (jsonObject.has(URL)) {
                photo.url = jsonObject.getString(URL)
            }
            if (jsonObject.has(THUMBNAIL_URL)) {
                photo.thumbnailUrl = jsonObject.getString(THUMBNAIL_URL)
            }
            photos.add(photo)
        }

        Log.v(TAG, "Parsed response size: " + photos.size)
        return photos
    }
}
