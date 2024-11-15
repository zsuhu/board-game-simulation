import java.util.ArrayList;

public class Faction {

    private ArrayList<ResourceGenerator> resourceGenerators;
    private int stockpileA, stockpileB, stockpileC;

    public Faction() {
        resourceGenerators = new ArrayList<>();
        resourceGenerators.add(new ResourceGenerator());
        resourceGenerators.add(new ResourceGenerator());
        stockpileA = 0;
        stockpileB = 0;
        stockpileC = 0;
    }
    // generates resources for each resource generator
    public void generateResources(int num) {
        for (ResourceGenerator generator : resourceGenerators) {
            String resource = generator.generateResource(num);
            if ("A".equals(resource)) {
                stockpileA++;
            } else if ("B".equals(resource)) {
                stockpileB++;
            } else {
                stockpileC++;
            }
        }
    }
    // when resources go over 8, each resource will be divided by 2
    // each resource will have a random chance to be divided based on its relative proportions
    // (the count of each resource in the stockpile over total resources)
    public void loseOverflow() {
        int sumOtherResources = stockpileA + stockpileB + stockpileC;
        if (sumOtherResources >= 8) {
            for (int i = 0; i < sumOtherResources; i++) {
                int randNum = (int) (Math.random() * sumOtherResources);
                if (randNum < stockpileA) {
                    stockpileA /= 2;
                } else if (randNum < stockpileA + stockpileB) {
                    stockpileB /= 2;
                } else {
                    stockpileC /= 2;
                }
                sumOtherResources = stockpileA + stockpileB + stockpileC;
            }
        }
    }
    // will steal resources randomly from another faction using a probability weighted by the amount of resources in each stockpile
    public void stealResourceFrom(Faction otherFaction) {
        int sumOtherResources = otherFaction.stockpileA + otherFaction.stockpileB + otherFaction.stockpileC;
        if (sumOtherResources > 0) {
            int randNum = (int) (Math.random() * sumOtherResources);
            if (randNum < otherFaction.stockpileA) {
                otherFaction.stockpileA--;
                this.stockpileA++;
            } else if (randNum < otherFaction.stockpileA + otherFaction.stockpileB) {
                otherFaction.stockpileB--;
                this.stockpileB++;
            } else {
                otherFaction.stockpileC--;
                this.stockpileC++;
            }
        }
    }
    // will construct new generators if there are 1 of each resource, subtract 1 from each until false
    public void constructNewGenerators() {
        while (stockpileA > 0 && stockpileB > 0 && stockpileC > 0) {
            stockpileA--;
            stockpileB--;
            stockpileC--;
            resourceGenerators.add(new ResourceGenerator());
        }
    }
    // toString method to print out each resource count and number of generators
    public String toString() {
        return "A: " + stockpileA + "\nB: " + stockpileB + "\nC: " + stockpileC + "\nResourceGenerators: " + getNumberOfResourceGenerators();
    }
    // returns the number of generators that the faction has
    public int getNumberOfResourceGenerators() {
        return resourceGenerators.size();
    }
}
