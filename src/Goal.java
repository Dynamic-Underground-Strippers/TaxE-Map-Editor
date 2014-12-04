public class Goal {
    private final String description;
    private final Node end;
    private final String name;
    private final int points;
    private final Node start;


    public Goal(String name, String description, int points, Node start, Node end) {
        this.name = name;
        this.description = description;
        this.points = points;
        this.start = start;
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd(){ return end;}
}
