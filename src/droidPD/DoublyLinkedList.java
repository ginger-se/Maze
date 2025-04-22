package droidPD;
import java.util.ArrayList;

public class DoublyLinkedList<E> {
  
  public static class Node<E>{
    private E element;
    private Node<E> next;
    private Node<E> previous;
    public Node(E e, Node<E> p, Node<E> n){
      element = e;
      next = n;
      previous = p;
      
    }
    public E getElement() {
      return element;
    }
    public void setElement(E element) {
      this.element = element;
    }
    public Node<E> getNext() {
      return next;
    }
    public void setNext(Node<E> next) {
      this.next = next;
    }
    public Node<E> getPrevious() {
      return previous;
    }
    public void setPrevious(Node<E> previous) {
      this.previous = previous;
    }
  }
  
  private Node<E> head;
  private Node<E> tail;
  private int size = 0;
  public DoublyLinkedList() {
    head = new Node<>(null,null,null);
    tail = new Node<>(null, head, null);
    head.setNext(tail);
    
  }
  public int size() {
    return size;
  }
  public boolean isEmpty() {
    return size == 0;
  }
  public E first() {
    if(isEmpty()) {
      return null;
    }
    return head.getNext().getElement();
  }
  public E last() {
    if(isEmpty()) {
      return null;
    }
    return tail.getPrevious().getElement();
  }
  public void addBetween(E e, Node<E> before, Node<E> after) {
    Node<E> newest = new Node<>(e, before, after);
    before.setNext(newest);
    after.setPrevious(newest);
    size++;
  }
  public E remove(Node<E> node) {
    Node<E> before = node.getPrevious();
    Node<E> after = node.getNext();
    before.setNext(after);
    after.setPrevious(before);
    size--;
    return node.getElement();
  }
  public void addFirst(E e) {
    addBetween(e, head, head.getNext());
  }
  public void addLast(E e) {
    addBetween(e, tail.getPrevious(), tail);
  }
  public E removeFirst() {
    if(isEmpty())
      return null;
    return remove(head.getNext());
  }
  public E removeLast() {
    if(isEmpty())
      return null;
    return remove(tail.getPrevious());
  }
  public ArrayList<Node<E>> toArrayFromFirst() {
    Node<E> current = head.getNext();
    ArrayList<Node<E>> list = new ArrayList<Node<E>>();
    for(int i = 0; i < size; i++) {
      list.addLast(current);
      current = current.getNext();
    }
    return list;
  }
  public ArrayList<Node<E>> toArrayFromLast() {
    Node<E> current = head.getNext();
    ArrayList<Node<E>> list = new ArrayList<Node<E>>();
    for(int i = 0; i < size; i++) {
      list.addFirst(current);
      current = current.getNext();
    }
    return list;
  }
  public DoublyLinkedList<E> copy(){
    DoublyLinkedList<E> copy = new DoublyLinkedList<E>();
    Node<E> current = head.getNext();
    while(current.getNext() != null) {
      copy.addLast(current.getElement());
      current = current.getNext();
    }
    return copy;
  }
}
