package asalcedo.com.zemogaapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import asalcedo.com.zemogaapp.data.database.entities.PostEntity
import asalcedo.com.zemogaapp.data.database.entities.UserEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM post_table ORDER BY isFavorite DESC")
    suspend fun getAllPosts(): List<PostEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insertAllPosts(posts: List<PostEntity>)

    @Query("DELETE FROM post_table")
    suspend fun deleteAllPosts()

    @Insert(onConflict = REPLACE)
    suspend fun insertAllUsers(users: List<UserEntity>)

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Update
    suspend fun updatePost(post: PostEntity)

    @Query("SELECT * FROM post_table WHERE isFavorite = 1")
    suspend fun getFavoritesPost(): List<PostEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insertPost(post: PostEntity)

    @Query("SELECT MAX(id_post) FROM post_table")
    suspend fun getLasPostId(): Int

    @Query("DELETE FROM post_table WHERE id_post = :postId")
    suspend fun deletePostById(postId: Int)
}