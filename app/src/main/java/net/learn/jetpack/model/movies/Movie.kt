package net.learn.submission4mvvm.model.movies

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Movie(
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("id")
    @PrimaryKey var id: Int?,
    @SerializedName("original_language")
    var originalLanguage: String?,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("release_date", alternate = ["first_air_date"])
    var releaseDate: String?,
    @SerializedName("title", alternate = ["original_name"])
    var title: String?,
    @SerializedName("vote_average")
    var voteAverage: Double?,
    var type: String?
) : Parcelable