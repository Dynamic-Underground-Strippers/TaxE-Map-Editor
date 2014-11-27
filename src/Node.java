

public abstract class Node {
    private int id;
    private final Point location;
    private final String name;

    public Node(int id, String name, Point location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Point getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String toString(){
        String retStr = Integer.toString(id) + "," + location.toString() + "," + name;
        return retStr;
    }
}

