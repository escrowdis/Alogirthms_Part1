
import java.awt.Color;
import java.util.Arrays;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tony
 */
public class Fast {
    private static LinkedList<String> rst = new LinkedList();
    
   public static void main(String[] args) {
       int N, L;
        double radius = 0.01;
        In in = new In(args[0]);
        N = in.readInt();
        L = 32768;
                
        // read points from file
        Point[] a = new Point[N];
        int x, y;
        for (int i = 0; i < N; i++) {
            x = in.readInt();
            y = in.readInt();
            a[i] = new Point(x, y);
        }
        
        // create window
        StdDraw.setScale(0, L);

        for (int i = 0 ; i < a.length; i++) {
            Point[] a_sort = new Point[N];
            a_sort = Arrays.copyOfRange(a, 0, a.length);
            Arrays.sort(a_sort, a[i].SLOPE_ORDER);
            int m = 0, n = 1;
            while (n < a.length) {
                if (a[i].slopeTo(a_sort[m]) == a[i].slopeTo(a_sort[n])) {
                    if (n == a.length - 1 && n - m >= 2) {
                        OutputSegment(a_sort, m, n, a[i]);
                    }
                }
                else {
                    if (n - m >= 3)
                        OutputSegment(a_sort, m, n - 1, a[i]);
                    m = n;
                }                
                n++;
            }
        }
        
        // Draw points
        StdDraw.setPenColor(Color.red);
        StdDraw.setPenRadius(radius);
        for (int i = 0; i < a.length; i++)
            a[i].draw();
   }
   
   private static void OutputSegment(Point[] Pts, int low, int high, Point a) {
        Point[] collinearPts = new Point[high - low + 2];
        collinearPts[0] = a;
        for (int i = low; i <= high; i++) {
            collinearPts[i - low + 1] = Pts[i];
        }
        Arrays.sort(collinearPts);
        String result = "";
        for (int i = 0; i < collinearPts.length; i++) {
            if (i < collinearPts.length - 1) 
                result += collinearPts[i] + " -> ";
            else
                result += collinearPts[i] + "\n";
        }
        boolean output = true;
        for (int i = 0; i < rst.size(); i++)
            if (result.equals(rst.get(i)))
                output = false;
        if (output == true) {
            rst.push(result);
            StdOut.print(result);
            collinearPts[0].drawTo(collinearPts[collinearPts.length - 1]);
        }        
    }
}
