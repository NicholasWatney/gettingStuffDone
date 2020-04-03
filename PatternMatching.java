import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Your implementations of various pattern matching algorithms.
 *
 * @author samhb_slides
 * @version Broken
 * @userid -,-
 * @GTID 123 456 789
 *
 * Collaborators: Srsly, I legit just copied straight from the slides. Soz. I can't.
 *
 * Resources: I barely could bring myself to go through course materials.
 */
public class PatternMatching {

    /**
     * Brute force pattern matching algorithm to find all matches.
     * <p>
     * You should check each substring of the text from left to right,
     * stopping early if you find a mismatch and shifting down by 1.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> bruteForce(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {

        if (pattern == null || pattern.length() == 0) {

            throw new IllegalArgumentException("Pattern is null, or of length 0.");
        }

        if (text == null || comparator == null) {

            throw new IllegalArgumentException("Text or comparator is null.");
        }

        ArrayList<Integer> list = new ArrayList<Integer>();

        int i = 0;
        while (i <= text.length() - pattern.length()) {

            int j = 0;

            while (j < pattern.length() && comparator.compare(pattern.charAt(j), text.charAt(j + i)) == 0) {
                j++;
            }

            if (j == pattern.length()) {
                list.add(i);
            }

            i++;

        }

        return list;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     * <p>
     * The table built should be the length of the input text.
     * <p>
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     * <p>
     * Ex. ababac
     * <p>
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     * <p>
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {

        if (pattern == null || comparator == null) {

            throw new IllegalArgumentException("The pattern or comparator is null.");
        }

        if (pattern.length() == 0) {
            return new int[0];
        }

        int[] list = new int[pattern.length()];

        int i = 0;
        int j = 1;

        list[0] = 0;

        while (j < pattern.length()) {

            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                i++;
                list[j] = i;
                j++;

            } else {

                if (i == 0) {
                    list[j] = 0;
                    j++;

                } else {

                    i = list[i - 1];
                }

            }
        }

        return list;
    }


    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     * <p>
     * Make sure to implement the failure table before implementing this
     * method. The amount to shift by upon a mismatch will depend on this table.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {

        if (pattern == null || pattern.length() == 0) {

            throw new IllegalArgumentException("Pattern is null, or of length 0.");
        }

        if (text == null || comparator == null) {

            throw new IllegalArgumentException("Text or comparator is null.");
        }

        if (pattern.length() > text.length()) {

            return new ArrayList<Integer>();
        }

        int[] table = buildFailureTable(pattern, comparator);
        ArrayList<Integer> list = new ArrayList<Integer>();

        int i = 0;
        int j = 0;

        while (i <= text.length() - pattern.length()) {
            while (j < pattern.length() && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {

                j++;
            }

            if (j == 0) {
                i++;

            } else {

                if (j == pattern.length()) {

                    list.add(i);
                }

                int next = table[j - 1];
                i += j - next;
                j = next;

            }
        }

        return list;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     * <p>
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     * <p>
     * Ex. octocat
     * <p>
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     * <p>
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {

        if (pattern == null) {
            throw new IllegalArgumentException("Pattern is null.");
        }

        HashMap<Character, Integer> map = new HashMap<Character, Integer>(pattern.length());

        for (int i = 0; i < pattern.length(); i++) {

            map.put(pattern.charAt(i), i);
        }

        return map;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     * <p>
     * <p>
     * <p>
     * Note: You may find the getOrDefault() method useful from Java's Map.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {

        if (pattern == null || pattern.length() == 0) {

            throw new IllegalArgumentException("Pattern is null, or of length 0.");
        }

        if (text == null || comparator == null) {

            throw new IllegalArgumentException("Text or comparator is null.");
        }

        Map<Character, Integer> map = buildLastTable(pattern);
        ArrayList<Integer> list = new ArrayList<Integer>();

        int i = 0;

        while (i <= text.length() - pattern.length()) {
            int j = pattern.length() - 1;

            while (j >= 0 && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j--;
            }

            if (j == -1) {
                list.add(i);
                i += 1;

            } else {

                int shiftedIndex = map.getOrDefault(text.charAt(i + j), -1);

                if (shiftedIndex < j) {
                    i += j - shiftedIndex;

                } else {

                    i += 1;
                }
            }
        }

        return list;
    }
}


