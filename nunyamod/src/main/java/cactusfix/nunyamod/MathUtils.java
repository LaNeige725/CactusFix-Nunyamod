package cactusfix.nunyamod;

public class MathUtils {
    public static long parseAbbreviatedNumber(String input) {
        input = input.toLowerCase().trim();
        long multiplier = 1;

        if (input.endsWith("k")) {
            multiplier = 1_000L;
            input = input.substring(0, input.length() - 1);
        } else if (input.endsWith("m")) {
            multiplier = 1_000_000L;
            input = input.substring(0, input.length() - 1);
        } else if (input.endsWith("b")) {
            multiplier = 1_000_000_000L;
            input = input.substring(0, input.length() - 1);
        }

        try {
            // Using Double here allows for inputs like "10.5m"
            return (long) (Double.parseDouble(input) * multiplier);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}