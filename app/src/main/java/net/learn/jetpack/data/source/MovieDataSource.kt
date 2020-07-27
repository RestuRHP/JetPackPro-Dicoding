package net.learn.jetpack.data.source

import net.learn.jetpack.data.MovieEntity

interface MovieDataSource {
    fun getAllItem(type: String): List<MovieEntity>
}