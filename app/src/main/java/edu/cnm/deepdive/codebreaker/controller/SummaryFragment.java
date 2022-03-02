package edu.cnm.deepdive.codebreaker.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.codebreaker.R;
import edu.cnm.deepdive.codebreaker.databinding.FragmentSummaryBinding;
import edu.cnm.deepdive.codebreaker.viewmodel.GameViewModel;

public class SummaryFragment extends Fragment implements OnSeekBarChangeListener {

  private static final float MILLISECONDS_PER_SECOND = 1000.0f;
  private static final int SECONDS_PER_MINUTE = 60;
  private GameViewModel viewModel;
  private FragmentSummaryBinding binding;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentSummaryBinding.inflate(inflater, container, false);
    binding.length.setOnSeekBarChangeListener(this);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //noinspection ConstantConditions
    viewModel = new ViewModelProvider(getActivity()).get(GameViewModel.class);
    getLifecycle().addObserver(viewModel);
    viewModel
        .getSummary()
        .observe(getViewLifecycleOwner(),(summary) -> {
          if (summary == null) {
            binding.countGames.setText(String.valueOf(0));
            binding.minGuesses.setText("");
            binding.maxGuesses.setText("");
            binding.avgGuesses.setText("");
            binding.minTime.setText("");
            binding.maxTime.setText("");
            binding.avgTime.setText("");
          } else {
            binding.countGames.setText(String.valueOf(summary.getCountGames()));
            binding.minGuesses.setText(String.valueOf(summary.getMinGuesses()));
            binding.maxGuesses.setText(String.valueOf(summary.getMaxGuesses()));
            binding.avgGuesses.setText(getString(R.string.avg_guess_format, summary.getAvgGuesses()));
            binding.minTime.setText(getDuration(summary.getMinTime()));
            binding.maxTime.setText(getDuration(summary.getMaxTime()));
            binding.avgTime.setText(getDuration((int) Math.round(summary.getAvgTime())));
          }
        });
    viewModel
        .getLength()
        .observe(getViewLifecycleOwner(), (length) -> binding.length.setProgress(length));
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  @Override
  public void onProgressChanged(SeekBar seekBar, int newValue, boolean byUser) {
    binding.lengthValue.setText(String.valueOf(newValue));
    if (byUser) {
      viewModel.setLength(newValue);
    }
  }

  @Override
  public void onStartTrackingTouch(SeekBar seekBar) {
    // do nothing
  }

  @Override
  public void onStopTrackingTouch(SeekBar seekBar) {
    // do nothing
  }

  private String getDuration(int milliseconds) {
    int seconds = Math.round(milliseconds / MILLISECONDS_PER_SECOND);
    int minutes = seconds / SECONDS_PER_MINUTE;
    seconds %= SECONDS_PER_MINUTE;
    return getString(R.string.mmss_format, minutes, seconds);
  }
}
