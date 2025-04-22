package droidPD;

public interface Stack<E> {
  public void push(E e);
  public E pop();
  public boolean isEmpty();
  public E peek();
}
