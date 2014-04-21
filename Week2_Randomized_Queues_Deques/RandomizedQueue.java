import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tony
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private List<Item> list;
    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        list = new LinkedList<Item>();
    }
    /**
     * is the queue empty?
     * @return 
     */    
    public boolean isEmpty() {
        return list.isEmpty();
    }
    /**
     * return the number of items on the queue
     * @return 
     */
    public int size() {
        return list.size();
    }
    /**
     * add the item
     * @param item 
     */
    public void enqueue(Item item) {
        if (item == null)
           throw new NullPointerException();
        list.add(item);
    }
    /**
     * delete and return a random item
     * @return 
     */
    public Item dequeue() {
        if (isEmpty())
           throw new NoSuchElementException();
        return list.remove(StdRandom.uniform(size()));
    }
    /**
     * return (but do not delete) a random item
     * @return 
     */
    public Item sample() {
        if (isEmpty())
           throw new NoSuchElementException();
        return (Item) list.get(StdRandom.uniform(size()));
    }
    /**
     * return an independent iterator over items in random order
     * @return 
     */
    public Iterator<Item> iterator() {
        return new RQIterator<>();
    }
    
    private class RQIterator<Item> implements Iterator<Item> {
         @Override
        public boolean hasNext() {
            return size() > 0;
        }
        
        @Override
        public void remove() { 
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return (Item) dequeue();
        }
    }
    
    public static void main(String[] args) {
        RandomizedQueue rq = new RandomizedQueue();
        RandomizedQueue rq1 = new RandomizedQueue();
        for (int i=0; i<10; i++) {
            rq.enqueue(i);
            rq1.enqueue(i);
        }
        Iterator itr = rq.iterator();
        Iterator itr1 = rq1.iterator();
        while (itr.hasNext()) {
            StdOut.print(itr.next());
        }
        StdOut.print("\n");
        while (itr1.hasNext()) {
            StdOut.print(itr1.next());
        }
    }
}
