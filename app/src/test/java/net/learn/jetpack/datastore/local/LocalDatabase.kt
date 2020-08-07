package net.learn.jetpack.datastore.local

import net.learn.jetpack.database.AppDatabase
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
abstract class LocalDatabase {
    lateinit var db: AppDatabase

    //
//    This test under progress
    @Before
    fun initDB() {
//        db=Room.inMemoryDatabaseBuilder()
    }

}