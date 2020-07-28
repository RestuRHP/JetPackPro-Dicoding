package net.learn.jetpack

import android.app.Application
import net.learn.jetpack.data.repository.MovieRepository
import net.learn.jetpack.data.store.MovieLocalStore
import net.learn.jetpack.data.store.MovieRemoteStore
import net.learn.jetpack.utils.service.Retrofit

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val api = Retrofit.API
        MovieRepository.instance.apply {
            init(
                MovieLocalStore(),
                MovieRemoteStore(api)
            )
        }
    }
}