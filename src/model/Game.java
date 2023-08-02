package model;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
public class Game
{
    private ObservableList<Team> teams;
    private ObservableList<Team> currentTeams;
    private ObservableList<Team> results;
    public IntegerProperty term;
    public Integer size()
    {
        return teams.size();
    }
    public void add(Team e)
    {
        teams.add(e);
    }
    public ObservableList<Team> get()
    {
        return this.teams;
    }
    
        public ObservableList<Team> getCurrentTeams()
    {
        return this.currentTeams;
    }
    
    public Game(Integer Term)
    {
        this.teams= FXCollections.<Team>observableArrayList();
        currentTeams = FXCollections.<Team>observableArrayList();
        this.results=FXCollections.<Team>observableArrayList();
        this.term = new SimpleIntegerProperty();
        this.term.set(Term);
        teams.addListener(new ListChangeListener<Team>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Team> c) {
                currentTeams.clear();
                currentTeams.addAll(teams);
            }
        });
    }
    
    public ReadOnlyIntegerProperty termProperty(){
        return this.term;
    }
    
    public ReadOnlyStringProperty team1(){
        try{
            return this.currentTeams.get(0).nameProperty();
        }
        catch(Exception e){
            return new SimpleStringProperty("");
        }
    }

    public ReadOnlyStringProperty team2(){
        try{
            return this.currentTeams.get(1).nameProperty();
        }
        catch(Exception e){
            return new SimpleStringProperty("");
        }
    }    
    
    
    public Integer getTerm(){
        return term.get();
    }
    

    public ObservableList<Team> play()
    {
        Double firstTeam=teams.get(0).getPlayers().CountAvgCredit();
        Double secondTeam=teams.get(1).getPlayers().CountAvgCredit();

        if (firstTeam>secondTeam)
        {
            Double diff= (firstTeam-secondTeam)/(5.0);
            for(Player p : teams.get(0).getCurrentPlayers())
            {
                p.updateCredit(diff);
                results.add(teams.get(0));
                results.add(teams.get(1));
            }
            for(Player p : teams.get(1).getCurrentPlayers())
            {
                p.updateCredit(-diff);
            }
        }
        else
        {
            results.add(teams.get(1));
            results.add(teams.get(0));
            Double diff= (secondTeam-firstTeam)/(5.0);
            for(Player p : teams.get(0).getCurrentPlayers())
            {
                p.updateCredit(-diff);
            }
            for(Player p : teams.get(1).getCurrentPlayers())
            {
                p.updateCredit(diff);
            }

        }
        return results;
    }

}




