package id.my.okisulton.mynews_dicoding.utils

import android.content.Context
import java.io.IOException
import java.security.AccessControlContext

/**
 *Created by osalimi on 25-06-2022.
 **/
fun getDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    }catch (ioException: IOException){
        ioException.printStackTrace()
        return null
    }
    return jsonString
}