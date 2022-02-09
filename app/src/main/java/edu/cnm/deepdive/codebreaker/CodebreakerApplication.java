package edu.cnm.deepdive.codebreaker;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.service.CodebreakerServiceProxy;
import edu.cnm.deepdive.codebreaker.service.GameRepository;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CodebreakerApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
//    GameRepository repository = new GameRepository(this);
//    repository
//        .startGame("XYZ", 3)
//        .flatMap((game -> repository.submitGuess(game, "ZZZ")))
//        .subscribe();
    //Initialize database.
    //Initialize repositories that need an app-level context.
    //etc.
  }

}
