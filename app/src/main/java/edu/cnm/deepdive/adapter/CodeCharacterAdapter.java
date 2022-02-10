package edu.cnm.deepdive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import edu.cnm.deepdive.codebreaker.R;
import java.util.Map;

public class CodeCharacterAdapter extends ArrayAdapter<Character> {

  private final Map<Character, Integer> colorValueMap;
  private final Map<Character, String> colorLabelMap;
  private final LayoutInflater inflater;

  public CodeCharacterAdapter(Context context, Map<Character, Integer> colorValueMap,
      Map<Character, String> colorLabelMap, Character[] poolChars) {
    super(context, R.layout.item_swatch_spinner, R.id.label, poolChars);
    setDropDownViewResource(R.layout.dropdown_item_swatch_spinner);
    this.colorValueMap = colorValueMap;
    this.colorLabelMap = colorLabelMap;
    inflater = LayoutInflater.from(context);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    View view = (convertView != null)
        ? convertView
        : inflater.inflate(R.layout.item_swatch_spinner, parent, false);
    return populateLayout(position, view);
  }


  @Override
  public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    View view = (convertView != null)
        ? convertView
        : inflater.inflate(R.layout.dropdown_item_swatch_spinner, parent, false);
    return populateLayout(position, view);
  }

  private View populateLayout(int position, View view) {
    Character c = getItem(position);
    ImageView swatch = view.findViewById(R.id.swatch);
    //noinspection ConstantConditions
    swatch.setBackgroundColor(colorValueMap.get(c));
    String label = colorLabelMap.get(c);
    swatch.setContentDescription(label);
    ((TextView) view.findViewById(R.id.label)).setText(label);
    return view;
  }

}
