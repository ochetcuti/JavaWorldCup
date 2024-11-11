import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

// This function takes 4 teams and plays them against eachother using the PlayMatch class
// Returns the teams in random order (to be sorted in main)

public class groupStage{
    // each group has a 4 teams
    private Squad[] squads = new Squad[4];
    // 2D Match results array
    private List<List<Integer>> matchResults = new ArrayList<>();
    // Constructor
    public groupStage(Squad team1, Squad team2, Squad team3, Squad team4) {
        // 4 teams per group
        this.squads[0] = team1;
        this.squads[1] = team2;
        this.squads[2] = team3;
        this.squads[3] = team4;

        // Points then Goal differnce in array
        for (int i = 0; i < squads.length; i++) {
            this.matchResults.add(new ArrayList<>(List.of(0, 0)));
        }
    }

    // Getters and Setters
    public Squad[] getSquads() {
        return squads;
    }

    public Squad getSquad(int squadID) {
        return this.squads[squadID];
    }

    public void setSquad(int squadID,Squad squads) {
        this.squads[squadID] = squads;
    }

    public List<Integer> getResults(int squadID) {
        return Arrays.asList(this.matchResults.get(squadID).get(0), this.matchResults.get(squadID).get(1));
    }

    public int getPoints(int squadID) {
        return this.matchResults.get(squadID).get(0);
    }

    public int getGoalDifference(int squadID) {
        return this.matchResults.get(squadID).get(1);
    }

    // End of Getters and Setters

    // Simple swap to swap the squads and the match results (used in main)
    public void swapSquads(int squadIndex, int newIndex) {
        // Swap Squads

        // Not using .this as refers to main
        Squad tempSquad = squads[squadIndex];
        squads[squadIndex] = squads[newIndex];
        squads[newIndex] = tempSquad;
    
        // Swap Match Results
        List<Integer> tempResults = matchResults.get(squadIndex);
        matchResults.set(squadIndex, matchResults.get(newIndex));
        matchResults.set(newIndex, tempResults);
    }
    
    // Starts the GroupStage
    public void playTournament() {
        // Dual for loop much like a bubble sort ensures all teams play eachother
        for (int i = 0; i < this.squads.length; i++) {
            for (int j = i + 1; j < this.squads.length; j++) {
                // Play the match from class Playmatch returning teams and results
                PlayMatch match = new PlayMatch(this.squads[i], this.squads[j]);
                match.playMatch();
                updateResults(match.getTeam1(), match.getTeam2(), match.getTeam1Goals(), match.getTeam2Goals());
                // Displays result to user
                System.out.println(this.squads[i].getTeamName() + " VS " + this.squads[j].getTeamName() + "\nSCORE: " + match.getTeam1Goals() + ':' +match.getTeam2Goals());
            }
        }
    }

    // Get index as all teams play eachother saves passing the index from the double for loops
    private int findSquadIndex(Squad team) {
        for (int i = 0; i < this.squads.length; i++) {
            if (this.squads[i].equals(team)) { // simple squad ref check
                return i;
            }
        }
        return -1; // Impossible but must return a value
    }

    // Update score to match results, converted from list to array
    private void updateResults(Squad team1, Squad team2, int team1Goals, int team2Goals) {
        int indexTeam1 = findSquadIndex(team1);
        int indexTeam2 = findSquadIndex(team2);
        
        // Get results to add
        List<Integer> team1Results = this.matchResults.get(indexTeam1);
        List<Integer> team2Results = this.matchResults.get(indexTeam2);

        // Update wins and goals
        if (team1Goals > team2Goals) {
            team1Results.set(0, team1Results.get(0) + 3); // Win Team1
        } else if (team2Goals > team1Goals) {
            team2Results.set(0, team2Results.get(0) + 3); // Win Team2
        } else {
            team1Results.set(0, team1Results.get(0) + 1); // Draw
            team2Results.set(0, team2Results.get(0) + 1);
        }
        team1Results.set(1, team1Results.get(1) + (team1Goals - team2Goals)); // Update goal difference
        team2Results.set(1, team2Results.get(1) + (team2Goals - team1Goals));
        
        // Return list
        this.matchResults.set(indexTeam1, team1Results);
        this.matchResults.set(indexTeam2, team2Results);
    }
}
