package edu.cnm.deepdive.codebreaker.service;

import android.content.Context;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.codebreaker.model.dao.GameDao;
import edu.cnm.deepdive.codebreaker.model.dao.GuessDao;
import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.model.entity.Guess;
import edu.cnm.deepdive.codebreaker.model.pojo.GameWithGuesses;
import edu.cnm.deepdive.model.view.GameSummary;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.Iterator;

public class GameRepository {

  private final Context context;
  private final CodebreakerServiceProxy proxy;
  private final GameDao gameDao;
  private final GuessDao guessDao;

  public GameRepository(Context context) {
    this.context = context;
    proxy = CodebreakerServiceProxy.getInstance();
    CodebreakerDatabase database = CodebreakerDatabase.getInstance();
    gameDao = database.getGameDao();
    guessDao = database.getGuessDao();
  }

  public Single<GameWithGuesses> startGame(String pool, int length) {
    Game game = new Game();
    game.setPool(pool);
    game.setLength(length);
    return proxy
        .startGame(game)
        .subscribeOn(Schedulers.io());
  }

  public Single<GameWithGuesses> submitGuess(GameWithGuesses game, String text) {
    Guess guess = new Guess();
    guess.setText(text);
    return proxy
        .submitGuess(game.getServiceKey(), guess)
        .map((g) -> {
          game.getGuesses().add(g);
          return g;
        })
        .flatMap((g) -> g.isSolution() ? persist(game) : Single.just(game))
        .subscribeOn(Schedulers.io());
  }

  public LiveData<GameSummary> getSummary(int length) {
    return gameDao.getSummary(length);
  }

  private Single<GameWithGuesses> persist(GameWithGuesses game) {

    return  gameDao
        .insert(game)
        .map((id) -> {
          game.setId(id);
          for(Guess guess : game.getGuesses()) {
            guess.setGameId(id);
          }
          return game.getGuesses();
        })
        .flatMap(guessDao::insert)
        .map((ids) -> {
          Iterator<Long> idIter = ids.iterator();
          Iterator<Guess> guessIter = game.getGuesses().iterator();
          while (idIter.hasNext() && guessIter.hasNext()) {
            guessIter.next().setId(idIter.next());
          }
          return game;
        });
  }

}
















