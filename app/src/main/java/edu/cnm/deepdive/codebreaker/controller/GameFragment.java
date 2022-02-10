package edu.cnm.deepdive.codebreaker.controller;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.snackbar.Snackbar;
import edu.cnm.deepdive.codebreaker.R;
import edu.cnm.deepdive.codebreaker.databinding.FragmentGameBinding;
import edu.cnm.deepdive.codebreaker.model.entity.Guess;
import edu.cnm.deepdive.codebreaker.viewmodel.GameViewModel;
import java.util.HashMap;
import java.util.Map;

public class GameFragment extends Fragment {

  private GameViewModel gameViewModel;
  private FragmentGameBinding binding;
  private Map<Character, Integer> colorValueMap;
  private Map<Character, String> colorLabelMap;
  private Character[] poolCharacters;
  private Spinner[] spinners;
  private int codeLength;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentGameBinding.inflate(inflater, container, false);
    binding.submit.setOnClickListener((view) -> {
      //noinspection ConstantConditions
      String text = binding.guess
          .getText()
          .toString()
          .trim();
      gameViewModel.submitGuess(text);
    });
    setupMaps();
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
    gameViewModel
        .getThrowable()
        .observe(getViewLifecycleOwner(), (throwable) -> {
          if (throwable != null) {
            //noinspection ConstantConditions
            Snackbar
                .make(binding.getRoot(), throwable.getMessage(), Snackbar.LENGTH_LONG)
                .show();
          }
        });
    gameViewModel
        .getGame()
        .observe(getViewLifecycleOwner(), (game) -> {
          binding.guess.setText("");
          ArrayAdapter<Guess> adapter = new ArrayAdapter<>(getContext(),
              android.R.layout.simple_list_item_1, game.getGuesses());
          binding.guesses.setAdapter(adapter);
          binding.submit.setEnabled(!game.isSolved());
        });
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.game_options, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled;
    if (item.getItemId() == R.id.new_game) {
      gameViewModel.startGame("ABCDEF", 3);
      handled = true;
    } else {
      handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  private void setupMaps(){
    String colors = getString(R.string.color_chars);
    char[] colorChars = colors.toCharArray();
    poolCharacters = colors
        .chars()
        .mapToObj((value) -> (char) value)
        .toArray(Character[]::new);
    Resources resources = getResources();
    int[] colorValues = resources.getIntArray(R.array.color_values);
    String[] colorLabels = resources.getStringArray(R.array.color_labels);
    colorValueMap = buildValueMap(colorChars, colorValues);
    colorLabelMap = buildLabelMap(colorChars, colorLabels);
  }

  private static Map<Character, Integer> buildValueMap(char[] chars, int[] values) {
    Map<Character, Integer> valueMap = new HashMap<>();
    for (int i = 0; i < chars.length; i++) {
      valueMap.put(chars[i], values[i]);
    }
    return valueMap;
  }

  private static Map<Character, String> buildLabelMap(char[] chars, String[] labels) {
    Map<Character, String> labelMap = new HashMap<>();
    for (int i = 0; i < chars.length; i++) {
      labelMap.put(chars[i], labels[i]);
    }
    return labelMap;
  }
}












