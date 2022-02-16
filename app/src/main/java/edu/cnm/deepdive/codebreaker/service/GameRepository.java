package edu.cnm.deepdive.codebreaker.service;

import android.content.Context;
import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.model.entity.Guess;
import edu.cnm.deepdive.codebreaker.model.pojo.GameWithGuesses;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GameRepository {

  private final Context context;
  private final CodebreakerServiceProxy proxy;

  public GameRepository(Context context) {
    this.context = context;
    proxy = CodebreakerServiceProxy.getInstance();
  }

  public Single<GameWithGuesses> startGame(String pool, int length) {
    Game game = new Game();
    game.setPool(pool);
    game.setLength(length);
    return proxy
        .startGame(game)
        .subscribeOn(Schedulers.io());
  }

  public Single<Guess> submitGuess(GameWithGuesses game, String text) {
    Guess guess = new Guess();
    guess.setText(text);
    return proxy
        .submitGuess(game.getServiceKey(), guess)
        .map((g) -> {
          game.getGuesses().add(g);
          return g;
        })
        .map((g) -> {
          if (g.isSolution()) {
            CodebreakerDatabase
                .getInstance()
                .getGameDao()
                .insert(game)
                .flatMap((id) -> {
                  game
                      .getGuesses()
                      .forEach((g2) -> g2.setGameId(id));
                  return CodebreakerDatabase
                      .getInstance()
                      .getGuessDao()
                      .insert(game.getGuesses());
                })
                .subscribe();
          }
          return g;
        })
        .subscribeOn(Schedulers.io());
  }
}
















