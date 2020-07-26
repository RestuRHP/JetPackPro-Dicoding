package net.learn.jetpack.base

import androidx.lifecycle.ViewModel
import net.learn.jetpack.data.MovieEntity
import net.learn.jetpack.utils.Dummy


class BaseViewModel : ViewModel() {

    private var entity: MovieEntity? = null
    private var id: String? = null

    fun getDetails(): MovieEntity? {
        val entityList: MutableList<MovieEntity> = ArrayList()
        entityList.addAll(Dummy.generateDummnyMovies())
        entityList.addAll(Dummy.generateDummyTvShows())
        for (i in entityList.indices) {
            val mEntity = entityList[i]
            if (mEntity.id == id) {
                entity = mEntity
            }
        }
        return entity
    }
    fun setId(id: String?) {
        this.id = id
    }

    fun getMovies(): List<MovieEntity> = Dummy.generateDummnyMovies()
    fun getTvShows(): List<MovieEntity> = Dummy.generateDummyTvShows()
}