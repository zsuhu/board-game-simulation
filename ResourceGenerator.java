public class ResourceGenerator {

    private String resource;
    private int resourceNum;
    private static final String[] RESOURCE = {"A", "B", "C"};
    private static int blockedNum = 0;

    // constructs a resource generator with a random resource and random resource number
    public ResourceGenerator() {
        this.resource = RESOURCE[(int) (Math.random() * 3)];
        do {
            this.resourceNum = rollDice();
        } while (this.resourceNum == 7);
    }

    // returns a resource if it's not blocked and if it's num matches the resource num
    public String generateResource(int num) {
        if (num == blockedNum) {
            return null;
        }
        if (num != resourceNum) {
            return null;
        }
        return resource;
    }

    // assigns a number to be blocked
    public static void blockNumber(int num) {
        blockedNum = num;
    }

    // returns the sum of two random dice rolls
    public static int rollDice() {
        return (int) (((Math.random() * 6) + 1) + ((Math.random() * 6) + 1));
    }
}
