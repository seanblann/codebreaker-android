package edu.cnm.deepdive.codebreaker.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import edu.cnm.deepdive.codebreaker.R;
import edu.cnm.deepdive.codebreaker.model.pojo.GameWithGuesses;
import edu.cnm.deepdive.codebreaker.service.GameRepository;
import edu.cnm.deepdive.model.view.GameSummary;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class GameViewModel extends AndroidViewModel implements DefaultLifecycleObserver {

  private final MutableLiveData<GameWithGuesses> game;
  private final MutableLiveData<Integer> length;
  private final LiveData<GameSummary> summary;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final GameRepository repository;
  private final String pool;
  private final SharedPreferences preferences;
  private final String codeLengthPrefKey;
  private final int codeLengthPrefDefault;

  public GameViewModel(@NonNull Application application) {
    super(application);
    repository = new GameRepository(application);
    preferences = PreferenceManager.getDefaultSharedPreferences(application);
    codeLengthPrefKey = application.getString(R.string.code_length_pref_key);
    codeLengthPrefDefault = application.getResources()
        .getInteger(R.integer.code_length_pref_default);
    game = new MutableLiveData<>();
    length = new MutableLiveData<>(getCodeLengthPreference());
    summary = Transformations.switchMap(length, repository::getSummary);
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
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

  public LiveData<Integer> getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length.setValue(length);
  }

  public LiveData<GameSummary> getSummary() {
    return summary;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public void startGame() {
    throwable.setValue(null);
    Disposable disposable = repository
        .startGame(pool, getCodeLengthPreference())
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

  private int getCodeLengthPreference() {
    return preferences.getInt(codeLengthPrefKey, codeLengthPrefDefault);
  }


}