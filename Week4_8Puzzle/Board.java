import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tony
 */
public class Board {
    private final int [][] blocks;
    private static int N;
    private static int hamming = -1;
    private static int manhattan = 0;
    
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = blocks.length;
        this.blocks = copyBlocks(blocks, N);
    }
    // board dimension N
    public int dimension() {
        return N;
    }
    // number of blocks out of place
    public int hamming() {
        hamming = -1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != i * N + j + 1)
                    hamming++;
            }
        }        
        return hamming;
    }
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        manhattan = 0;
        int quotient, remainder;
        for ( int i = 0; i < N; i++) {
            for ( int j = 0; j < N; j++) {
                if (blocks[i][j] == 0)  continue;
                quotient = (blocks[i][j] -1) / N;
                remainder = (blocks[i][j] -1) % N;
                manhattan += (Math.abs(i - quotient) + Math.abs(j - remainder));
            }
        }
        
        return manhattan;
    }
    // is this board the goal board?
    public boolean isGoal() {
        if (blocks[N - 1][N - 1] != 0)
            return false;
        int r, c;
        for (int i = 0; i < N * N - 1; i++) {
            r = i / N;
            c = i % N;
            if (blocks[r][c] != (i + 1))
                return false;
        }
        
        return true;
    }
    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int[][] newBlocks = copyBlocks(blocks, N);
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - 1; j++) {
                if (newBlocks[i][j] != 0 && newBlocks[i][j + 1] != 0) {
                    int swapVal = newBlocks[i][j];
                    newBlocks[i][j] = newBlocks[i][j + 1];
                    newBlocks[i][j + 1] = swapVal;
                    
                    return new Board(newBlocks);
                }
            }
        }
        
        throw new IllegalStateException("No twins for the block");
    }
    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y)
            return true;
        if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;
        Board that = (Board) y;
        if (that.dimension() != this.dimension())
            return false;
        if (!Arrays.deepEquals(that.blocks, blocks))
            return false;
        
        return true;
    }
    // all neighboring boards
    public Iterable<Board> neighbors() {
        // find where is zero
        int ZeroC = -1, ZeroR = -1;
        loops: {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (blocks[i][j] == 0) {
                        ZeroR = i;
                        ZeroC = j;
                        break loops;
                    }
                }
            }
        }
        
        List<Board> neighbor = new LinkedList<>();
        
        int swapVal;
        // Up
        if (ZeroR < N - 1) {
            int[][] mblocks = copyBlocks(blocks, N);
            swapVal = mblocks[ZeroR][ZeroC];
            mblocks[ZeroR][ZeroC] = mblocks[ZeroR + 1][ZeroC];
            mblocks[ZeroR + 1][ZeroC] = swapVal;
            Board output = new Board(mblocks);
            neighbor.add(output);
        }
        // Down
        if (ZeroR > 0) {
            int[][] mblocks = copyBlocks(blocks, N);
            swapVal = mblocks[ZeroR][ZeroC];
            mblocks[ZeroR][ZeroC] = mblocks[ZeroR - 1][ZeroC];
            mblocks[ZeroR - 1][ZeroC] = swapVal;
            Board output = new Board(mblocks);
            neighbor.add(output);
        }
        // Left
        if (ZeroC < N - 1) {
            int[][] mblocks = copyBlocks(blocks, N);
            swapVal = mblocks[ZeroR][ZeroC];
            mblocks[ZeroR][ZeroC] = mblocks[ZeroR][ZeroC + 1];
            mblocks[ZeroR][ZeroC + 1] = swapVal;
            Board output = new Board(mblocks);
            neighbor.add(output);
        }
        // Right
        if (ZeroC > 0) {
            int[][] mblocks = copyBlocks(blocks, N);
            swapVal = mblocks[ZeroR][ZeroC];
            mblocks[ZeroR][ZeroC] = mblocks[ZeroR][ZeroC - 1];
            mblocks[ZeroR][ZeroC - 1] = swapVal;
            Board output = new Board(mblocks);
            neighbor.add(output);
        }
        
        return neighbor;
    }
    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    private int[][] copyBlocks(int [][] src, int dimen) {
        int[][] dst = new int[dimen][dimen];
        for ( int i = 0 ; i < N; i++) {
            for ( int j = 0 ; j < N; j++) {
                int Val = src[i][j];
                dst[i][j] = Val;
            }
        }
        
        return dst;
    }
    
    public static void main(String[] args) { 
// Example 0 (read from argument        
//        In in = new In(args[0]);
//        int N = in.readInt();
//        int[][] blocks = new int[N][N];
//        for (int i = 0; i < N; i++)
//            for (int j = 0; j < N; j++)
//                blocks[i][j] = in.readInt();
//        Board b1 = new Board(blocks);
//        StdOut.println(b1.toString());
//        StdOut.println(b1.twin().toString());
//        StdOut.println(b1.hamming());
        
// Example 1
        int[][] t1 = new int[][]{{1,2,0}, {4,5,3}, {7,8,6}}; 
        Board b1 = new Board(t1); 
        StdOut.println(b1.hamming()); // 2 
        StdOut.println(b1.manhattan()); // 2 
        StdOut.println(b1.isGoal()); // false; 
        StdOut.println(b1.dimension()); // 3 
        StdOut.println(b1.toString()); // print t1 
        StdOut.println(b1.twin().toString()); // print twin of t1 (每個人寫的可能不一樣) 
        Iterator<Board> i = b1.neighbors().iterator(); 
        while (i.hasNext()) { 
        StdOut.println(i.next().toString()); 
        } 
        
// Example 2        
//        int[][] input = new int[][]{{1, 2, 3, 4}, {5, 6, 0, 8}, {9, 10, 11, 12}, {13, 14, 15, 7}};
//    	Board testBoard = new Board(input);
//    	
//    	StdOut.println(testBoard.toString());
//    	
//    	Iterable<Board> result = testBoard.neighbors();
//    	
//    	for (Board b : result) {
//    		StdOut.println(b.toString());
//    	}
    } 
}
