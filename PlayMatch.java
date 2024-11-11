import java.util.Random;

public class PlayMatch {
    public Squad team1;
    public Squad team2;
    public int team1Goals;
    public int team2Goals;

    // Constructor
    public PlayMatch(Squad team1, Squad team2) {
        // 2 teams per match
        this.team1 = team1;
        this.team2 = team2;
        // scoreline
        this.team1Goals = 0;
        this.team2Goals = 0;
    }

    private int calculateGoals(double score) {
        // scale the score by a power
        double randomBoundedPower = (0.1 + Math.random() * 0.6);
        double scaledScore = Math.pow(score, randomBoundedPower);

        // Randomly add goals as anything could happen in the game
        Random rand = new Random();
        int randAdjustment = rand.nextInt(3) - 1; // -1, 0, +1
        scaledScore += randAdjustment;

        // Round to nearest int
        int goals = (int) Math.round(scaledScore);
        
        goals = Math.max(goals, 0);// Ensure goals are not less than 0

        return goals;
    }

    public void playMatch() {
        // Get team from main code.
        Team t1 = Main.getTeam(this.team1);
        Team t2 = Main.getTeam(this.team2);

        // decide the winner
        // Score tracker (float)
        double t1Score = 0;
        double t2Score = 0;

        for (int i = 0; i < 11; i++) {
            // Get Averages to find the total 'score'
            t1Score += (t1.getPlayer(i).getFitness() + t1.getPlayer(i).getPassingAccuracy() + t1.getPlayer(i).getShotAccuracy() + 
            t1.getPlayer(i).getShotFrequency() + t1.getPlayer(i).getDefensiveness() + t1.getPlayer(i).getAggression() + 
            t1.getPlayer(i).getPositioning() + t1.getPlayer(i).getDribbling() + t1.getPlayer(i).getChanceCreation() + 
            t1.getPlayer(i).getOffsideAdherence()) / 10.0;

            t2Score += (t2.getPlayer(i).getFitness() + t2.getPlayer(i).getPassingAccuracy() + t2.getPlayer(i).getShotAccuracy() + 
            t2.getPlayer(i).getShotFrequency() + t2.getPlayer(i).getDefensiveness() + t2.getPlayer(i).getAggression() + 
            t2.getPlayer(i).getPositioning() + t2.getPlayer(i).getDribbling() + t2.getPlayer(i).getChanceCreation() + 
            t2.getPlayer(i).getOffsideAdherence()) / 10.0;

            // Set the fitness based on the previous fitness level with a modifier
            // Scale factor of 5 and then /100 to limit bottoming out players
            double newFitness = Math.max(0, t1.getPlayer(i).getFitness() - Math.log(1 + (5 * t1.getPlayer(i).getFitness())) / 100);
            t1.getPlayer(i).setFitness(newFitness);
            newFitness = Math.max(0, t2.getPlayer(i).getFitness() - (0.05 * t2.getPlayer(i).getFitness()));
            t2.getPlayer(i).setFitness(newFitness);
        }

        team1Goals = calculateGoals(t1Score);
        team2Goals = calculateGoals(t2Score);
    }

    // Getters

    public Squad getTeam1() {
        return team1;
    }

    public Squad getTeam2() {
        return team2;
    }

    public int getTeam1Goals() {
        return team1Goals;
    }

    public int getTeam2Goals() {
        return team2Goals;
    }
}