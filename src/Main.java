import java.io.File;

public class Main {
    public static void main(String[] args) {
        String folderPath = "E:/Downloads";
        File file = new File(folderPath);
        System.out.println(getFolderSize(file));
    }

    public static long getFolderSize(File folder) {
        if (folder.isFile()) {
            return folder.length();
        }
        long sumSize = 0;
        File[] files = folder.listFiles();
        for (File file : files) {
            sumSize += getFolderSize(file);
        }
        return sumSize;
    }
}
