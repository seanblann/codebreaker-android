package edu.cnm.deepdive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.adapter.RankingAdapter.Holder;
import edu.cnm.deepdive.codebreaker.R;
import edu.cnm.deepdive.codebreaker.databinding.ItemRankingBinding;
import edu.cnm.deepdive.codebreaker.model.view.GamePerformance;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<Holder> {

  private final List<GamePerformance> rankings;
  private final LayoutInflater inflater;
  private final DateFormat dateFormat;
  private final DateFormat timeFormat;
  private final String dateTimeOrderFormat;
  private final String durationFormat;

  public RankingAdapter(Context context, List<GamePerformance> rankings) {
    this.rankings = rankings;
    inflater = LayoutInflater.from(context);
    dateFormat = android.text.format.DateFormat.getDateFormat(context);
    timeFormat = android.text.format.DateFormat.getTimeFormat(context);
    dateTimeOrderFormat = context.getString(R.string.date_time_order_format);
    durationFormat = context.getString(R.string.mmss_format)
  }


  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemRankingBinding binding = ItemRankingBinding.inflate(inflater, parent, false);
    return new Holder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.bind(position);

  }

  @Override
  public int getItemCount() {
    return rankings.size();
  }


  class Holder extends RecyclerView.ViewHolder {

    private final ItemRankingBinding binding;

    public Holder(@NonNull ItemRankingBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(int postition) {
      GamePerformance item = rankings.get(postition);
      Date created = item.getCreated();
      binding.created.setText(String.format(dateTimeOrderFormat,
          dateFormat.format(created), timeFormat.format(created)));
      binding.guessCount.setText(String.valueOf(item.getGuessCount()));
      int seconds = Math.round(item.getDuration() / 1000f);
      int minutes = seconds / 60;
      seconds %= 60;
      binding.duration.setText(String.format(durationFormat, minutes, seconds));
    }

  }

}
