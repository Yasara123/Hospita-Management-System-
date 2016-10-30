/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.util.LinkedList;

/**
 *
 * @author Kasun
 */
public class MyQueue<E> {

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
   public E peek(){
       return list.getFirst();
   }
   public int size() {
      return list.size();
   }
   public E reverseTravers(){
       E e=list.pollLast();
       list.addFirst(e);
       return e;
   }
   public E forwardTravers(){
       E e=list.pollFirst();
       list.addLast(e);
       return e;
   }
   public void clearQueue(){
       list.clear();
   }

}
