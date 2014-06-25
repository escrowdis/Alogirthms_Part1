
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tony
 */
public class Subset {

    public static void main(String[] args) {
        int K = Integer.valueOf(args[0]);
//        Deque<String> dq = new Deque<>();
        RandomizedQueue rq = new RandomizedQueue();
        while (StdIn.hasNextLine() && !StdIn.isEmpty()) {
//            dq.addLast(StdIn.readString());
            rq.enqueue(StdIn.readString());
        }
        
        for (int i=0; i<K; i++) {
//            StdOut.print(dq.iterator().next() + " ");
            StdOut.println(rq.iterator().next());
        }
    }
}
