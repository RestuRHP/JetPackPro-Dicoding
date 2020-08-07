package net.learn.jetpack.model.tv

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(indices = [Index(value = ["id"], unique = true)])
@Parcelize
data class TvShow(
    @PrimaryKey(autoGenerate = true)
    var VID: Int?,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("original_language")
    var originalLanguage: String?,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("first_air_date")
    var releaseDate: String?,
    @SerializedName("original_name")
    var title: String?,
    @SerializedName("vote_average")
    var voteAverage: Double?,
    var favorite: Boolean? = false
) : Parcelable