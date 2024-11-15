import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter number of factions: ");
        int numFaction = input.nextInt();

        System.out.println("Enter number of resource generators for victory: ");
        int numGeneratorsToWin = input.nextInt();

        // creates a list of factions and adds each faction object to it
        ArrayList<Faction> factions = new ArrayList<>();
        for (int i = 0; i < numFaction; i++) {
            factions.add(new Faction());
        }

        int factionTurn = 0;
        boolean gameLoop = false;

        // creates the game loop
        while (!gameLoop) {
            Faction currentFaction = factions.get(factionTurn);
            int diceRoll = ResourceGenerator.rollDice();
            System.out.println("\nFaction " + factionTurn + " rolled a " + diceRoll);

            // generates resources for each faction when the dice roll is not 7
            if (diceRoll != 7) {
                System.out.println("Generating Resources...");
                for (Faction faction : factions) {
                    faction.generateResources(diceRoll);
                }
            } else { // eliminates overflow for each faction in faction list when the dice rolls a 7
                System.out.println("Eliminating Overflow...");
                for (Faction faction : factions) {
                    faction.loseOverflow();
                }

                // blocks a random number
                int blockedNumber = ResourceGenerator.rollDice();
                ResourceGenerator.blockNumber(blockedNumber);
                System.out.println("Blocking Number...");

                // gets a random faction to steal from that is not the current faction
                ArrayList<Faction> randFactionsSet = new ArrayList<>(factions);
                randFactionsSet.remove(currentFaction);
                int randFactionElem = (int) (Math.random() * randFactionsSet.size());
                Faction randomFaction = randFactionsSet.get(randFactionElem);

                System.out.println("Stealing Resource...");
                currentFaction.stealResourceFrom(randomFaction);

            }

            // toString method is called before constructing new generators and after
            System.out.println(currentFaction);
            System.out.println("Constructing Generators...");
            currentFaction.constructNewGenerators();
            System.out.println(currentFaction);

            // checks win condition to stop the game loop
            if (currentFaction.getNumberOfResourceGenerators() >= numGeneratorsToWin) {
                System.out.println("Faction " + factionTurn + " wins with " + numGeneratorsToWin + " generators!");
                gameLoop = true;
            } else {
                factionTurn = (factionTurn + 1) % numFaction; // this will increment the turn for the next faction
            }
        }

        input.close();

    }

}


