package edu.cnm.deepdive.codebreaker.model.entity;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

@SuppressWarnings("ALL")
@Entity(
    tableName = "guess",
    foreignKeys = {
        @ForeignKey(
            entity = Game.class,
            parentColumns = {"game_id"},
            childColumns = {"game_id"},
            onDelete = ForeignKey.CASCADE
        )
    },
    indices = {
        @Index(value = {"game_id", "created"})
    }
)
public class Guess {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "guess_id")
  private long id;

  @Expose
  @SerializedName("id")
  @Ignore
  private String serviceKey;

  //TODO: Add converter for date-to-long
  @Expose
  @NonNull
  private Date created;

  @Expose
  @NonNull
  private String text;

  @Expose
  @ColumnInfo(name = "exact_matches")
  private int exactMatches;

  @Expose
  @ColumnInfo(name = "near_matches")
  private int nearMatches;

  @Expose
  private boolean solution;

  @ColumnInfo(name = "game_id", index = true)
  private long gameId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getServiceKey() {
    return serviceKey;
  }

  public void setServiceKey(String serviceKey) {
    this.serviceKey = serviceKey;
  }

  @NonNull
  public Date getCreated() {
    return created;
  }

  public void setCreated(@NonNull Date created) {
    this.created = created;
  }

  @NonNull
  public String getText() {
    return text;
  }

  public void setText(@NonNull String text) {
    this.text = text;
  }

  public int getExactMatches() {
    return exactMatches;
  }

  public void setExactMatches(int exactMatches) {
    this.exactMatches = exactMatches;
  }

  public int getNearMatches() {
    return nearMatches;
  }

  public void setNearMatches(int nearMatches) {
    this.nearMatches = nearMatches;
  }

  public boolean isSolution() {
    return solution;
  }

  public void setSolution(boolean solution) {
    this.solution = solution;
  }

  public long getGameId() {
    return gameId;
  }

  public void setGameId(long gameId) {
    this.gameId = gameId;
  }

  @SuppressLint("DefaultLocale")
  @NonNull
  @Override
  public String toString() {
    return String.format("%1$s (%2$d / %3$d)", text, exactMatches, nearMatches);
  }

}
