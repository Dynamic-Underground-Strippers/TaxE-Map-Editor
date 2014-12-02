
public class Line {
    private Point start;
    private Point end;
    private int distance;

    public Line(Point start, Point end,int distance){
        this.start = start;
        this.end = end;
        this.distance = distance;
    }

    public Point getStart() {
        return this.start;
    }

    public Point getEnd(){
        return this.end;
    }

    public Point getMidPoint(){
        float midx = (this.start.getX()+this.end.getX())/2;
        float midy = (this.start.getY()+this.end.getY())/2;
        return new Point(midx,midy);
    }

    public int getDistance(){
        return this.distance;
    }
}
