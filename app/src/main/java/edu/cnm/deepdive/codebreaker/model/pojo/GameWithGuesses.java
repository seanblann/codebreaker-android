package edu.cnm.deepdive.codebreaker.model.pojo;

import androidx.room.Relation;
import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.model.entity.Guess;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class GameWithGuesses extends Game {

  @Relation(
      entity = Guess.class,
      entityColumn = "game_id",
      parentColumn = "game_id"
  )


  private List<Guess> guesses = new LinkedList<>();

  public List<Guess> getGuesses() {
    return guesses;
  }

  public void setGuesses(List<Guess> guesses) {
    this.guesses = guesses;
  }

  public boolean isSolved() {
    return guesses
        .stream()
        .anyMatch(Guess::isSolution);
  }

  public String getText() {
    return guesses
        .stream()
        .filter(Guess::isSolution)
        .map(Guess::getText)
        .findFirst()
        .orElse(null);
  }
}
