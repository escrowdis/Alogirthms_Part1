
import java.awt.Color;
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tony
 */
public class Point implements Comparable<Point> {
    private final int x, y;
    
    // compare points by slope to this point    
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point p1, Point p2) {
            double s1, s2;
            s1 = slopeTo(p1);
            s2 = slopeTo(p2);
            if (s1 < s2)
                return -1;
            else if (s1 > s2)
                return 1;
            return 0;
        }    
    };
    
    /**
     * construct the point (x, y)
     * @param x
     * @param y 
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
    * draw this point
    */
    public void draw() {
        StdDraw.point(x, y);
//        StdDraw.text(x, y+10, x + ", " + y);
    }
    /**
     * draw the line segment from this point to that point
     * @param that 
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    /**
     * string representation
     * @return 
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    /**
     * is this point lexicographically smaller than that point?
     * @param that
     * @return 
     */
    public int compareTo(Point that) {
        if (this.y < that.y || (this.y == that.y && this.x < that.x)) 
            return -1;
        else if (this.x == that.x && this.y == that.y)
            return 0;
        return 1;
    }
    /**
     * the slope between this point and that point
     * @param that
     * @return 
     */
    public double slopeTo(Point that) {
//        StdOut.println(this.x + ", " + this.y + "\t" + that.x + ", " + that.y);
        double dx = that.x - this.x;
        double dy = that.y - this.y;
        if (dx == 0.0 && dy == 0.0)
            return Double.NEGATIVE_INFINITY;
        else if (dx ==0.0)
            return Double.POSITIVE_INFINITY;
        else if (dy == 0.0)
            return 0.0;
        else
            return dy/dx;
    }
    
    public static void main(String[] args) {
        int N, L;
        double radius = 0.01;
        N = Integer.valueOf(args[0]);
        L = 512;
        Point[] a = new Point[N];
        
        // create window
        StdDraw.setCanvasSize(L, L);
        StdDraw.setScale(0, L);
        StdDraw.setPenRadius(radius);
        
        // create random points
        int x, y;
        for (int i=0; i<N; i++) {
            x = StdRandom.uniform(L);
            y = StdRandom.uniform(L);
            a[i] = new Point(x, y);
        }
        for (int i = 0; i< a.length - 1; i++) {
            StdDraw.setPenRadius(radius*2);
            a[i].draw();
            StdDraw.setPenColor(Color.yellow);
            StdDraw.setPenRadius(radius);
            a[i].drawTo(a[i + 1]);
            StdDraw.setPenColor(Color.red);
//            StdOut.println(a[i].slopeTo(a[i + 1]));
            StdOut.print(a[i].x + ", " + a[i].y + "\t" + a[i+1].x + ", " + a[i+1].y + "\t");
            StdOut.println(a[i].compareTo(a[i + 1]));
        }
    }
}
