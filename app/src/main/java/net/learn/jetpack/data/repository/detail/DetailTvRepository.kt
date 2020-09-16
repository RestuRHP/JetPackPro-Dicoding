package net.learn.jetpack.data.repository.detail

import net.learn.jetpack.data.model.tv.TvShow


interface DetailTvRepositoryContract {
    suspend fun getSimilarTv(tvId: Int): MutableList<TvShow>?
    suspend fun setTvToFavorite(tvId: Int)
    suspend fun removeTvFromFavorite(tvId: Int)
    suspend fun isFavorite(tvId: Int): MutableList<TvShow>?
}

class DetailTvRepository private constructor() : DetailTvRepositoryContract,
    BaseDetailRepository<TvShow>() {
    override suspend fun getSimilarTv(tvId: Int): MutableList<TvShow>? =
        remoteStoreBase?.getSimilar(tvId)

    override suspend fun setTvToFavorite(tvId: Int) {
        roomStoreBase?.setToFavorite(tvId)
    }

    override suspend fun removeTvFromFavorite(tvId: Int) {
        roomStoreBase?.removeFromFavorite(tvId)
    }

    override suspend fun isFavorite(tvId: Int): MutableList<TvShow>? =
        roomStoreBase?.isFavorite(tvId)

    companion object {
        val instance by lazy { DetailTvRepository() }
    }
}