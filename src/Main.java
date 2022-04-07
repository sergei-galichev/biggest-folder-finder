import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {
        ParametersBag parametersBag = new ParametersBag(args);
        long sizeLimit = parametersBag.getLimit();
        String folderPath = parametersBag.getPath();
        File file = new File(folderPath);
        Node root = new Node(file, sizeLimit);

        //metering ForkJoinPool
        long start = System.currentTimeMillis();
        FolderSizeCalculator calculator = new FolderSizeCalculator(root);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(calculator);
        long sum = root.getSize();
        long duration = System.currentTimeMillis() - start;
        System.out.println(duration + " ms");
        System.out.println("Files and sub-folders size: " + sum + " byte");
        System.out.println(root);

        //metering getFolderSize()
        start = System.currentTimeMillis();
        sum = getFolderSize(file);
        duration = System.currentTimeMillis() - start;
        System.out.println(duration + " ms");
        System.out.println("Files and sub-folders size: " + sum + " byte");

        //
        System.out.println("Files and sub-folders size (from string): "
                            + SizeCalculator.getSizeFromHumanReadable("49.82Gb")
                            + " byte");
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

}
