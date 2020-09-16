package net.learn.jetpack.utils

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.mockito.Mockito.mock

object PagingDataUtil {

    fun <T : Any> mockPagedList(list: List<T>): Flow<PagingData<T>> {
        val pagingData = mock(PagingData::class.java) as Flow<PagingData<T>>


        return pagingData
    }

}