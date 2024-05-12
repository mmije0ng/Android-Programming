package com.example.practice11

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val address: String
)

@Dao
interface MyDAO {
    @Query("SELECT * FROM Item")
    fun getAll(): LiveData<List<Item>>

    @Query("SELECT * FROM Item")
    fun getAll_f(): Flow<List<Item>>

    @Insert
    suspend fun insert(item: Item)
}

@Database(entities = [Item::class], version=1)
abstract class MyDatabase: RoomDatabase(){
    abstract fun getMyDAO(): MyDAO

    companion object{
        private var INSTANCE: MyDatabase? = null
        fun getDatabase(context: Context): MyDatabase{
            if(INSTANCE==null){
                INSTANCE = Room.databaseBuilder(
                  context, MyDatabase::class.java, "MyDatabase"
                ).build()
            }

            return INSTANCE as MyDatabase
        }
    }
}