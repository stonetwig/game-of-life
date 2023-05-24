import java.util.Arrays;
import java.util.Random;

public class Board {
    private int[][] previousGen;
    private int[][] currentGen;
    private final int[][] nextGen;
    private int generation = 0;

    public Board(int size) {
        this.previousGen = new int[size][size];
        this.currentGen = new int[size][size];
        this.nextGen = new int[size][size];
    }


    public void initGenerations() {
        // Reset all values to 0 in order to not have any remaining values
        for (int i = 0; i < currentGen.length; i++) {
            Arrays.fill(previousGen[i], 0);
            Arrays.fill(currentGen[i], 0);
            Arrays.fill(nextGen[i], 0);
        }

        Random rand = new Random();
        for (int i = 0; i < currentGen.length; i++) {
            for (int j = 0; j < currentGen[i].length; j++) {
                int n = rand.nextInt(10);
                if (n > 8) {
                    currentGen[i][j] = 1;
                } else {
                    currentGen[i][j] = 0;
                }
                nextGen[i][j] = 0;
            }
        }
    }

    public void logGeneration() {
        generation = generation += 1;
        System.out.println("Generation #" + generation);
        for (int[] ints : currentGen) {
            System.out.println(Arrays.toString(ints));
        }
    }

    public void setCurrentGen(int[][] currentGen) {
        this.currentGen = currentGen;
    }

    public void setNextGen() {
        // Reset all values to 0 in order to not have any remaining values
        for (int[] ints : nextGen) {
            Arrays.fill(ints, 0);
        }

        for (var i = 0; i < currentGen.length; i++) {
            for (var j = 0; j < currentGen[i].length; j++) {
                var neighbors = getNumberOfNeighbors(i, j);

                if (currentGen[i][j] == 1) {
                    if (neighbors == 2 || neighbors == 3) {
                        nextGen[i][j] = 1;
                    } else {
                        nextGen[i][j] = 0;
                    }
                } else if (currentGen[i][j] == 0 && neighbors == 3) {
                    nextGen[i][j] = 1;
                }
            }
        }

        previousGen = Arrays.stream(currentGen).map(int[]::clone).toArray(int[][]::new);
        currentGen = Arrays.stream(nextGen).map(int[]::clone).toArray(int[][]::new);
    }

    public boolean isPreviousAndCurrentGenTheSame() {
        return Arrays.deepEquals(previousGen, currentGen);
    }

    public int getNumberOfNeighbors(int row, int col) {
        return getNeighbor(row - 1, col - 1) + getNeighbor(row - 1, col) + getNeighbor(row - 1, col + 1)
                + getNeighbor(row, col - 1) + getNeighbor(row, col + 1) + getNeighbor(row + 1, col - 1)
                + getNeighbor(row + 1, col) + getNeighbor(row + 1, col + 1);
    }

    private int getNeighbor(int row, int col) {
        try {
            return currentGen[row][col] == 1 ? 1 : 0;
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    public int[][] getCurrentGen() {
        return this.currentGen;
    }
}
