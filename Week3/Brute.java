
import java.awt.Color;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tony
 */
public class Brute {
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
        
        double s_ij, s_jk, s_kl;
        for (int i = 0; i < a.length; i++) {
            Point p_i = a[i];
            for (int j = i + 1; j < a.length; j++) {
                Point p_j = a[j];
                s_ij = a[i].slopeTo(a[j]);
                for (int k = j + 1; k < a.length; k++) {
                    Point p_k = a[k];
                    s_jk = a[j].slopeTo(a[k]);
                    // check if slope are equal or not
                    if (Double.compare(s_ij, s_jk) != 0) continue; 
                    for (int l = k + 1; l < a.length; l++) {
                        Point p_l = a[l];
                        s_kl = a[k].slopeTo(a[l]);
                        // check slope are equal or not
                        if (Double.compare(s_jk, s_kl) == 0) {
//                            StdOut.println(s_ij + ", " + s_jk + ", " + s_kl);
                            OutputSegment(p_i, p_j, p_k, p_l);
                        }
                    }
                }
            }
        }
        
        StdDraw.setPenColor(Color.red);
        StdDraw.setPenRadius(radius);
        for (int i = 0; i < a.length; i++)
            a[i].draw();
    }
    
    private static void OutputSegment(Point... collinearPts) {
        Arrays.sort(collinearPts);
        StdOut.printf("%s -> %s -> %s -> %s\n", collinearPts[0].toString(), 
                collinearPts[1].toString(), collinearPts[2].toString(), 
                collinearPts[3].toString());
        collinearPts[0].drawTo(collinearPts[3]);
    }
}
