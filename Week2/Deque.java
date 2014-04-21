
import java.util.NoSuchElementException;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tony
 */
public class Deque<Item> implements Iterable<Item> {
    private List<Item> list;
    /**
     * construct an empty deque
     */
   public Deque() { 
       list = new LinkedList<Item>();
   }
   
   /**
    * is the deque empty?
    * @return 
    */
   public boolean isEmpty() {
       return list.isEmpty();
   }
   /**
    * return the number of items on the deque
    * @return 
    */
   public int size() {
       return list.size();
   }
   /**
    * insert the item at the front
    * @param item 
    */
   public void addFirst(Item item) {
       if (item == null)
           throw new NullPointerException();
       list.add(0, item);
   }
   /**
    * insert the item at the end
    * @param item 
    */
   public void addLast(Item item) {
       if (item == null)
           throw new NullPointerException();
       list.add(item);
   }
   /**
    * delete and return the item at the front
    * @return 
    */
   public Item removeFirst() {
       if (isEmpty())
           throw new NoSuchElementException();
       
       return list.remove(0);
   }
   /**
    * delete and return the item at the end
    * @return 
    */
   public Item removeLast() {
       if (isEmpty())
           throw new NoSuchElementException();
       
       return list.remove(size() - 1);
   }
   /**
    * return an iterator over items in order from front to end
    * @return 
    */
   public Iterator<Item> iterator() {
       return new DequeIterator<>();
   }
   
   private class DequeIterator<Item> implements Iterator<Item> {
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
            return (Item) removeFirst();
        }
   }
   
   public static void main(String[] args) {
       Deque dq = new Deque();
       for (int i = 0; i < 10; i++)
           dq.addFirst(i);
       
       Iterator itr = dq.iterator();
       while (itr.hasNext()) {
           StdOut.print(itr.next());
       }
   }
}
