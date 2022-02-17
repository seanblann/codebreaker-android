package edu.cnm.deepdive.adapter;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.adapter.GuessAdapter.Holder;

public class GuessAdapter extends RecyclerView.Adapter<Holder>{

  //TODO:  Add fields for context, list of guesses, etc.

  //TODO: Define constructor with parameters to initialize context, list of guesses, etc.

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    //TODO: Inflate layout for guess item, and pass resulting binder object to Holder constructor.
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.bind(position);
  }

  @Override
  public int getItemCount() {
    //TODO: Return the number of guesses in the list.
    return 0;
  }

  class Holder extends RecyclerView.ViewHolder {

    //TODO: Add fields for binder object, etc.

    public Holder(@NonNull View itemView) {
      super(itemView);

    }
    private void bind(int position) {
      //TODO Populate binder object with content from model object identified by position.

    }

  }

}
