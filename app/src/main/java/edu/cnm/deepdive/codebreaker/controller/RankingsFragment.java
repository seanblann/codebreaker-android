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
import edu.cnm.deepdive.adapter.RankingAdapter;
import edu.cnm.deepdive.codebreaker.databinding.FragmentRankingsBinding;
import edu.cnm.deepdive.codebreaker.model.view.GamePerformance;
import edu.cnm.deepdive.codebreaker.viewmodel.StatisticsViewModel;
import java.util.List;

public class RankingsFragment extends Fragment implements OnSeekBarChangeListener {

  private FragmentRankingsBinding binding;
  private StatisticsViewModel viewModel;


  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentRankingsBinding.inflate(inflater, container, false);
    binding.length.setOnSeekBarChangeListener(this);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //noinspection ConstantConditions
    viewModel = new ViewModelProvider(getActivity()).get(StatisticsViewModel.class);
    viewModel
        .getRankings()
        .observe(getViewLifecycleOwner(), this::handleRankings);
    viewModel
        .getLength()
        .observe(getViewLifecycleOwner(), (length) -> binding.length.setProgress(length));

  }

  private void handleRankings(List<GamePerformance> rankings) {
    RankingAdapter adapter = new RankingAdapter(getContext(), rankings);
    binding.rankings.setAdapter(adapter);
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
    // Intentionally empty.
  }

  @Override
  public void onStopTrackingTouch(SeekBar seekBar) {
    // Intentionally empty.
  }
}