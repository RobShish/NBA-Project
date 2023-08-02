package model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import java.util.stream.Collectors;

public class Players {

    private final ObservableList<Player> Players;
    private DoubleProperty avgAge;
    private DoubleProperty avgCredit;
    private IntegerProperty playerNumber;
    private ObservableList<Player> filteredPlayers;

    public Players() {

        this.Players = FXCollections.<Player>observableArrayList();
        this.filteredPlayers = FXCollections.observableArrayList(Players);
        this.avgAge = new SimpleDoubleProperty();
        this.avgAge.set(CountAvgAge());
        this.avgCredit = new SimpleDoubleProperty();
        this.avgCredit.set(CountAvgCredit());
        this.playerNumber = new SimpleIntegerProperty();
        this.playerNumber.set(getPlayerNumber());

        this.Players.addListener(new ListChangeListener<Player>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Player> c) {
                avgAge.set(CountAvgAge());
                avgCredit.set(CountAvgCredit());
                playerNumber.set(getPlayerNumber());
            }
        });
    }

    public void updateStats() {
        this.avgAge.set(CountAvgAge());
        this.avgCredit.set(CountAvgCredit());
        this.playerNumber.set(getPlayerNumber());
    }

    public ReadOnlyDoubleProperty avgAgeProperty() {
        avgAge.set(CountAvgAge());
        return this.avgAge;
    }

    public ReadOnlyDoubleProperty avgCreditProperty() {
        this.avgCredit.set(CountAvgCredit());
        return this.avgCredit;
    }

    public ReadOnlyIntegerProperty playerNumberProperty() {
        this.playerNumber.set(getPlayerNumber());
        return this.playerNumber;
    }

    public void addPlayer(Player player) {
        this.Players.add(player);
    }

    public void removePlayer(Player player) {
        this.Players.remove(player);
    }

    public boolean hasPlayer(String name) {
        for (Player e : this.Players) {
            if (e.hasName(name) && !name.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public Player getPlayer(String name) {
        for (Player p : this.Players) {
            if (p.hasName(name)) {
                return p;
            }
        }
        return null;
    }

    public void remove(ArrayList<Player> selectedItems) {
        this.Players.removeAll(selectedItems);
    }

    public void addPlayers(ArrayList<Player> selectedItems) {
        this.Players.addAll(selectedItems);
    }

    public ObservableList<Player> getPlayersList() {
        return this.Players;
    }

    public ObservableList<Player> getFilteredPlayers() {
        return filteredPlayers;
    }

    public int getPlayerNumber() {
        return this.Players.size();
    }

    public double CountAvgCredit() {
        double sum = 0;
        for (Player p : this.Players) {
            sum = sum + p.getCredit();
        }
        if (sum == 0) {
            return 0;
        } else {
            return sum / (double) (getPlayerNumber());
        }

    }

    public double CountAvgAge() {
        double sum = 0;
        for (Player p : this.Players) {
            sum = sum + (double) p.getAge();
        }
        if (sum == 0) {
            return 0;
        } else {
            return sum / (double) (getPlayerNumber());
        }

    }

    public void filterList(String name, String level, int ageFrom, int ageTo) {
        if ((name == null || name.isEmpty()) && (level == null || level.isEmpty()) && ageFrom == 0 && ageTo == 0) {
            filteredPlayers.setAll(Players);
        } else {
            final String lowerCaseNameFilter = name == null ? "" : name.toLowerCase();
            final String lowerCaseLevelFilter = level == null ? "" : level.toLowerCase();

            List<Player> filtered = Players.stream()
                    .filter(player -> (player.getName().toLowerCase().contains(lowerCaseNameFilter)
                    && player.getLevel().toLowerCase().contains(lowerCaseLevelFilter))
                    && (ageFrom == 0 || player.getAge() >= ageFrom)
                    && (ageTo == 0 || player.getAge() <= ageTo))
                    .collect(Collectors.toList());
            filteredPlayers.setAll(filtered);
        }
    }

    public ObservableList<Player> getFilteredPlayersList() {
        return this.filteredPlayers;
    }

}
