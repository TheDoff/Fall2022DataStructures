package edu.ust.cisc;

import java.util.Iterator;

public class CiscDoublyLinkedList<E> implements CiscList<E> {

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
        Node<E> currentNode = head;
        for (int i=0; i<size; i++) {
            if(currentNode.data == o){
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public Iterator iterator() {
        return new CiscDoublyLinkedListIterator();
    }

    @Override
    public Object[] toArray() {
        Node<E> currentNode = head;
        Object[] newArray = new Object[size];
        for (int i=0; i<size; i++) {
            newArray[i] = currentNode.data;
            currentNode = currentNode.next;
        }
        return newArray;
    }

    @Override
    public boolean add(E e) {
        // add head
        if(size == 0){
            Node node = new Node(e, null, null);
            node.next = node;
            node.prev = node;
            head = node;
        // add to end of list
        } else {
            Node node = new Node(e, head, head.prev);
            head.prev.next = node;
            head.prev = node;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        //first node checked is head
        Node<E> currentNode = head;

        //head has to be valid
        if (currentNode != null){

            //cycling through linked list
            for (int i=0; i<size; i++) {

                // if target object is found
                if (currentNode.data == o) {

                    if (size == 1){
                        head = null;
                        size--;
                        return true;
                    }

                    //link prev and next nodes to each other
                    currentNode.prev.next = currentNode.next;
                    currentNode.next.prev = currentNode.prev;
                    //Account for head as the removable node
                    if(currentNode == head){
                        head = head.next;
                    }
                    //decrement size
                    size--;
                    return true;
                }
                currentNode = currentNode.next;
            }
        }
        return false;
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
        Node<E> currentNode = head;
        for (int i=0; i<size; i++) {
            if (i == index){
                return currentNode.data;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    @Override
    public E set(int index, E element) {
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        Node<E> currentNode = head;
        for (int i=0; i<size; i++) {
            if(i == index){
                E prevElement = currentNode.data;
                currentNode.data = element;
                return prevElement;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    @Override
    public void add(int index, E element) {
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        }
        //if adding to start of the list
        if (index == 0){
            //Add to an empty list
            if (head == null){
                Node newNode = new Node(element, null, null);
                newNode.next = newNode;
                newNode.prev = newNode;
                head = newNode;
                size++;
            // add to the head of the list
            } else {
                Node newNode = new Node(element, head, head.prev);
                head.prev.next = newNode;
                head.prev = newNode;
                head = newNode;
                size++;
            }
        // adding to the end of the list
        } else if (index == size) {
            Node newNode = new Node(element, head, head.prev);
            head.prev.next = newNode;
            head.prev = newNode;
            size++;
        } else {
            // add to the middle of the list
            Node<E> currentNode = head;
            for (int i=0; i<size; i++) {
                if (i == index) {
                    Node newNode = new Node(element, currentNode, currentNode.prev);
                    currentNode.prev.next = newNode;
                    currentNode.prev = newNode;
                    size++;
                    break;
                }
                currentNode = currentNode.next;
            }
        }
    }

    @Override
    public E remove(int index) {
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        if (size == 1){
            E returnData = head.data;
            head = null;
            size--;
            return returnData;
        }
        Node<E> currentNode = head;
        E oldData;
        for (int i=0; i<size; i++) {
            if(index == i){
                oldData = currentNode.data;
                currentNode.prev.next = currentNode.next;
                currentNode.next.prev = currentNode.prev;
                if(index == 0){
                    head = head.next;
                }
                currentNode.next = null;
                currentNode.prev = null;
                size--;
                return oldData;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    @Override
    public int indexOf(Object o) {
        Node<E> currentNode = head;
        for (int i=0; i<size; i++) {
            if (currentNode.data == o){
                return i;
            }
            currentNode = currentNode.next;
        }
        return -1;
    }

    private static class Node<E> {
        private E data;
        private Node<E> next;
        private Node<E> prev;

        private Node(E data, Node<E> next, Node<E> prev){
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private class CiscDoublyLinkedListIterator implements Iterator<E>{

        private Node<E> nextNode;
        private int nextNodeIndex;

        public CiscDoublyLinkedListIterator() {
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