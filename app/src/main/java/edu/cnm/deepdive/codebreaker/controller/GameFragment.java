package edu.cnm.deepdive.codebreaker.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.snackbar.Snackbar;
import edu.cnm.deepdive.codebreaker.databinding.FragmentGameBinding;
import edu.cnm.deepdive.codebreaker.model.entity.Guess;
import edu.cnm.deepdive.codebreaker.viewmodel.GameViewModel;

public class GameFragment extends Fragment {

  private GameViewModel gameViewModel;
  private FragmentGameBinding binding;

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
          //TODO: Enable/disable controls.
        });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}