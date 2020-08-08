package net.learn.submission4mvvm.model.movies

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(indices = arrayOf(Index(value = ["id"], unique = true)))
@Parcelize
data class Movie(
    @PrimaryKey(autoGenerate = true)
    var VID: Int?,
    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdropPath")
    var backdropPath: String?,
    @SerializedName("id")
    @ColumnInfo(name = "id")
    var id: Int?,
    @SerializedName("original_language")
    @ColumnInfo(name = "originalLanguage")
    var originalLanguage: String?,
    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    var overview: String?,
    @SerializedName("poster_path")
    @ColumnInfo(name = "posterPath")
    var posterPath: String?,
    @SerializedName("release_date")
    @ColumnInfo(name = "releaseDate")
    var releaseDate: String?,
    @SerializedName("original_title")
    @ColumnInfo(name = "title")
    var title: String?,
    @SerializedName("vote_average")
    @ColumnInfo(name = "voteAverage")
    var voteAverage: Double?,
    @ColumnInfo(name = "favorite")
    val favorite: Boolean? = false
) : Parcelable