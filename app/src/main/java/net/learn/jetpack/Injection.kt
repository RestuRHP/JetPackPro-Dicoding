package net.learn.jetpack

import net.learn.jetpack.data.network.Api
import net.learn.jetpack.data.network.Client

object Injection {

//    fun provideDetailMovie(context: Context): DetailRepository {
//        val database = AppDatabase.getInstance(context)
//        return DetailRepository(database.movieDao())
//    }

    private fun provideNetworkService(): Api = Client.create()

//    fun fixProvideDetail(context: Context):DetailRepository{
//        val db = AppDatabase.getInstance(context)
//        return DetailRepository.getInstance(db)
//    }
}