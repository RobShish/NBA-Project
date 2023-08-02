package model;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;

public class Player {

    private StringProperty name;
    private DoubleProperty credit;
    private StringProperty level;
    private IntegerProperty age;
    private Team team;
    private IntegerProperty No;

    public Player(String name, Double credit, Integer age, Integer No) {
        this.level = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        setName(name);
        this.credit = new SimpleDoubleProperty();
        setCredit(credit);
        this.age = new SimpleIntegerProperty();
        setAge(age);
        this.No = new SimpleIntegerProperty();
        setNo(No);

    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setCredit(Double credit) {
        this.credit.set(credit);
        setLevel();
    }

    public void setAge(Integer age) {
        this.age.set(age);
    }

    public void setNo(Integer No) {
        this.No.set(No);
    }

    public Integer getNo() {
        return No.get();
    }

    public ReadOnlyIntegerProperty getPlayerNoProperty() {
        return this.No;
    }

    public String getNameByNo(int No) {
        if (this.No.get() == No) {
            return name.get();
        }
        return null;
    }

    public void setTeam(Team t) {
        this.team = t;
    }

    public String getTeamName() {
        return team.getName();
    }

    public Team getTeam() {
        return this.team;
    }

    public ReadOnlyStringProperty getTeamNameProperty() {
        StringProperty team = new SimpleStringProperty();
        team.set(getTeamName());
        return team;
    }

    public void setLevel() {
        double creditValue = this.credit.get();
        if (creditValue >= 0 && creditValue < 1000) {
            this.level.set("Edge");
        } else if (creditValue >= 1000 && creditValue < 1500) {
            this.level.set("Common");
        } else if (creditValue >= 1500 && creditValue < 2000) {
            this.level.set("Core");
        } else if (creditValue >= 2000) {
            this.level.set("All Star");
        }
    }

    public void updateCredit(double credit) {
        this.credit.set(this.credit.get() + credit);
        setLevel();
    }

    public void update(String name, double credit, int age, int No) {
        setName(name);
        setCredit(credit);
        setAge(age);
        setNo(No);
    }

    public Integer getAge() {
        return this.age.get();
    }

    public ReadOnlyIntegerProperty getPlayerAgeProperty() {
        return this.age;
    }

    public String getName() {
        return name.get();
    }

    public ReadOnlyStringProperty nameProperty() {
        return this.name;
    }

    public Double getCredit() {
        return credit.get();
    }

    public ReadOnlyDoubleProperty getPlayerCreditProperty() {
        return this.credit;
    }

    public String getLevel() {
        return level.get();
    }

    public ReadOnlyStringProperty levelProperty() {
        return this.level;
    }

    public boolean hasName(String name) {
        return getName().toLowerCase().equals(name.toLowerCase().trim());
    }

}
