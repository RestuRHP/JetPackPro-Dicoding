package net.learn.jetpack.utils

import android.content.Context
import net.learn.jetpack.data.source.remote.response.Movie
import org.json.JSONObject

class JsonHelper(private val context: Context) {

    private fun parsingData(fileName: String): String? {
        return try {
            val `is` = context.assets.open(fileName)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun loadItem(type: String): List<Movie> {
        val list = ArrayList<Movie>()
        try {
            val responseObject = JSONObject(parsingData("$type.json").toString())
            val listArray = responseObject.getJSONArray("Movie")
            for (i in 0 until listArray.length()) {
                val item = listArray.getJSONObject(i)

                val id = item.getInt("id")
                val title: String
                val release: String
                if (type == "movie") {
                    title = item.getString("title")
                    release = item.getString("release_date")
                } else {
                    title = item.getString("original_name")
                    release = item.getString("first_air_date")
                }
                val language = item.getString("original_language")
                val vote = item.getDouble("vote_average")
                val overview = item.getString("overview")
                val poster = item.getString("poster_path")
                val backdrop = item.getString("backdrop_path")

                val response = Movie(backdrop, id, language, title, overview, poster, release, vote)
                list.add(response)
            }
        } catch (e: Exception) {

        }
        return list
    }

}