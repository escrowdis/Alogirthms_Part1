/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tony
 */
public class PercolationStats {
    private double[] cnt;
    private static double mean_tmp, stddev_tmp, high, low;
    private double sz;
    /**
    * Perform T independent computational experiments on an N-by-N grid
    * @param N grid length
    * @param T run  T times
    */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException ("N & T shall larger than zero!");
        sz = Math.pow(N, 2);
        cnt = new double[T];
        for (int i = 0; i < T; i++) {
            cnt[i] = 0;
            Percolation perc = new Percolation(N);
            while (true) {
                int row = StdRandom.uniform(1, N + 1);
                int col = StdRandom.uniform(1, N + 1);
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    if (!perc.percolates())
                        cnt[i]++;
                   else
                        break;
                }
            }
        }
        mean_tmp = mean();
        stddev_tmp = stddev();
        high = confidenceHi();
        low = confidenceLo();
    }
   
    /**
     * Sample mean of percolation threshold
     * @return 
     */
    public double mean() {
        return StdStats.mean(cnt) / sz;
    }
   
    /**
     * Sample standard deviation of percolation threshold
     * @return 
     */
    public double stddev() {
        return StdStats.stddev(cnt) / sz;
    }
   
    /**
    * Returns lower bound of the 95% confidence interval
    * @return 
    */
    public double confidenceLo() {
        return mean_tmp - (1.96 * stddev_tmp / Math.pow(cnt.length, 0.5));
    }
   
    /**
    * Returns upper bound of the 95% confidence interval
    * @return 
    */
    public double confidenceHi() {
       return mean_tmp + (1.96 * stddev_tmp / Math.pow(cnt.length, 0.5));
    }
    /**
    * Test client, described below
    * @param args 
    */
    public static void main(String[] args) {
        int N = Integer.valueOf(args[0]);
        int T = Integer.valueOf(args[1]);
        
        PercolationStats stats = new PercolationStats(N, T);
        StdOut.printf("%% java PercolationStats %d %d\n", N, T);
        StdOut.printf("mean\t\t\t= %8.6f\n", mean_tmp);
        StdOut.printf("stddev\t\t\t= %19.17f\n", stddev_tmp);
        StdOut.printf("95%% confidence interval\t= %19.16f %19.16f\n", low, high);
    }
}
