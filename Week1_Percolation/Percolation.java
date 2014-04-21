/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author Tony
 */
public class Percolation {
    private int [][]grid;
    private int sz, L;
    private static int pt, pt_cvt;
//    QuickFindUF uf;
    private WeightedQuickUnionUF uf, uf1;
    
    /**
     * Create N-by-N grid, with all sites blocked
     * @param N 
     */
    public Percolation(int N) {
        grid = new int[N][N];
        L = N;
        sz = N * N;
        uf = new WeightedQuickUnionUF(sz);
        uf1 = new WeightedQuickUnionUF(sz);
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == 0) {
                    if (uf.connected(i, j))
                        continue;
                    uf.union(i, j);
                    uf1.union(i, j);
                }
                else if(i == N - 1) {
                    if (uf.connected(sz - 1, sz - 1 - j))
                        continue;
                    uf.union(sz - 1, sz - 1 - j);
                }
                grid[i][j] = 0;
            }
        }
    }
    
    /**
     * Open site (row i, column j) if it is not already
     * @param i
     * @param j 
     */
    public void open(int i, int j) {
        grid[i - 1][j - 1] = 1;
        
        pt = xyTo1D(i, j);
        int row[] = new int[4];
        int col[] = new int[4];
        row[0] = i - 1;     col[0] = j;
        row[1] = i + 1;     col[1] = j;
        row[2] = i;         col[2] = j - 1;
        row[3] = i;         col[3] = j + 1;
        
        for (int k = 0; k < 4; k++) {
            if (row[k] - 1 >= 0 && row[k] - 1 < L && col[k] - 1 >= 0 && col[k] - 1 < L) {
                if (grid[row[k]-1][col[k]-1] == 1) {
                    pt_cvt = xyTo1D(row[k], col[k]);
                    if (uf1.connected(pt, pt_cvt))
                        continue;
                    uf.union(pt, pt_cvt);
                    uf1.union(pt, pt_cvt);
                }
            }
        }
    }
    
    /**
     * Is site (row i, column j) open?
     * @param i
     * @param j
     * @return 
     */
    public boolean isOpen(int i, int j) {
        if (i <= 0 || i > L)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > L)
            throw new IndexOutOfBoundsException("col index i out of bounds");
        return grid[i - 1][j - 1] == 1;
    }
    
    /**
     * Is site (row i, column j) full?
     * @param i
     * @param j
     * @return 
     */
    public boolean isFull(int i, int j) {
        if (i <= 0 || i > L)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > L)
            throw new IndexOutOfBoundsException("col index i out of bounds");
        pt = xyTo1D(i, j);

        return uf1.connected(0, pt) && grid[i - 1][j - 1] == 1;
    }
    
    /**
     * Does the system percolate?
     * @return 
     */
    public boolean percolates() {
        if (L == 1 && grid[0][0] == 1)
            return true;
        else if (L != 1) {
            if (uf.connected(0, sz - 1))
                return true;
        }
        
        return false;
    }
    
    /**
     * Transform point from 2D to 1D
     * @param i
     * @param j
     * @return 
     */
    private int xyTo1D(int row, int col) {
        return ((col - 1) + (row - 1) * L);
    }
}
