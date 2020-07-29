package net.learn.jetpack.ui.tv.store

import net.learn.jetpack.model.tv.TvShow

interface TvDataStore {
    suspend fun getSets(): MutableList<TvShow>?
    suspend fun addAll(sets: MutableList<TvShow>?)
}