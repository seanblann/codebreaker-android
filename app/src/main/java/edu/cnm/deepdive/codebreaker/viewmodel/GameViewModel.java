package edu.cnm.deepdive.codebreaker.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.codebreaker.R;
import edu.cnm.deepdive.codebreaker.model.pojo.GameWithGuesses;
import edu.cnm.deepdive.codebreaker.service.GameRepository;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class GameViewModel extends AndroidViewModel implements DefaultLifecycleObserver {

  private final MutableLiveData<GameWithGuesses> game;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final GameRepository repository;
  private final String pool;

  public GameViewModel(@NonNull Application application) {
    super(application);
    game = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    repository = new GameRepository(application);
    pool = application.getString(R.string.color_chars);
    startGame();
  }

  @Override
  public void onStop(@NonNull LifecycleOwner owner) {
    DefaultLifecycleObserver.super.onStop(owner);
    pending.clear();
  }

  public LiveData<GameWithGuesses> getGame() {
    return game;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public void startGame() {
    throwable.setValue(null);
    Disposable disposable = repository
        .startGame(pool, 3) //FIXME: Read length from preferences.
        .subscribe(
            game::postValue,
            this::postThrowable
        );
    pending.add(disposable);
  }

  public void submitGuess(String text) {
    throwable.setValue(null);
    GameWithGuesses game = this.game.getValue();
    //noinspection ConstantConditions
    Disposable disposable = repository
        .submitGuess(game, text)
        .subscribe(
            this.game::postValue,
            this::postThrowable
        );
    pending.add(disposable);
  }

  private void postThrowable(Throwable throwable) {
    Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }

}