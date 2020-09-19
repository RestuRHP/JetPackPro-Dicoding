package net.learn.jetpack.data.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.learn.jetpack.data.model.tv.TvShow
import net.learn.jetpack.utils.Room.DELETE_DISCOVERY_TV
import net.learn.jetpack.utils.Room.FILTER_TV_IS_FAVORITE_WITH_ID
import net.learn.jetpack.utils.Room.GET_ALL_DISCOVERY_TV
import net.learn.jetpack.utils.Room.GET_ALL_TV_IS_FAVORITE
import net.learn.jetpack.utils.Room.REMOVE_TV_FROM_FAVORITE
import net.learn.jetpack.utils.Room.UPDATE_TV_BE_FAVORITE

@Dao
interface TvDao {
    @Query(GET_ALL_DISCOVERY_TV)
    suspend fun getAllDiscovery(): MutableList<TvShow>

    @Query(DELETE_DISCOVERY_TV)
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movie: MutableList<TvShow>)

    @Query(UPDATE_TV_BE_FAVORITE)
    suspend fun setTvBeFavorite(fvBool: Boolean, tvId: Int)

    @Query(REMOVE_TV_FROM_FAVORITE)
    suspend fun removeTvFromFavorite(fvBool: Boolean, tvId: Int)

    @Query(FILTER_TV_IS_FAVORITE_WITH_ID)
    suspend fun tvIsFavorite(fvBool: Boolean, tvId: Int): MutableList<TvShow>

    @Query(GET_ALL_TV_IS_FAVORITE)
    fun getAllFavoriteTv(fvBool: Boolean): DataSource.Factory<Int, TvShow>
}