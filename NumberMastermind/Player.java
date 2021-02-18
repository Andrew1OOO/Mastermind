import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import java.util.Hashtable;

import java.util.Map.Entry;

public class Player {
    // Set this string to any value you like. It is used as an identifier, listed
    // alongside results.
    String name = "BigDawg";

    public Player() {
    }

    public String getName() {
        return (name);
    }

    // This is the method you complete to implement your solving algorithm.
    // Included is a naive solver that simply tries every number, beginning with 0,
    // until it reaches the secret number. So for a secret number of n digits, this
    // will take an average of (10^n)/2 guesses! As a benchmark. my home PC takes
    // about 6.7*10^-4 milliseconds per guess.
    //
    // Note that you have to submit your guesses to the puzzle as Strings, but there
    // is no limatation
    // on how you represent your them internally. You may use a String, a long,
    // a BigInteger, an array of char, or any other format you like. Simply convert
    // it to
    // a String before calling the guess method.
    //
    // Candidate solutions must be contain exactly the correct number of digits,
    // including
    // being left padded with zeroes, when appropriate. For example, if you are
    // guessing a 6-digit
    // value, then "000021" is a valid guess, while "21" is not.
    public void solve(Puzzle puzzle) {
        // Formatter used to left pad zeroes

        GuessResult gr;
        ArrayList<String> allValues = generateArr(puzzle);

        ArrayList<String> possibleVals = allValues;
        String guess = "111222";

        do {

            gr = puzzle.guess(guess);
            possibleVals = removeMoves(possibleVals, gr.incPos, gr.corPos, guess);
            guess = move(possibleVals);

            //if (possibleVals.size() < 15) {
          //      System.out.println(possibleVals);
           // }

        } while (!gr.solved);

    }

    public static ArrayList<String> generateArr(Puzzle puzzle) {
        ArrayList<String> x = new ArrayList<String>();

        String formatter = "%0" + puzzle.getNumDigits() + "d";
        for (int i = 0; i < 1000000; i++) {
            x.add(String.format(formatter, i));
        }

        return x;
    }

    public static String move(ArrayList<String> x) {
        String guess = x.get(0);

        return guess;

    }

    public static ArrayList<String> removeMoves(ArrayList<String> x, int incPos, int corPos, String guess) {
        String token = "";
        String correct = "+" + corPos + "-" + incPos;

        ArrayList<String> vals = new ArrayList<String>();
        for (int i = 0; i < x.size(); i++) {
            token = x.get(i);
            if (compare(token, guess).equalsIgnoreCase(correct)) {
                vals.add(token);
            }
        }

        return vals;
    }

    private static String compare(String token, String guess) {
        int corPos = 0;
        int incPos = 0;
        int loc = -1;
        char guessChar;
        char[] secretChars = token.toCharArray();
        char[] guessChars = guess.toCharArray();

        for (int i = 0; i < 6; i++) {
            if (guessChars[i] == secretChars[i]) {
                corPos++;
                guessChars[i] = 'X';
                secretChars[i] = 'X';
            }
        }

        for (int i = 0; i < 6; i++) {
            guessChar = guessChars[i];
            loc = getIndex(guessChar, secretChars);
            if ((guessChar != 'X') && (loc >= 0)) {
                incPos++;
                guessChars[i] = 'X';
                secretChars[loc] = 'X';
            }
        }

        return "+" + corPos + "-" + incPos;
    }

    private static int getIndex(char value, char[] charArray) {
        int loc = -1;
        for (int i = 0; i < charArray.length; i++) {
            if (value == charArray[i]) {
                loc = i;
                break;
            }
        }
        return loc;
    }
        

}