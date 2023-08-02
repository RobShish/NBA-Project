package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;

public class Record {

    private StringProperty winTeam;
    private StringProperty loseTeam;
    private IntegerProperty gameNumber;
    private IntegerProperty round;

    public Record(String win, String lose, Integer No, Integer round) {
        this.winTeam = new SimpleStringProperty(win);
        this.loseTeam = new SimpleStringProperty(lose);
        this.gameNumber = new SimpleIntegerProperty(No + 1);
        this.round = new SimpleIntegerProperty(round);
    }

    public String getWinTeam() {
        return winTeam.get();
    }

    public void setWinTeam(String winTeam) {
        this.winTeam.set(winTeam);
    }

    public ReadOnlyStringProperty winTeamProperty() {
        return winTeam;
    }

    public String getLoseTeam() {
        return loseTeam.get();
    }

    public void setLoseTeam(String loseTeam) {
        this.loseTeam.set(loseTeam);
    }

    public ReadOnlyStringProperty loseTeamProperty() {
        return loseTeam;
    }

    public Integer getGameNumber() {
        return gameNumber.get();
    }

    public void setGameNumber(Integer gameNumber) {
        this.gameNumber.set(gameNumber);
    }

    public ReadOnlyIntegerProperty gameNumberProperty() {
        return gameNumber;
    }

    public Integer getRound() {
        return round.get();
    }

    public void setRound(Integer round) {
        this.round.set(round);
    }

    public ReadOnlyIntegerProperty roundProperty() {
        return round;
    }

}
