import java.io.File;
import java.util.ArrayList;

public class Node {
    private File folder;
    private ArrayList<Node> children;
    private long size;
    private int level = 0;
    private long sizeLimit;

    public Node(File folder, long sizeLimit) {
        this.folder = folder;
        this.sizeLimit = sizeLimit;
        children = new ArrayList<>();
    }

    public File getFolder() {
        return folder;
    }

    public void addChild(Node node) {
        node.setLevel(level + 1);
        children.add(node);
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getSizeLimit() {
        return sizeLimit;
    }

    @Override
    public String toString() {
        String size = SizeCalculator.getHumanReadableSize(getSize());
        StringBuilder sb = new StringBuilder();
        sb.append(folder.getName());
        sb.append(" - ");
        sb.append(size);
        sb.append("\n");
        for (Node child : children) {
            if (child.size < sizeLimit) {
                continue;
            }
            sb.append(".".repeat(child.level));
            sb.append(child);
        }
        return sb.toString();
    }
}
