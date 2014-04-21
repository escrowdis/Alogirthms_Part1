

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tony
 */
public class Solver {
    private boolean solvable;
    private boolean solvableSwap;
    
    private Node lastNode = null;
    private class Node implements Comparable<Node> {
        Board board;
        Node preNode;
        int moves;
        
        public Node(Board board, Node preNode) {
            this.board = board;
            this.preNode = preNode;
            if (preNode == null)
                moves = 0;
            else 
                moves = preNode.moves + 1;
        }
        @Override
        public int compareTo(Node pre) {
            return (this.board.manhattan() + this.moves) - (pre.board.manhattan() + pre.moves);
        }
    }
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<Node> Q = new MinPQ<Node>();
        Q.insert(new Node(initial, null));
        MinPQ<Node> QSwap = new MinPQ<Node>();
        QSwap.insert(new Node(initial.twin(), null));
        
        solvable = false;
        solvableSwap = false;
        while (!solvable && !solvableSwap) {
            solvable = Steps(Q);
            solvableSwap = Steps(QSwap);
        }
    }
    
    private boolean Steps(MinPQ<Node> q) {
        if (q.isEmpty())    return false;
        Node current = q.delMin();
        if (current.board.isGoal()) {
            lastNode = current;
            return true;
        }
        
        for (Board b : current.board.neighbors()) {
            if (current.preNode == null || !b.equals(current.preNode.board)) {
                q.insert(new Node(b, current));
            }
        }
        
        return false;
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }
    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (isSolvable()) 
            return lastNode.moves;
        return -1;
    }
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (lastNode != null) {
            Stack<Board> result = new Stack<>();
            for (Node now = lastNode; now != null; now = now.preNode) {
                result.push(now.board);
            }
            return result;
        }
        return null;
    }
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
                StdOut.println(board.hamming());
            }
        }
    }
}
