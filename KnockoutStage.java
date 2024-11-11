import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// This function is the knockout stage after the groupStage. Uses the same PlayMatch class.
// This function does not return any value as the winner is found inside its public function

public class KnockoutStage{
    // Post group stage is 8 groups of 2
    Squad[][] knockoutSquads = new Squad[8][2];

    // 3 stages need to be tracked
    private List<Squad> winnersRoundOf16 = new ArrayList<>();
    private List<Squad> winnersQuarterFinals = new ArrayList<>();
    private List<Squad> winnersSemiFinals = new ArrayList<>();

    // Constructor
    KnockoutStage(Squad[][] knockoutSquads){
        this.knockoutSquads = knockoutSquads;
    }

    public void playTournament() {
        // Each stage is its own private function
        playRoundOf16();
        // Display winner of each round to the user
        System.out.println("\nWinners of Round Of 16:");
        for (Squad winner : this.winnersRoundOf16) {
            System.out.println(winner.getTeamName());
        }

        playQuarterFinals();
        System.out.println("\nWinners of Quarter Finals:");
        for (Squad winner : this.winnersQuarterFinals) {
            System.out.println(winner.getTeamName());
        }

        playSemiFinals();
        System.out.println("\nWinners of Semi-Finals:");
        for (Squad winner : this.winnersSemiFinals) {
            System.out.println(winner.getTeamName());
        }

        Squad Winner = playFinals();
        System.out.println("\nWinner of The World Cup: " + Winner.getTeamName());
    }

    private void playRoundOf16() {
        System.out.println("\n--- Word Cup Round of 16 Results --- \n");
        for (int i = 0; i < this.knockoutSquads.length; i += 2) {
            // Each result is returned and added to the subsequent list. 
            // PlayMatch class to play the game and get the winners from that, pass to getWinner function
            PlayMatch match = new PlayMatch(this.knockoutSquads[i][0], this.knockoutSquads[i + 1][1]);
            match.playMatch();
            this.winnersRoundOf16.add(getWinner(match.getTeam1(), match.getTeam2(),match.getTeam1Goals(),match.getTeam2Goals()));
            match = new PlayMatch(this.knockoutSquads[i][1], this.knockoutSquads[i + 1][0]);
            match.playMatch();
            this.winnersRoundOf16.add(getWinner(match.getTeam1(), match.getTeam2(),match.getTeam1Goals(),match.getTeam2Goals()));
        }
    }

    private void playQuarterFinals() {
        System.out.println("\n--- Word Cup Quarter Finals Results --- \n");
        for (int i = 0; i < this.winnersRoundOf16.size(); i += 2) {
            PlayMatch match = new PlayMatch(this.winnersRoundOf16.get(i), this.winnersRoundOf16.get(i + 1));
            match.playMatch();
            this.winnersQuarterFinals.add(getWinner(match.getTeam1(), match.getTeam2(),match.getTeam1Goals(),match.getTeam2Goals()));
        }
    }

    private void playSemiFinals() {
        System.out.println("\n--- Word Cup Semi Finals Results --- \n");
        for (int i = 0; i < winnersQuarterFinals.size(); i += 2) {
            PlayMatch match = new PlayMatch(this.winnersQuarterFinals.get(i), this.winnersQuarterFinals.get(i + 1));
            match.playMatch();
            this.winnersSemiFinals.add(getWinner(match.getTeam1(), match.getTeam2(),match.getTeam1Goals(),match.getTeam2Goals()));
        }
    }

    private Squad playFinals(){
        System.out.println("\n--- Word Cup Final Results --- \n");
        PlayMatch match = new PlayMatch(this.winnersSemiFinals.get(0), this.winnersSemiFinals.get(1));
        match.playMatch();
        return getWinner(match.getTeam1(), match.getTeam2(),match.getTeam1Goals(),match.getTeam2Goals());
    }

    private Squad getWinner(Squad team1, Squad team2, int team1Goals, int team2Goals) {
        // Determine winner based on goals
        
        if (team1Goals > team2Goals) { // Team1 winner
            // Display result to user
            System.out.println(team1.getTeamName() + " VS " + team2.getTeamName() + "\nSCORE: " + team1Goals + ':' + team2Goals);
            return team1;
        } else if (team1Goals < team2Goals) { // Team2 winner
            System.out.println(team1.getTeamName() + " VS " + team2.getTeamName() + "\nSCORE: " + team1Goals + ':' + team2Goals);
            return team2;
        } else { // Draw then randomly select to show penalties
            Random rand = new Random();
            if (rand.nextBoolean()){ // Team1 winner
                System.out.println(team1.getTeamName() + " VS " + team2.getTeamName() + "\nSCORE: " + team1Goals + ':' + team2Goals + "\nPENALTIES: " + team1.getTeamName() + " Winner ");
                return team1;
            }else{ // Team2 winner
                System.out.println(team1.getTeamName() + " VS " + team2.getTeamName() + "\nSCORE: " + team1Goals + ':' + team2Goals + "\nPENALTIES: " + team2.getTeamName() + " Winner ");
                return team2;
            }
        }
    }
}
