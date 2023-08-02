package model;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Team {

    private StringProperty name;
    private Players players;

    public Team(String name) {
        this.name = new SimpleStringProperty();
        this.name.set(name);
        this.players = new Players();

    }

    public Players getPlayers() {
        return this.players;
    }

    public void filterPlayersList(String name, String level, int ag1, int ag2) {
        this.players.filterList(name, level, ag1, ag2);
    }

    public String getName() {
        return name.get();
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public ReadOnlyStringProperty nameProperty() {
        return this.name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableList<Player> getCurrentPlayers() {
        return this.players.getPlayersList();
    }

    public boolean hasName(String name) {
        return getName().toLowerCase().equals(name.toLowerCase().trim());
    }

    public ReadOnlyIntegerProperty countPlayerProperty() {
        return players.playerNumberProperty();
    }

    public ReadOnlyDoubleProperty countAvgAgeProperty() {
        return players.avgAgeProperty();
    }

    public ReadOnlyDoubleProperty countAvgCreditProperty() {
        return players.avgCreditProperty();
    }

    public void filterList(String name, String level, int ag1, int ag2) {
        this.players.filterList(name, level, ag1, ag2);
    }

}
