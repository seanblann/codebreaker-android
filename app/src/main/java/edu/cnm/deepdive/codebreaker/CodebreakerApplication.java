package edu.cnm.deepdive.codebreaker;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.service.CodebreakerDatabase;
import edu.cnm.deepdive.codebreaker.service.CodebreakerServiceProxy;
import edu.cnm.deepdive.codebreaker.service.GameRepository;
import edu.cnm.deepdive.codebreaker.service.GoogleSignInService;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CodebreakerApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    CodebreakerDatabase.setContext(this);
    GoogleSignInService.setContext(this);
    //TODO Initialize repositories that need an app-level context.
    //etc.
  }

}
