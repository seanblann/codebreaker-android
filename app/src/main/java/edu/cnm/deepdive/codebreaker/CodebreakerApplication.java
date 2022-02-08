package edu.cnm.deepdive.codebreaker;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.service.CodebreakerServiceProxy;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CodebreakerApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    Game game = new Game();
    game.setPool("ABC");
    game.setLength(3);
    CodebreakerServiceProxy
        .getInstance()
        .startGame(game)
        .subscribeOn(Schedulers.io())
        .subscribe();
    //Initialize database.
    //Initialize repositories that need an app-level context.
    //etc.
  }

}
