package edu.cnm.deepdive.codebreaker.service;

import android.content.Context;
import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.model.entity.Guess;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.function.Function;

public class GameRepository {

  private final Context context;
  private final CodebreakerServiceProxy proxy;


  public GameRepository(Context context) {
    this.context = context;
    proxy = CodebreakerServiceProxy.getInstance();
  }

  public Single<Game> startGame(String pool, int length) {
    Game game = new Game();
    game.setPool(pool);
    game.setLength(length);
    return proxy
        .startGame(game)
        .subscribeOn(Schedulers.io());
  }
  public Single<Guess> submitGuess(Game game, String text) {
    Guess guess = new Guess();
    guess.setText(text);
    return proxy
        .submitGuess(game.getId(), guess)
        .map((g) -> {
          game.getGuesses() .add(g);
          if (g.isSolution()) {
            game.setSolved(true);
            game.setText(g.getText());
          }
          return g;
        })
        .subscribeOn(Schedulers.io());
  }
}
















