import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.Comparator;


public class Main {
    public static Squad[] squads = new Squad[32];
    public static void main(String[] args){
        // track Squads
        int managerIndex = 0;
        try {
            // Process Managers
            Scanner managerScanner = new Scanner(new File("Managers.csv"));
            managerScanner.nextLine(); // Skip header row
            while (managerScanner.hasNextLine() && managerIndex < squads.length) {
                String line = managerScanner.nextLine();
                String[] data = line.split("\\,");
                // get data based on positioning
                String firstName = data[0].trim(); //trim whitespace
                String Surname = data[1].trim();
                String teamName = data[2].trim();
                String favouredFormation = data[3].trim();
                double respect = Double.parseDouble(data[4]);
                double ability = Double.parseDouble(data[5]);
                double knowledge = Double.parseDouble(data[6]);
                double belief = Double.parseDouble(data[7]);
                // create new manager with this data
                Manager manager = new Manager(firstName, Surname, teamName, favouredFormation, 
                                            respect, ability, knowledge, belief);
                squads[managerIndex] = new Squad(teamName, manager);
                // next squad
                managerIndex++;
            }
            managerScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // process players
        try {
            // UTF 8 to ensure not parse errors
            Scanner playerScanner = new Scanner(new File("Players.csv"),"UTF-8");
            playerScanner.nextLine(); // skip header row
            while (playerScanner.hasNextLine()) {
                String line = playerScanner.nextLine();
                String[] data = line.split("\\,");
                String firstName = data[0].trim();
                String Surname = data[1].trim();
                String teamName = data[2].trim();
                String position = data[3].trim();
                double fitness = Double.parseDouble(data[4]);
                double passingAccuracy = Double.parseDouble(data[5]);
                double shotAccuracy = Double.parseDouble(data[6]);
                double shotFrequency = Double.parseDouble(data[7]);
                double defensiveness = Double.parseDouble(data[8]);
                double aggression = Double.parseDouble(data[9]);
                double positioning = Double.parseDouble(data[10]);
                double dribbling = Double.parseDouble(data[11]);
                double chanceCreation = Double.parseDouble(data[12]);
                double offsideAdherence = Double.parseDouble(data[13]);
                for (int i = 0; i < managerIndex; i++) {
                    if (squads[i].getTeamName().equals(teamName)) {
                        // create player to the correct squad based on team name
                        Player player = new Player(firstName, Surname, teamName, position, fitness, passingAccuracy, 
                                                shotAccuracy, shotFrequency, defensiveness, aggression, positioning, 
                                                dribbling, chanceCreation, offsideAdherence);
                        squads[i].addPlayer(player);
                        break;
                    }
                }
            }
            playerScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Start the torunament simulation
        runTournament();
    }

    public static Team getTeam(Squad s){
        Team t = new Team(s.getTeamName(), s.getManager());

        // manager's preferred formation
        String[] formation = s.getManager().getFavouredFormation().split("-");
        int numDefenders = Integer.parseInt(formation[0]);
        int numMidfielders = Integer.parseInt(formation[1]);
        int numForwards = Integer.parseInt(formation[2]);

        // position-specific players
        ArrayList<Player> goalkeepers = new ArrayList<>();
        ArrayList<Player> defenders = new ArrayList<>();
        ArrayList<Player> midfielders = new ArrayList<>();
        ArrayList<Player> forwards = new ArrayList<>();

        // players by position
        for (int i = 0; i < 26; i++) {
            Player player = s.getPlayer(i);
            switch (player.getPosition().toLowerCase()) {
                case "goal keeper":
                    goalkeepers.add(player);
                    break;
                case "defender":
                    defenders.add(player);
                    break;
                case "midfielder":
                    midfielders.add(player);
                    break;
                case "forward":
                    forwards.add(player);
                    break;
            }
        }

        // attribute comparator
        class AttributeComparator implements Comparator<Player> {
            @Override
            public int compare(Player p1, Player p2) {
                // Get all atributes and divide by all the atributes to get the avg
                double average1 = (p1.getFitness() + p1.getPassingAccuracy() + p1.getShotAccuracy() + p1.getShotFrequency() + 
                p1.getDefensiveness() + p1.getAggression() + p1.getPositioning() + p1.getDribbling() + 
                p1.getChanceCreation() + p1.getOffsideAdherence()) / 10.0;
                double average2 = (p2.getFitness() + p2.getPassingAccuracy() + p2.getShotAccuracy() + p2.getShotFrequency() + 
                p2.getDefensiveness() + p2.getAggression() + p2.getPositioning() + p2.getDribbling() + 
                p2.getChanceCreation() + p2.getOffsideAdherence()) / 10.0;
                
                return Double.compare(average2, average1);
            }
        }

        goalkeepers.sort(new AttributeComparator());
        defenders.sort(new AttributeComparator());
        midfielders.sort(new AttributeComparator());
        forwards.sort(new AttributeComparator());

        // Only one goalkeeper is needed - take the highest
        t.addPlayer(goalkeepers.get(0));

        // add defenders
        for (int i = 0; i < numDefenders; i++) {
            t.addPlayer(defenders.get(i));
        }

        // add midfielders
        for (int i = 0; i < numMidfielders; i++) {
            t.addPlayer(midfielders.get(i));
        }

        // add forwards
        for (int i = 0; i < numForwards; i++) {
            t.addPlayer(forwards.get(i));
        }
        return t;
    }

    public static void runTournament(){
        System.out.println("\n World Cup ");
        // randomise squad index
        Random rand = new Random();
        for (int i = 0; i < squads.length; i++) {
            int randIndex = rand.nextInt(squads.length);
            Squad temp = squads[randIndex];
            squads[randIndex] = squads[i];
            squads[i] = temp;
        }

        // squad in groups
        groupStage[] groups = new groupStage[8];
        for (int i = 0, groupIndex = 0; i < 32; i+=4) {
            groups[groupIndex] = new groupStage(squads[i], squads[i+1], squads[i+2], squads[i+3]);
            groupIndex++;
        }


        // Set 2D array for the knockout squads
        Squad[][] knockoutSquads = new Squad[8][2];

        // Play Group stages
        // Display to the user the current stage
        System.out.println("\n--- Word Cup Group Stages ---");
        for (int i = 0; i < groups.length; i++) {
            System.out.println("\n--- Group "+ i +" --- \n");
            groups[i].playTournament();
            // Get top 2 teams of current group
            int numberOfSquads = groups[i].getSquads().length;
            for (int j = 0; j < numberOfSquads - 1; j++) {
                // Bubble sort to correctly sort the teams
                boolean swapped = false;
                // Decrease range each pass to optimise
                for (int k = 0; k < numberOfSquads - j - 1; k++) {
                    int pointTeam = groups[i].getPoints(k);
                    int pointsNextTeam = groups[i].getPoints(k + 1);
                    int goalDiffTeam = groups[i].getGoalDifference(k);
                    int goalDiffNextTeam = groups[i].getGoalDifference(k + 1);
        
                    // Points ELSE goal diff
                    if (pointTeam < pointsNextTeam || (pointTeam == pointsNextTeam && goalDiffTeam < goalDiffNextTeam)) {
                        // Swap teams function
                        groups[i].swapSquads(k, k + 1);
                        swapped = true;
                    }
                }
                // No swaps then break
                if (!swapped) {
                    break;
                }
            }
            // Put top two from the current group into knockout array
            knockoutSquads[i][0] = groups[i].getSquad(0); // Winner
            knockoutSquads[i][1] = groups[i].getSquad(1); // 2nd place
            // Display the results of each group from current group
            System.out.println("\n--- Word Cup Group "+ i +" Results --- \n");
            for (int j = 0; j < groups[i].getSquads().length; j++) {
                System.out.println(groups[i].getSquad(j).getTeamName() + " | Points = " + groups[i].getPoints(j) + " | Goal Difference = " + groups[i].getGoalDifference(j)); 
            }
        }
        // Run the knockouts
        System.out.println("\n--- Knockout Stage --- \n");
        KnockoutStage knockoutStage = new KnockoutStage(knockoutSquads);
        knockoutStage.playTournament();
        System.out.println("\n--- End of the World Cup --- ");


        
    }
}

