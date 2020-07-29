package net.learn.jetpack

import android.app.Application
import net.learn.jetpack.data.repository.MovieRepository
import net.learn.jetpack.data.store.MovieRemoteStore
import net.learn.jetpack.data.store.MovieRoomStore
import net.learn.jetpack.database.AppDatabase
import net.learn.jetpack.utils.service.Retrofit

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val api = Retrofit.API
        val appDatabase = AppDatabase.getInstance(this)
        MovieRepository.instance.apply {
            init(
                MovieRoomStore(appDatabase.movieDao()),
                MovieRemoteStore(api)
            )
        }
    }
}