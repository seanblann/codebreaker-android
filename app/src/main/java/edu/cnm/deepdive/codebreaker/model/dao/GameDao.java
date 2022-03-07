package edu.cnm.deepdive.codebreaker.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.model.pojo.GameWithGuesses;
import edu.cnm.deepdive.codebreaker.model.view.GamePerformance;
import edu.cnm.deepdive.model.view.GameSummary;
import io.reactivex.rxjava3.core.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface GameDao {

  @Insert
  Single<Long> insert(Game game);

  @Insert
  Single<List<Long>> insert(Game... games);

  @Insert
  Single<List<Long>> insert(Collection<Game> games);

  @Update
  Single<Integer> update(Game game);

  @Update
  Single<Integer> update(Game... games);

  @Update
  Single<Integer> update(Collection<Game> games);

  @Delete
  Single<Integer> delete(Game game);

  @Delete
  Single<Integer> delete(Game... games);

  @Delete
  Single<Integer> delete(Collection<Game> games);

  @Transaction
  @Query("SELECT * FROM game WHERE game_id = :id")
  LiveData<GameWithGuesses> select(long id);

  @Query("SELECT * FROM game WHERE length = :length ORDER BY created DESC")
  LiveData<List<Game>> select(int length);

  @SuppressWarnings("AndroidUnresolvedRoomSqlReference")
  @Query("SELECT * FROM game_summary WHERE length = :length")
  LiveData<GameSummary> getSummary(int length);

  @SuppressWarnings("AndroidUnresolvedRoomSqlReference")
  @Query("SELECT * FROM game_performance WHERE length = :length ORDER BY duration ASC")
  LiveData<List<GamePerformance>> getRankingsByDuration(int length);

  @SuppressWarnings("AndroidUnresolvedRoomSqlReference")
  @Query("SELECT * FROM game_performance WHERE length = :length ORDER BY guess_count ASC, duration ASC")
  LiveData<List<GamePerformance>> getRankingsByGuessCount(int length);

}
