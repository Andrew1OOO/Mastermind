import java.util.ArrayList;

public class Player
{
    String name = "12DigitSolver";
    public Player()
    {
    }

    public String getName()
    {
        return(name);
    }

    public void solve(Puzzle puzzle)
    {
        GuessResult gr;

        int corPosCount = 0; //Counter for amount of correct numbers found
        ArrayList<Integer> includedNums = new ArrayList<Integer>(); //Array of all digits in the myster number
        Integer[] corNums = new Integer[puzzle.getNumDigits()]; //Array of all the digits in the correct positions
        int[] guess = new int[puzzle.getNumDigits()]; //Array of numbers to guess
        gr = puzzle.guess("0".repeat(puzzle.getNumDigits())); //Initial guess to see number of zeroes
        int numZeros = gr.corPos; //Number of zeroes in the mystery number

        //Goes through digits 1 to 9 to see if they are in the number
        for (int i = 1; i < 10; i++)
        {
            //Breaks out of the for loop if all 12 digits are already found
            if (corPosCount == puzzle.getNumDigits())
            {
                break;
            }

            //Guess to see if a number is present in the mystery number, if
            //it is, then add it to the includedNums array
            gr = puzzle.guess(Integer.toString(i).repeat(puzzle.getNumDigits()));
            if (gr.corPos > 0)
            {
                includedNums.add(i);
                corPosCount += gr.corPos;
            }
        }

        //Goes through all of the included numbers and then cycles through
        //the possible places it could be
        for (int i = 0; i < includedNums.size(); i++)
        {
            for (int j = 0; j < puzzle.getNumDigits(); j++)
            {
                //Replaces the previous digit with a zero
                if (j != 0)
                {
                    guess[j-1] = 0;
                }

                //Checks if a number is already in that location
                if (corNums[j] == null)
                {
                    guess[j] = includedNums.get(i);
                    gr = puzzle.guess(guessToString(guess));

                    //If the correct positions for number of zeroes decreases,
                    //it puts a zero where that number was. If it increases,
                    //it puts the current number there
                    if (gr.corPos < numZeros)
                    {
                        corNums[j] = 0;
                    }
                    else if (gr.corPos > numZeros)
                    {
                        corNums[j] = includedNums.get(i);
                    }
                }
            }
            guess[puzzle.getNumDigits() - 1] = 0;
        }

        //Final guess with all of the digits in the corNums array
        gr = puzzle.guess(corNumsToString(corNums));
    }

    //Converts a guess array into a String
    public String guessToString(int[] guessArr)
    {
        String guessStr = "";
        for (int i = 0; i < guessArr.length; i++)
        {
            guessStr += guessArr[i];
        }
        return guessStr;
    }

    //Converts a correct numbers array into a String
    public String corNumsToString(Integer[] guessArr)
    {
        String guessStr = "";
        for (int i = 0; i < guessArr.length; i++)
        {
            guessStr += guessArr[i];
        }
        return guessStr;
    }
}