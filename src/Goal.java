public class Goal {
    private final String description;
    private final Node end;
    private final int points;
    private final Node start;


    public Goal(int points, Node start, Node end) {
        this.points = points;
        this.start = start;
        this.end = end;
        this.description = "Move a train from " + this.start.getName() + " to " + this.end.getName();
    }

    public String getDescription() {
        return description;
    }

    public int getPoints() {
        return points;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd(){ return end;}
}

