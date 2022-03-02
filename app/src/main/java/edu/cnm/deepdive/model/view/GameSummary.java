package edu.cnm.deepdive.model.view;

import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;

@DatabaseView(viewName = "game_summary", value = GameSummary.SUMMARY_QUERY)
public class GameSummary {

  static final String SUMMARY_QUERY = "SELECT\n"
      + "    gs.length,\n"
      + "    COUNT(*) AS count_games,\n"
      + "    MIN(gs.guess_count) AS min_guesses,\n"
      + "    MAX(gs.guess_count) AS max_guesses,\n"
      + "    AVG(gs.guess_count) AS avg_guesses,\n"
      + "    MIN(gs.last_guess_timestamp - gs.first_guess_timestamp) AS min_time,\n"
      + "    MAX(gs.last_guess_timestamp - gs.first_guess_timestamp) AS max_time,\n"
      + "    AVG(gs.last_guess_timestamp - gs.first_guess_timestamp) AS avg_time\n"
      + "FROM (\n"
      + "    SELECT\n"
      + "        ga.length,\n"
      + "        gu.game_id,\n"
      + "        COUNT(*) AS guess_count,\n"
      + "        MIN(gu.created) AS first_guess_timestamp,\n"
      + "        MAX(gu.created) AS last_guess_timestamp\n"
      + "    FROM\n"
      + "        guess AS gu\n"
      + "        INNER JOIN game AS ga\n"
      + "            ON gu.game_id = ga.game_id\n"
      + "    GROUP BY\n"
      + "        ga.length,\n"
      + "        gu.game_id\n"
      + ") AS gs\n"
      + "GROUP BY\n"
      + "    gs.length";

  @ColumnInfo(index = true)
  public int length;

  @ColumnInfo(name = "count_games")
  private int countGames;

  @ColumnInfo(name = "min_guesses")
  private int minGuesses;

  @ColumnInfo(name = "max_guesses")
  private int maxGuesses;

  @ColumnInfo(name = "avg_guesses")
  private double avgGuesses;

  @ColumnInfo(name = "min_time")
  private int minTime;

  @ColumnInfo(name = "max_time")
  private int maxTime;

  @ColumnInfo(name = "avg_time")
  private double avgTime;

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public int getCountGames() {
    return countGames;
  }

  public void setCountGames(int countGames) {
    this.countGames = countGames;
  }

  public int getMinGuesses() {
    return minGuesses;
  }

  public void setMinGuesses(int minGuesses) {
    this.minGuesses = minGuesses;
  }

  public int getMaxGuesses() {
    return maxGuesses;
  }

  public void setMaxGuesses(int maxGuesses) {
    this.maxGuesses = maxGuesses;
  }

  public double getAvgGuesses() {
    return avgGuesses;
  }

  public void setAvgGuesses(double avgGuesses) {
    this.avgGuesses = avgGuesses;
  }

  public int getMinTime() {
    return minTime;
  }

  public void setMinTime(int minTime) {
    this.minTime = minTime;
  }

  public int getMaxTime() {
    return maxTime;
  }

  public void setMaxTime(int maxTime) {
    this.maxTime = maxTime;
  }

  public double getAvgTime() {
    return avgTime;
  }

  public void setAvgTime(double avgTime) {
    this.avgTime = avgTime;
  }
}
