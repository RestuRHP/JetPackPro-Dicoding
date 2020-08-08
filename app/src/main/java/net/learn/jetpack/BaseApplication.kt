package net.learn.jetpack

import android.app.Application
import net.learn.jetpack.database.AppDatabase
import net.learn.jetpack.datastore.MovieStore.MovieRemoteStore
import net.learn.jetpack.datastore.MovieStore.MovieRoomStore
import net.learn.jetpack.datastore.TvStore.TvRemoteStore
import net.learn.jetpack.datastore.TvStore.TvRoomStore
import net.learn.jetpack.repository.MovieRepository
import net.learn.jetpack.repository.TvRepository
import net.learn.jetpack.service.Retrofit

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val api = Retrofit.API
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
    }

}