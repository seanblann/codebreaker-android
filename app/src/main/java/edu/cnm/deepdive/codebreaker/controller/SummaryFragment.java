package edu.cnm.deepdive.codebreaker.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.codebreaker.databinding.FragmentSummaryBinding;
import edu.cnm.deepdive.codebreaker.viewmodel.GameViewModel;

public class SummaryFragment extends Fragment {

  private GameViewModel viewModel;
  private FragmentSummaryBinding binding;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentSummaryBinding.inflate(inflater, container, false);
    //TODO: Attach event listeners to view objects in binding.
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
          //TODO: Populate TextView objects in binding with summary data.
        });
    viewModel.getLength()
        .observe(getViewLifecycleOwner(), (length) -> {
          //TODO: Update SeekBar in binding if necessary.
        });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}