package net.learn.jetpack.data.repository

import net.learn.jetpack.data.model.tv.TvShow

private const val TV_STARTING_PAGE_INDEX = 1

class TvRepository private constructor() : BaseRepository<TvShow>() {
    private var nextRequestPage = TV_STARTING_PAGE_INDEX
    private var isRequestInProgress = false

    suspend fun loadDiscoveryTv(): MutableList<TvShow>? {
        val data = getDiscoveryTvFromDB()
        return try {
            if (data != null) {
                data
            } else {
                saveDiscoveryTvToDB(nextRequestPage)
                getDiscoveryTvFromDB()
            }
        } catch (ex: Exception) {
            throw Exception(ex)
        }
    }

    suspend fun paginationSets() {
        if (isRequestInProgress) return
        val successful = saveDiscoveryTvToDB(nextRequestPage)
        if (successful) {
            nextRequestPage++
        }
    }

    private suspend fun saveDiscoveryTvToDB(page: Int): Boolean {
        isRequestInProgress = true
        var successful = false
        try {
            val response = remoteStore?.getDiscovery(page = page)
            if (response != null) {
                localStore?.addAll(response)
                successful = true
            }
        } catch (ex: Exception) {

            throw Exception(ex)
        }
        isRequestInProgress = false
        return successful
    }

    suspend fun getDiscoveryTvFromDB(): MutableList<TvShow>? {
        return localStore?.getDiscovery(nextRequestPage)
    }

    companion object {
        val instance by lazy { TvRepository() }
    }

}