
import java.util.Set;
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
public class KdTree {
    private int size;
    private Node root;
    
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        
        public Node(Point2D p, RectHV rect, Node lb, Node rt) {
            this.p = p;
            this.rect = rect;
            this.lb = lb;
            this.rt = rt;
        }
    }
    
    public KdTree() {
        size = 0;
        root = null;
    }
    
    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }
    
    // number of points in the set
    public int size() {
        return size;
    }
    
    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (isEmpty()) {
            root = new Node(p, new RectHV(0, 0, 1, 1), null, null);
        }
        else {
            Node node = root;
            Node parentNode = root;
            boolean level = true;
            while (node != null) {
                if (node.p.equals(p))
                    return;
                parentNode = node;
                if (level == true) {
                    node = (p.x() < node.p.x()) ? node.lb : node.rt;
                }
                else {
                    node = (p.y() < node.p.y()) ? node.lb : node.rt;
                }
                level = !level;
            }
            level = !level;
            
            if (level == true) {
                if (p.x() < parentNode.p.x()) {
                    parentNode.lb = new Node(p, new RectHV(parentNode.rect.xmin(), parentNode.rect.ymin(), parentNode.p.x(), parentNode.rect.ymax()), null, null);
                }
                else if (p.x() > parentNode.p.x()) {
                    parentNode.rt = new Node(p, new RectHV(parentNode.p.x(), parentNode.rect.ymin(), parentNode.rect.xmax(), parentNode.rect.ymax()), null, null);
                }
            }
            else {
                if (p.y() < parentNode.p.y()) {
                    parentNode.lb = new Node(p, new RectHV(parentNode.rect.xmin(), parentNode.rect.ymin(), parentNode.rect.xmax(), parentNode.p.y()), null, null);
                }
                else if (p.y() > parentNode.p.y()) {    
                    parentNode.rt = new Node(p, new RectHV(parentNode.rect.xmin(), parentNode.p.y(), parentNode.rect.xmax(), parentNode.rect.ymax()), null, null);
                }
            }
        }
        
        size++;
    }
    
    private boolean containsLoop(Node node, Point2D p) {
        if (node == null)
            return false;
        else if (node.p.equals(p))
            return true;
        if (p.x() < node.p.x() || p.y() < node.p.y()) {
            if (containsLoop(node.lb, p))
                return true;
        }
        else if(p.x() > node.p.x() || p.y() > node.p.y()) {
            if (containsLoop(node.rt, p))
                return true;
        }
        return false;
    }    
    
    // does the set contain the point p?
    public boolean contains(Point2D p) {
        Node node = root;
        return containsLoop(node, p);
    }
    
    private void drawAlls(Node node, boolean dir, double minX, double minY, double maxX, double maxY) {
        if (node == null)
            return;
        
        StdDraw.setPenRadius(0.001);
        if (dir == true) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), minY, node.p.x(), maxY);
            drawAlls(node.lb, !dir, minX, minY, node.p.x(), maxY);
            drawAlls(node.rt, !dir, node.p.x(), minY, maxX, maxY);
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(minX, node.p.y(), maxX, node.p.y());
            drawAlls(node.lb, !dir, minX, minY, maxX, node.p.y());
            drawAlls(node.rt, !dir, minX, node.p.y(), maxX, maxY);
        }
        
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();
    }
    
    private void drawBoundary() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        StdDraw.line(0, 0, 0, 1);
        StdDraw.line(0, 0, 1, 0);
        StdDraw.line(1, 0, 1, 1);
        StdDraw.line(0, 1, 1, 1);
    }
    
    // draw all of the points to standard draw
    public void draw() {
        if (isEmpty())
            return;
        boolean dir = true;     // true: vertical, false: horizontal
        drawBoundary();
        drawAlls(root, dir, 0, 0, 1, 1);
    }
    
    private void rangeLoop(Node node, RectHV rect, Set set) {
        if (node == null)
            return;
        if (node.rect.intersects(rect)) {
            if (rect.contains(node.p)) {
                set.add(node.p);
            }
            rangeLoop(node.lb, rect, set);
            rangeLoop(node.rt, rect, set);
        }        
    }
    
    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Set<Point2D> set = new TreeSet<Point2D>();
        rangeLoop(root, rect, set);
        return set;
    }
    
    private void nearestLoop(Node node, Point2D p) {
        if (node == null)
            return;
        double distPP = node.p.distanceSquaredTo(p);
        if (distPP < minDist) {
            minDist = distPP;
            nearestPt = node.p;
        }
        nearestLoop(node.lb, p);
        nearestLoop(node.rt, p);
    }
    
    private Point2D nearestPt;
    private double minDist;
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty())
            return null;
        Node node = root;
        nearestPt = node.p;
        minDist = root.p.distanceSquaredTo(p);
        nearestLoop(node, p);
        return nearestPt;
    }
    
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        StdDraw.show(0);

        // initialize the two data structures with point from standard input
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        StdOut.println("cal");
        Point2D pp = new Point2D(0.907292, 0.337988);
        StdOut.print(kdtree.contains(pp));
        
//        StdOut.println("hi");
    }
}
