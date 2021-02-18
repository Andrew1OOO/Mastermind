import java.util.Random; 

public class Puzzle
{
    private Integer numDigits;
    private String secretNumber;
    private boolean solved;
    private long startTime;
    private long endTime;
    private int numGuesses;
    
    Puzzle(Integer numDigits)
    {
        System.out.println("Building a puzzle of length " + numDigits);
        this.numDigits = numDigits;
        solved = false;
        numGuesses = 0;
        startTime = System.currentTimeMillis();
        endTime = System.currentTimeMillis();
        secretNumber = "";
        Integer randomDigit = 0;
        Random random = new Random();
        
        for (int i = 0; i < numDigits; i++)
        {
            randomDigit = random.nextInt(10);
            secretNumber = secretNumber + randomDigit.toString();
        }
        System.out.println("Secret number: " + secretNumber);
    }
    
    public int getNumDigits()
    {
        return numDigits;
    }
    
    public int getGuesses()
    {
        return numGuesses;
    }

    public long getSolveTime()
    {
        long solveTime = 0;
        if (solved)
        {
            solveTime = endTime - startTime;
        }   
        return solveTime;
    }    
    
    public boolean getSolved()
    {
        return solved;
    }
    
    public void printResults()
    {
        System.out.println("-----Puzzle Results-----");
        System.out.println("Number of digits: " + getNumDigits());
        System.out.println("Secret number: " + getSecretNumber());
        System.out.println("Puzzle status: " + getSolved());
        System.out.println("Puzzle guesses: " + getGuesses());
        System.out.println("Puzzle time (milliseconds): " + getSolveTime());
    }
    
    private String getSecretNumber()
    {
        return secretNumber;
    }
    
    private int getIndex(char value, char[] charArray)
    {
        int loc = -1;
        for (int i = 0; i < charArray.length; i++)
        {
            if (value == charArray[i])
            {
                loc = i;
                break;
            }
        }
        return loc;
    }
    
    // Compare a guess to the secret number and return the results
    // in a GuessResult object.
    public GuessResult guess(String guess)
    {
        int corPos = 0;
        int incPos = 0;
        int loc = -1;
        char guessChar;
        char secretChar;
        char[] secretChars = secretNumber.toCharArray();
        char[] guessChars = guess.toCharArray();
        
        numGuesses++;
        
        if (guess.equals(secretNumber))
        {
            solved = true;
            corPos = numDigits;
            incPos = 0;
        } else
        {
        
            for (int i = 0; i < numDigits; i++)
            {
                if (guessChars[i] == secretChars[i])
                {
                    corPos++;
                    guessChars[i] = 'X';
                    secretChars[i] = 'X';
                }
            }
            
            for (int i = 0; i < numDigits; i++)
            {
                guessChar = guessChars[i];
                loc = getIndex(guessChar, secretChars);
                if ((guessChar != 'X') && (loc >= 0))
                {
                    incPos++;
                    guessChars[i] = 'X';
                    secretChars[loc] = 'X';
                }
            }
        }
        endTime = System.currentTimeMillis();
        return(new GuessResult(solved, corPos, incPos));
    }
    
    public void guessTester(String secretNumber, String guess)
    {
        this.secretNumber = secretNumber;
        numDigits = secretNumber.length();
        System.out.println(guess(guess));
    }
}

// This is a helper class to hold the result of a guess
// It is just a data container, so the attributes are public 
//     and may be accessed directly. It can also be printed directly.
class GuessResult
{
    public Boolean solved;  // Indicates whether the guess is correct
    public Integer corPos;  // Indicates the number of digits in the guess that are
                            //     present and in the correct position
    public Integer incPos;  // Indicates the number of digits in the guess that are
                            //     present but not in the correct position. If a digit
                            //     has been recorded in corPos, it will not also appear in
                            //     incPos
    
    public GuessResult(Boolean solved, Integer corPos, Integer incPos)
    {
        this.solved = solved;
        this.corPos = corPos;
        this. incPos = incPos;
    }

    
    public String toString()
    {
        String s = "Solved: "+ solved.toString();
        s = s + " corPos: " + corPos.toString();
        s = s + " incPos: " + incPos.toString();
        return(s);
    }
}