package edu.cnm.deepdive.codebreaker.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.codebreaker.model.entity.Guess;
import io.reactivex.rxjava3.core.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface GuessDao {

  @Insert
  Single<Long> insert(Guess guess);

  @Insert
  Single<List<Long>> insert(Guess... guesses);

  @Insert
  Single<List<Long>> insert(Collection<Guess> guesses);

  @Update
  Single<Integer> update(Guess guess);

  @Update
  Single<Integer> update(Guess... guess);

  @Update
  Single<Integer> update(Collection<Guess> guesses);

  @Delete
  Single<Integer> delete(Guess guess);

  @Delete
  Single<Integer> delete(Guess... guesses);

  @Delete
  Single<Integer> delete(Collection<Guess> guesses);

  @Query("SELECT * FROM guess WHERE guess_id = :id")
  LiveData<Guess> select(long id);

  @Query("SELECT * FROM guess WHERE game_id = :id ORDER BY created ASC")
  LiveData<List<Guess>> selectForGame(long id);











}
