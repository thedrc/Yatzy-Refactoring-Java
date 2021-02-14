import java.util.Arrays;
import java.util.stream.Stream;

public class Yatzy {

    public static int chance(int d1, int d2, int d3, int d4, int d5) {
        int total;
        total = Stream.of(d1,d2,d3,d4,d5).mapToInt(d -> d).sum();
        return total;
    }

    public static int yatzy(int... dice) {
        int[] counts = new int[6];
        for (int die : dice)
            counts[die-1]++;
        for (int i = 0; i != 6; i++)
            if (counts[i] == 5)
                return 50;
        return 0;
    }

    private static int combineHandler(Stream<Integer> dices, int category){
        int sum;
        sum = dices.filter(s -> s == category).mapToInt(Integer::valueOf).sum();
        return sum;
    }


    public static int ones(int d1, int d2, int d3, int d4, int d5) {
        return combineHandler(Stream.of(d1,d2,d3,d4,d5),1);
    }

    public static int twos(int d1, int d2, int d3, int d4, int d5) {
        return combineHandler(Stream.of(d1,d2,d3,d4,d5),2);
    }

    public static int threes(int d1, int d2, int d3, int d4, int d5) {
        return combineHandler(Stream.of(d1,d2,d3,d4,d5),3);
    }

    protected int[] dice;
    public Yatzy(int d1, int d2, int d3, int d4, int _5) {
        dice = new int[5];
        dice[0] = d1;
        dice[1] = d2;
        dice[2] = d3;
        dice[3] = d4;
        dice[4] = _5;
    }

    public int fours() {
        return combineHandler(Arrays.stream(dice).boxed(),4);
    }

    public int fives() {
        return combineHandler(Arrays.stream(dice).boxed(),5);
    }

    public int sixes() {
        return combineHandler(Arrays.stream(dice).boxed(),6);
    }

    private static int xOfKind(Stream<Integer> dices,int x){
        int[] counts = diceCounter(dices);
        for (int i = 0; i < 6; i++)
            if (counts[i] >= x)
                return (i+1) * x;
        return 0;
    }

    private static int[] diceCounter(Stream<Integer> dices){
        int[] counts = new int[6];
        dices.forEach(s -> counts[s-1]++);
        return counts;
    }
    private static int x_pair(int[]  counts,boolean isOnePair){
        int n = 0;
        int score = 0;
        for (int i = 0; i < 6; i += 1)
            if (counts[6-i-1] >= 2) {
                if(isOnePair) return (6-i)*2;
                n++;
                score += (6-i);
            }
        if (n == 2)
            return score * 2;
        else
            return 0;
    }

    public static int score_pair(int d1, int d2, int d3, int d4, int d5) {
        int[] counts = diceCounter(Stream.of(d1,d2,d3,d4,d5));
        return x_pair(counts,true);
    }

    public static int two_pair(int d1, int d2, int d3, int d4, int d5) {
        int[] counts = diceCounter(Stream.of(d1,d2,d3,d4,d5));
        return x_pair(counts,false);
    }

    public static int four_of_a_kind(int _1, int _2, int d3, int d4, int d5) {
        return xOfKind(Stream.of(_1,_2,d3,d4,d5),4);
    }

    public static int three_of_a_kind(int d1, int d2, int d3, int d4, int d5) {
        return xOfKind(Stream.of(d1,d2,d3,d4,d5),3);
    }

    public static int smallStraight(int d1, int d2, int d3, int d4, int d5) {
        int[] tallies;
        tallies = diceCounter(Stream.of(d1,d2,d3,d4,d5));
        if (tallies[0] == 1 &&
            tallies[1] == 1 &&
            tallies[2] == 1 &&
            tallies[3] == 1 &&
            tallies[4] == 1)
            return 15;
        return 0;
    }

    public static int largeStraight(int d1, int d2, int d3, int d4, int d5) {
        int[] tallies;
        tallies = diceCounter(Stream.of(d1,d2,d3,d4,d5));
        if (tallies[1] == 1 &&
            tallies[2] == 1 &&
            tallies[3] == 1 &&
            tallies[4] == 1
            && tallies[5] == 1)
            return 20;
        return 0;
    }

    public static int fullHouse(int d1, int d2, int d3, int d4, int d5) {
        int[] tallies;
        boolean _2 = false;
        int i;
        int _2_at = 0;
        boolean _3 = false;
        int _3_at = 0;

        tallies = diceCounter(Stream.of(d1,d2,d3,d4,d5));

        for (i = 0; i != 6; i += 1)
            if (tallies[i] == 2) {
                _2 = true;
                _2_at = i+1;
            }

        for (i = 0; i != 6; i += 1)
            if (tallies[i] == 3) {
                _3 = true;
                _3_at = i+1;
            }

        if (_2 && _3)
            return _2_at * 2 + _3_at * 3;
        else
            return 0;
    }
}



