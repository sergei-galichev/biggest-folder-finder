import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final char[] SIZE_MULTIPLIERS = {'B', 'K', 'M', 'G', 'T'};

    public static void main(String[] args) {
        String folderPath = "E:/Downloads";
        File file = new File(folderPath);

        //metering ForkJoinPool
        long start = System.currentTimeMillis();
        FolderSizeCalculator calculator = new FolderSizeCalculator(file);
        ForkJoinPool pool = new ForkJoinPool();
        long sum = pool.invoke(calculator);
        long duration = System.currentTimeMillis() - start;
        System.out.println(duration + " ms");
        System.out.println("Files and sub-folders size: " + sum + " byte");
        String humanReadableSize = getHumanReadableSize(sum);
        System.out.println("Files and sub-folders size (human-readable): " + humanReadableSize);

        //metering getFolderSize()
        start = System.currentTimeMillis();
        sum = getFolderSize(file);
        duration = System.currentTimeMillis() - start;
        System.out.println(duration + " ms");
        System.out.println("Files and sub-folders size: " + sum + " byte");

        //
        System.out.println("Files and sub-folders size (from string): " + getSizeFromHumanReadable("49.82Gb") + " byte");
    }

    public static long getFolderSize(File folder) {
        if (folder.isFile()) {
            return folder.length();
        }
        long sumSize = 0;
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                sumSize += getFolderSize(file);
            }
            return sumSize;
        }
        return -1;
    }

    public static String getHumanReadableSize(long size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE_MULTIPLIERS.length; i++) {
            double value = size / Math.pow(1024, i);
            if (value < 1024) {
                return sb
                        .append(String.format("%.2f", value))
                        .append(" ")
                        .append(SIZE_MULTIPLIERS[i])
                        .append(i > 0 ? "b" : "").toString();
            }
        }
        return "";
    }

    public static long getSizeFromHumanReadable(String size) {
        HashMap<Character, Long> char2Multipliers = getMultipliers();
        char sizeMultiplier = size.replaceAll("[.,0-9\\s+]+","").charAt(0);
        double sizeDouble = Double.parseDouble(size.replaceAll("[^0-9.,]+", ""));
        return Math.round(sizeDouble * char2Multipliers.get(sizeMultiplier));
    }

    private static HashMap<Character, Long> getMultipliers() {
        HashMap<Character, Long> char2Multipliers = new HashMap<>();
        for (int i = 0; i < SIZE_MULTIPLIERS.length; i++) {
            char2Multipliers.put(SIZE_MULTIPLIERS[i], (long) Math.pow(1024.0, i));
        }
        return char2Multipliers;
    }
}
