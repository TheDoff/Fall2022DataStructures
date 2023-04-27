package edu.ust.cisc;

import java.util.Iterator;

public class CiscSortedLinkedList<E extends Comparable<E>> implements CiscList<E> {

    private Node<E> head;
    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new CiscLinkedListIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        if(size != 0){
            toArray(array, 0, head);
        }
        return array;
    }

    //private helper method
    private void toArray(Object[] arr, int index, Node<E> n){
        if(index != arr.length){
            arr[index] = n.data;
            toArray(arr, index+1, n.next);
        }
    }

    @Override
    public boolean add(E e) {
        if(size == 0){
            Node<E> node = new Node(e, null);
            head = node;
        } else if (head.data.compareTo(e) > 0){
            Node<E> node = new Node(e, head);
            head = node;
        } else {
            add(e, head);
        }
        size++;
        return true;
    }

    //private helper method
    private void add(E element, Node<E> n){
        if (n.next == null){
            Node<E> node = new Node(element, null);
            n.next = node;
        } else if(n.data.compareTo(element) <= 0 && n.next.data.compareTo(element) > 0){
            Node<E> node = new Node(element, n.next);
            n.next = node;
        } else {
            add(element, n.next);
        }
    }

    @Override
    public boolean remove(Object o) {
        if(size == 0){
            return false;
        } else if (head.data.equals(o)) {
            head = head.next;
            size--;
            return true;
        } else {
            return remove(o, head);
        }
    }

    //recursive private helper method
    private boolean remove(Object o, Node<E> n){
        if(n.next == null){
            return false;
        } else if (n.next.data.compareTo((E)o) > 0) {
            return false;
        } else if (n.next.data.compareTo((E)o) == 0) {
            n.next = n.next.next;
            size--;
            return true;
        } else {
            return remove(o, n.next);
        }
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        if(index == 0){
            return head.data;
        }
        return get(index, 1, head.next);
    }

    //private helper method
    private E get(int index, int currentIndex, Node<E> n){
        if(currentIndex == index){
            return n.data;
        } else {
            return get(index, currentIndex+1, n.next);
        }
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        if(size == 1){
            E oldData = head.data;
            head = null;
            size = 0;
            return oldData;
        } else if (index == 0) {
            E oldData = head.data;
            head = head.next;
            size--;
            return oldData;
        }
        return remove(index, 0, head);
    }

    //private helper function
    private E remove(int index, int currentIndex, Node<E> n){
        if(currentIndex == (index-1)){
            E oldData = n.data;
            n.next = n.next.next;
            size--;
            return oldData;
        } else {
            return remove(index, currentIndex+1, n.next);
        }
    }

    @Override
    public int indexOf(Object o) {
        if(size == 0){
            return -1;
        } else if (head.data.equals(o)) {
            return 0;
        } else {
            return indexOf(o, 1, head.next);
        }
    }

    //recursive private helper method
    private int indexOf(Object o, int currentIndex, Node<E> n){
        if (currentIndex == size){
            return -1;
        } else if (n.data.compareTo((E) o) > 0) {
            return -1;
        } else if (n.data.equals(o)){
            return currentIndex;
        }
        currentIndex++;
        return indexOf(o, currentIndex, n.next);
    }

    public String toString(){
        if (size == 0){
            return "[]";
        }
        return "[" + toString(head) + "]";
    }

    //private helper method
    private String toString(Node<E> n){
        if (n.next != null){
            return n.data + ", " + toString(n.next);
        }
        return n.data + "";
    }

    private static class Node<E>{
        private E data;
        private Node<E> next;

        Node(E data, Node<E> next){
            this.data = data;
            this.next = next;
        }
    }

    private class CiscLinkedListIterator implements Iterator<E>{
        private Node<E> nextNode;
        private int nextNodeIndex;

        public CiscLinkedListIterator(){
            nextNode = head;
            nextNodeIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return nextNodeIndex < size;
        }

        @Override
        public E next() {
            E value = nextNode.data;
            nextNode = nextNode.next;
            nextNodeIndex++;
            return value;
        }
    }

}