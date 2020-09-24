package net.learn.jetpack.ui

import net.learn.jetpack.data.model.movies.Movie
import net.learn.jetpack.data.model.tv.TvShow

sealed class BaseViewState {
    object ShowLoading : BaseViewState()
    object HideLoading : BaseViewState()
    object Error : BaseViewState()
    object LoadScreenError : BaseViewState()
    object SuccessAddFavorite : BaseViewState()
    object SuccessRemoveFavorite : BaseViewState()
    object FailedRemoveFavorite : BaseViewState()
    object FailedAddFavorite : BaseViewState()

    data class IsFavoriteTheater(val status: Boolean) : BaseViewState()
    data class LoadMovieSuccess(val data: MutableList<Movie>?) : BaseViewState()
    data class LoadSimilarMovieSuccess(val data: MutableList<Movie>?) : BaseViewState()
    data class LoadTvSuccess(val data: MutableList<TvShow>?) : BaseViewState()
    data class LoadSimilarTvSuccess(val data: MutableList<TvShow>?) : BaseViewState()
}