package model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Teams {

    public ObservableList<Team> currentTeams;
    public ObservableList<Team> teams;

    public Teams() {
        this.currentTeams = FXCollections.<Team>observableArrayList();
        this.teams = FXCollections.observableArrayList(
                new Team("Suns"),
                new Team("Bulls"),
                new Team("Hawks"),
                new Team("Nets"));
        TeamInit();
        this.currentTeams.addAll(this.teams);
        teams.addListener(new ListChangeListener<Team>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Team> c) {
                currentTeams.clear();
                currentTeams.addAll(teams);
            }
        });
    }

    public ObservableList<Team> currentTeams() {
        return this.currentTeams;
    }

    public void TeamInit() {
        for (Team t : this.teams) {
            switch (t.getName()) {
                case "Suns":
                    t.getCurrentPlayers().add(new Player("Devin Booker", 2500.0, 26, 1));
                    t.getCurrentPlayers().add(new Player("Chris Paul", 1500.0, 37, 3));
                    t.getCurrentPlayers().add(new Player("Deandre Ayton", 2000.0, 24, 22));
                    t.getCurrentPlayers().add(new Player("Kevin Durant", 3000.0, 34, 35));
                    t.getCurrentPlayers().add(new Player("Terrence Ross", 1000.0, 32, 8));
                    for (Player player : t.getCurrentPlayers()) {
                        player.setTeam(t);
                    }
                    break;
                case "Bulls":
                    t.getCurrentPlayers().add(new Player("Andre Drummond", 1500.0, 29, 3));
                    t.getCurrentPlayers().add(new Player("Zach LaVine", 3000.0, 28, 8));
                    t.getCurrentPlayers().add(new Player("Dalen Terry", 900.0, 20, 25));
                    t.getCurrentPlayers().add(new Player("Terry Taylor", 1000.0, 23, 32));
                    t.getCurrentPlayers().add(new Player("Carlik Jones", 800.0, 25, 22));
                    for (Player player : t.getCurrentPlayers()) {
                        player.setTeam(t);
                    }
                    break;
                case "Hawks":
                    t.getCurrentPlayers().add(new Player("Trae Young", 2200.0, 24, 11));
                    t.getCurrentPlayers().add(new Player("John Collins", 2000.0, 25, 20));
                    t.getCurrentPlayers().add(new Player("Aaron Holiday", 800.0, 26, 3));
                    t.getCurrentPlayers().add(new Player("Jalen Johnson", 1100.0, 21, 1));
                    t.getCurrentPlayers().add(new Player("Trent Forrest", 1200.0, 24, 2));
                    for (Player player : t.getCurrentPlayers()) {
                        player.setTeam(t);
                    }
                    break;
                case "Nets":
                    t.getCurrentPlayers().add(new Player("Mikal Bridges", 2400.0, 26, 1));
                    t.getCurrentPlayers().add(new Player("Ben Simmons", 2000.0, 26, 10));
                    t.getCurrentPlayers().add(new Player("Patty Mills", 900.0, 34, 8));
                    t.getCurrentPlayers().add(new Player("Joe Harris", 1200.0, 31, 12));
                    t.getCurrentPlayers().add(new Player("Seth Curry", 1900.0, 32, 30));
                    for (Player player : t.getCurrentPlayers()) {
                        player.setTeam(t);
                    }
                    break;

            }
        }
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public boolean hasTeam(String name) {
        for (Team e : teams) {
            if (e.hasName(name) && !name.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public Team getTeam(String name) {
        for (Team p : teams) {
            if (p.hasName(name)) {
                return p;
            }
        }
        return null;
    }

    public void remove(Team p) {
        this.teams.remove(p);
    }

    public void remove(ArrayList<Team> selectedItems) {
        this.teams.removeAll(selectedItems);
    }

    public void addTeams(ArrayList<Team> selectedItems) {
        this.teams.addAll(selectedItems);
    }

    public ObservableList<Player> allPlayersList() {
        ObservableList<Player> viewList;
        viewList = FXCollections.<Player>observableArrayList();

        for (Team t : this.teams) {
            viewList.addAll(t.getCurrentPlayers());
        }
        return viewList;
    }

}
