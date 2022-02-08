package edu.cnm.deepdive.codebreaker;

import android.app.Application;
import com.facebook.stetho.Stetho;

public class CodebreakerApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    //Initialize database.
    //Initialize repositories that need an app-level context.
    //etc.
  }

}
