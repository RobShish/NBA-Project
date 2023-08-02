package model;

import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Season {

    private ObservableList<Game> schedule;
    private ObservableList<Game> currentSchedule;
    private ObservableList<Team> current;
    private ObservableList<Team> currentTeams;
    private BooleanProperty allTeamsAdded;
    private Integer round;
    private ObservableList<Record> records;

    public Season() {
        currentSchedule = FXCollections.<Game>observableArrayList();
        currentTeams = FXCollections.<Team>observableArrayList();
        schedule = FXCollections.observableArrayList();
        current = FXCollections.observableArrayList();
        records = FXCollections.observableArrayList();

        round = 0;
        schedule.addListener(new ListChangeListener<Game>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Game> c) {
                currentSchedule.clear();
                currentSchedule.addAll(schedule);
            }
        });

    }

    public Integer round() {
        return round;
    }

    public void currentInit(ObservableList<Team> teams) {
        current.clear();
        records.clear();
        round = 0;
        for (Team team : teams) {
            current.add(team);
        }
    }

    public ObservableList<Team> getCurrentTeams() {
        return current;
    }

    public ObservableList<Game> getCurrentSchedule() {
        return currentSchedule;
    }

    public void addTeams(ObservableList<Team> teams) {
        Game g = new Game(schedule.size() + 1);
        g.add(teams.get(0));
        g.add(teams.get(1));
        schedule.add(g);
        current.removeAll(teams);
    }

    public String play(Integer round) {
        String res = "";
        ArrayList<Team> Win = new ArrayList<>();
        ArrayList<Team> Lost = new ArrayList<>();
        ArrayList<Team> result = new ArrayList<>();
        if (schedule.size() == 0) {
            return "No Games to play!\nPlease add games to this round.";
        }
        for (Game g : schedule) {
            for (Team t : g.play()) {
                result.add(t);
            }
            Win.add(result.get(0));
            Lost.add(result.get(1));
            result.clear();
        }
        current.addAll(Win);
        for (int i = 0; i < Win.size(); i++) {
            records.add(new Record(Win.get(i).getName(), Lost.get(i).getName(), i, round));
        }
        res += "All games finished! You can check results now!\n";

        if (current.size() == 1) {
            res += "This season ends!\n";
            res += current.get(0).getName() + " is the Champion!!";
        }
        schedule.clear();
        return res;
    }

    public String playGame() {
        round = round + 1;
        String res = play(round);
        return res;
    }

    public ObservableList<Record> record() {
        return records;
    }
}
