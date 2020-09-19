package net.learn.jetpack

import android.app.Application
import net.learn.jetpack.data.database.AppDatabase
import net.learn.jetpack.data.datastore.detail.DetailMovieRemoteStore
import net.learn.jetpack.data.datastore.detail.DetailMovieRoomStore
import net.learn.jetpack.data.datastore.detail.DetailTvRemoteStore
import net.learn.jetpack.data.datastore.detail.DetailTvRoomStore
import net.learn.jetpack.data.datastore.favorite.FavoriteMovieRoomStore
import net.learn.jetpack.data.datastore.favorite.FavoriteTvRoomStore
import net.learn.jetpack.data.datastore.movies.MovieRemoteStore
import net.learn.jetpack.data.datastore.movies.MovieRoomStore
import net.learn.jetpack.data.datastore.tv.TvRemoteStore
import net.learn.jetpack.data.datastore.tv.TvRoomStore
import net.learn.jetpack.data.network.Client
import net.learn.jetpack.data.repository.MovieRepository
import net.learn.jetpack.data.repository.TvRepository
import net.learn.jetpack.data.repository.detail.DetailMovieRepository
import net.learn.jetpack.data.repository.detail.DetailTvRepository
import net.learn.jetpack.data.repository.favorite.FavoriteMovieRepositoryImpl
import net.learn.jetpack.data.repository.favorite.FavoriteTvRepositoryImpl

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val api = Client.create()
        val appDatabase = AppDatabase.getInstance(this)

        MovieRepository.instance.apply {
            init(
                MovieRoomStore(appDatabase.movieDao()), MovieRemoteStore(api)
            )
        }

        TvRepository.instance.apply {
            init(
                TvRoomStore(appDatabase.tvDao()), TvRemoteStore(api)
            )
        }

        DetailMovieRepository.instance.apply {
            init(
                DetailMovieRemoteStore(api), DetailMovieRoomStore(appDatabase.movieDao())
            )
        }

        DetailTvRepository.instance.apply {
            init(
                DetailTvRemoteStore(api), DetailTvRoomStore(appDatabase.tvDao())
            )
        }

        FavoriteMovieRepositoryImpl.instance.apply {
            init(
                FavoriteMovieRoomStore(appDatabase)
            )
        }

        FavoriteTvRepositoryImpl.instance.apply {
            init(
                FavoriteTvRoomStore(appDatabase)
            )
        }
    }
}