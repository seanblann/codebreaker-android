package edu.cnm.deepdive.codebreaker.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.preference.PreferenceManager;
import edu.cnm.deepdive.codebreaker.R;
import edu.cnm.deepdive.codebreaker.model.view.GamePerformance;
import edu.cnm.deepdive.codebreaker.service.GameRepository;
import edu.cnm.deepdive.model.view.GameSummary;
import java.util.List;

public class StatisticsViewModel extends AndroidViewModel {


  private final MutableLiveData<Integer> length;
  private final LiveData<GameSummary> summary;
  private final LiveData<List<GamePerformance>> rankings;
  private final GameRepository repository;
  private final SharedPreferences preferences;
  private final String codeLengthPrefKey;
  private final int codeLengthPrefDefault;

  public StatisticsViewModel(@NonNull Application application) {
    super(application);
    repository = new GameRepository(application);
    preferences = PreferenceManager.getDefaultSharedPreferences(application);
    codeLengthPrefKey = application.getString(R.string.code_length_pref_key);
    codeLengthPrefDefault = application.getResources()
        .getInteger(R.integer.code_length_pref_default);
    length = new MutableLiveData<>(getCodeLengthPreference());
    summary = Transformations.switchMap(length, repository::getSummary);
    rankings = Transformations.switchMap(length, repository::getRankingsByDuration);
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

  public LiveData<List<GamePerformance>> getRankings() {
    return rankings;
  }

  private int getCodeLengthPreference() {
    return preferences.getInt(codeLengthPrefKey, codeLengthPrefDefault);
  }


}