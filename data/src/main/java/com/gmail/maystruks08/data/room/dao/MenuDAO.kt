package maystruks08.gmail.com.data.room.dao

import androidx.room.*
import com.gmail.maystruks08.data.room.entity.MenuTable

@Dao
interface MenuDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: MenuTable)


    @Update
    fun update(menu: MenuTable)

    @Update
    fun update(menus: List<MenuTable>)

    @Delete
    fun delete(menu: MenuTable)

    @Query("DELETE FROM users")
    fun dropTable()
}


