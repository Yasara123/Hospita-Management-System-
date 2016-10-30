/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Lab;

import java.util.LinkedList;

/**
 *
 * @author Hasangi Thathsarani
 */
public class ResultQueue<E> {
    private LinkedList<E> list = new LinkedList<E>();
   public void enqueue(E item) {
      list.addLast(item);
   }
   public E dequeue() {
      return list.poll();
   }
   public boolean hasItems() {
      return !list.isEmpty();
   }
   public E peak(){
       return list.getFirst();
   }
   public int size() {
      return list.size();
   }
    
}
