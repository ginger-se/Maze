package droidPD;

public class LinkedStack<E> implements Stack<E>{
  DoublyLinkedList<E> list;
  
  public LinkedStack(){
    list = new DoublyLinkedList<E>();
  }
  public void push(E e) {
    list.addFirst(e);
  }
  public E pop() {
    return list.removeFirst();
  }
  public boolean isEmpty() {
    if(list.first() != null) {
      return false;
    }
    else
      return true;
  }
  public E peek() {
    return list.first();
  }
}
