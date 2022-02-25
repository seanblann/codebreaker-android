package edu.cnm.deepdive.codebreaker.controller;

import android.content.Intent;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.codebreaker.R;
import edu.cnm.deepdive.codebreaker.databinding.ActivityLoginBinding;
import edu.cnm.deepdive.codebreaker.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

  private ActivityLoginBinding binding;
  private LoginViewModel viewModel;
  private ActivityResultLauncher<Intent> launcher;
  private boolean silent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    getLifecycle().addObserver(viewModel);
    launcher = registerForActivityResult(new StartActivityForResult(), viewModel::completeSignIn);
    silent = true;
    viewModel
        .getAccount()
            .observe(this, (account) -> {/* TODO: handle account */});
    viewModel
        .getThrowable()
            .observe(this, (throwable -> {/*TODO: handle throwable*/}));
  }


}