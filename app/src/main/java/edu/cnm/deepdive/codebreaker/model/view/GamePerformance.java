package edu.cnm.deepdive.codebreaker.model.view;

import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;
import java.util.Date;

@DatabaseView(viewName = "game_performance", value = GamePerformance.PERFORMANCE_QUERY )
public class GamePerformance {

static final String PERFORMANCE_QUERY = "SELECT\n"
    + "    g.game_id,\n"
    + "    g.created,\n"
    + "    g.length,\n"
    + "    (gs.last_guess_timestamp - gs.first_guess_timestamp) AS duration,\n"
    + "    gs.guess_count\n"
    + "FROM\n"
    + "    game AS g\n"
    + "    JOIN (\n"
    + "    SELECT\n"
    + "        game_id,\n"
    + "        MIN(created) AS first_guess_timestamp,\n"
    + "        MAX(created) AS last_guess_timestamp,\n"
    + "        COUNT(*) AS guess_count\n"
    + "    FROM\n"
    + "        guess\n"
    + "    GROUP BY\n"
    + "        game_id\n"
    + "    ) AS gs\n"
    + "    ON gs.game_id = g.game_id";

@ColumnInfo(name = "game_id")
private long id;

private Date created;

private int length;

private int duration;

@ColumnInfo(name = "guess_count")
private int guessCount;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public int getGuessCount() {
    return guessCount;
  }

  public void setGuessCount(int guessCount) {
    this.guessCount = guessCount;
  }
}
