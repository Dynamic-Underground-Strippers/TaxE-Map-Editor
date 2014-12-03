public class Rect extends java.awt.Rectangle {
    private int startIndex;
    private int endIndex;
    public Rect(int x,int y,int width, int height,int startIndex,int endIndex){
        super(x,y,width,height);
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public int getStartIndex(){
        return this.startIndex;
    }

    public int getEndIndex(){
        return this.endIndex;
    }
}

