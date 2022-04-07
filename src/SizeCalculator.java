import java.util.HashMap;

public class SizeCalculator {
    private static final char[] SIZE_MULTIPLIERS = {'B', 'K', 'M', 'G', 'T'};
    private static final HashMap<Character, Long> CHAR_2_MULTIPLIERS = getMultipliers();

    public static String getHumanReadableSize(long size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE_MULTIPLIERS.length; i++) {
            double value = ((double) size) / Math.pow(1024, i);
            if (value < 1024) {
                return sb
                        .append(Math.round(value * 100) / 100.0)
                        .append(" ")
                        .append(SIZE_MULTIPLIERS[i])
                        .append(i > 0 ? "b" : "").toString();
            }
        }
        return "";
    }
    public static long getSizeFromHumanReadable(String size) {
        char sizeMultiplier = size.replaceAll("[.,0-9\\s+]+","").charAt(0);
        double sizeDouble = Double.parseDouble(size.replaceAll("[^0-9.,]+", ""));
        return Math.round(sizeDouble * CHAR_2_MULTIPLIERS.get(sizeMultiplier));
    }

    private static HashMap<Character, Long> getMultipliers() {
        HashMap<Character, Long> char2Multipliers = new HashMap<>();
        for (int i = 0; i < SIZE_MULTIPLIERS.length; i++) {
            char2Multipliers.put(SIZE_MULTIPLIERS[i], (long) Math.pow(1024.0, i));
        }
        return char2Multipliers;
    }
}
