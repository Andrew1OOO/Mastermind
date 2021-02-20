import java.util.Scanner;

public class PuzzleRun
{
    private int numDigits;
    private int numPuzzles;
    private Player player;
    private int totalGuesses;
    private long totalSolveTime;
    
    public PuzzleRun(int numDigits, int numPuzzles)
    {
        this.numDigits = numDigits;
        this.numPuzzles = numPuzzles;
        player = new Player();
    }
    
    public void logResults(Puzzle puzzle)
    {
        totalGuesses = totalGuesses + puzzle.getGuesses();
        totalSolveTime = totalSolveTime + puzzle.getSolveTime();
    }
    
    public int getTotalGuesses()
    {
        return totalGuesses;
    }
    
    public long getTotalSolveTime()
    {
        return totalSolveTime;
    }

    public void run()
    {
        Puzzle puzzle;
        for (int i = 1; i <= numPuzzles; i++)
        {
            System.out.println("Creating and solving puzzle: " + i);
            puzzle = new Puzzle(numDigits);
            player.solve(puzzle);
            System.out.println("\nPuzzle " + i + " complete");
            puzzle.printResults();
            logResults(puzzle);
            System.out.println();
        }      
    }
        
    public static void main(String[] args)
    {
        GuessResult result;
        int numPuzzles;
        Puzzle puzzle;
        PuzzleRun pr;
        String guess;
        Scanner scanner = new Scanner(System.in);
        System.out.print("1.Interactive Mode or 2.Batch Mode? ");
        int mode = scanner.nextInt();
        System.out.print("How many digits in your puzzles? ");
        int numDigits = scanner.nextInt();

        if (mode == 1)
        {
            puzzle = new Puzzle(numDigits);
            scanner.nextLine();
            do
            {
                System.out.print("\nGuess? ");
                guess = scanner.nextLine();
                result = puzzle.guess(guess);
                System.out.println(result);
            } while (!result.solved);
            System.out.println("\nPuzzle complete");
            puzzle.printResults();
        } else
        {
            System.out.print("How many puzzles to solve? ");
            numPuzzles = scanner.nextInt();
            pr = new PuzzleRun(numDigits, numPuzzles);
            System.out.println("\nBeginning puzzle run...");
            pr.run();
            System.out.println("\nRun complete");
            System.out.println("-----Run Results-----");
            System.out.println("Agent name: " + pr.player.getName());
            System.out.println("Total puzzles: " + numPuzzles);
            System.out.println("Average guesses: " + (pr.getTotalGuesses()/numPuzzles));
            System.out.println("Average time (milliseconds): " + (pr.getTotalSolveTime()/numPuzzles));
        }
    }
}
