
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tony
 */
public class PointSET {
    private TreeSet<Point2D> points;
    
    // construct an empty set of points
    public PointSET() {
        points = new TreeSet<Point2D>();
    }
    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }
    
    // number of points in the set
    public int size() {
        return points.size();
    }
    
    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        points.add(p);
    }
    
    // does the set contain the point p?
    public boolean contains(Point2D p) {
        if (p == null)
            return false;
        return points.contains(p);
    }
    
    // draw all of the points to standard draw
    public void draw() {     
        for (Point2D p:points) {
            p.draw();
        }
    }
    
    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> pointsInRect = new ArrayList<Point2D>();

        for (Point2D p:points) {
            if (rect.distanceTo(p) == 0.0D) {
                pointsInRect.add(p);
            }
        }

        return pointsInRect;
    }
    
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (points.isEmpty())
            return null;
        
        boolean fg_first = false;
        double DisNearest = 0;
        Point2D PtNearest = null;
        for (Point2D q:points) {
            if (!fg_first) {
                DisNearest = q.distanceSquaredTo(p);
                PtNearest = q;
                fg_first = true;
            }
            if (q.distanceSquaredTo(p) < DisNearest) {
                DisNearest = q.distanceSquaredTo(p);
                PtNearest = q;
            }
        }
        
        return PtNearest;
    }
    
    public static void main(String[] args) { 
        In in = new In(args[0]); 
        int N = in.readInt(); 
        PointSET s = new PointSET(); 
        StdOut.println(s.isEmpty()); // true 
        StdOut.println(s.size()); // 0 
        for (int i = 0; i < N; i++) { 
            double x = in.readDouble(); 
            double y = in.readDouble(); 
            s.insert(new Point2D(x, y)); // insert 
        } 
        StdOut.println(s.size()); // 20 
        StdOut.println(s.isEmpty()); // false 
        StdDraw.setPenRadius(0.01);
        s.draw(); // 批改系統 won't test this function! 
        Point2D t1 = new Point2D(0.48333394289368536, 0.005911560679846994); 
        Point2D t2 = new Point2D(0.48333394289368, 0.005911560679846); 
        StdOut.println(s.contains(null)); // false 
        StdOut.println(s.contains(t1)); // true 
        StdOut.println(s.contains(t2)); // false 
        StdOut.println(s.nearest(t2).toString()); // (0.48333394289368536, 0.005911560679846994) 
        StdOut.println(s.nearest(new Point2D(1.0, 1.0)).toString()); // (0.8693410639235616, 0.9002806396543104) 
        RectHV r = new RectHV(0.3, 0.3, 0.7, 0.7); 
        Iterator i = s.range(r).iterator(); 
        while (i.hasNext()) { 
            Point2D p = (Point2D) i.next(); 
            StdOut.println(p.toString()); 
            StdDraw.setPenColor(255, 0, 0);
            p.draw();
        } 
        //(0.30548287907410765, 0.34467014393926365) 
        //(0.467900409978538, 0.4875390545441505) 
        //(0.6009118467985421, 0.6091088164052958) 
        //(0.3275329713070515, 0.66183445023924) 
        }
}
