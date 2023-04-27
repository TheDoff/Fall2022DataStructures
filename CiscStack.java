package edu.ust.cisc;

import java.util.EmptyStackException;
import java.util.Iterator;

public class CiscStack<E> implements CiscCollection<E> {

    public static final int DEFAULT_CAPACITY = 10;
    private E[] elementData;
    private int size;

    public CiscStack(){
        this(DEFAULT_CAPACITY);
    }

    public CiscStack(int initialCapacity){
        if(initialCapacity < 0){
            throw new IllegalArgumentException();
        }
        elementData = (E[]) new Object[initialCapacity];
        size = 0;
    }

    public E peek(){
        if(size == 0){
            throw new EmptyStackException();
        }
        return elementData[size-1];
    }

    public E pop(){
        if(isEmpty()){
            throw new EmptyStackException();
        }
        E item = elementData[size-1];
        elementData[size-1] = null;
        size--;
        return item;
    }

    public void push(E item){
        ensureCapacity(size+1);
        elementData[size] = item;
        size++;
    }

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
        for(int i=0; i<size; i++){
            if(elementData[i].equals(o)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CiscStackIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[size];
        int j = size-1;
        for(int i=0; i<size; i++){
            newArray[i] = elementData[j];
            j--;
        }
        return newArray;
    }

    @Override
    public void clear() {
        for(int i=0; i<size; i++){
            elementData[i] = null;
        }
        size = 0;
    }

    private void ensureCapacity(int minCapacity){
        if (elementData.length < minCapacity){
            if ((((elementData.length)*2) + 1) > minCapacity){
                E[] tempElementData = (E[]) new Object[(((elementData.length)*2) + 1)];
                for(int i=0; i<size; i++) {
                    tempElementData[i] = elementData[i];
                }
                elementData = tempElementData;
            } else {
                E[] tempElementData = (E[]) new Object[minCapacity];
                for(int i=0; i<size; i++) {
                    tempElementData[i] = elementData[i];
                }
                elementData = tempElementData;
            }
        }
    }

    private class CiscStackIterator implements Iterator<E>{

        private int nextIndex;

        public CiscStackIterator(){
            nextIndex = size - 1;

        }

        @Override
        public boolean hasNext() {
            return nextIndex >= 0;
        }

        @Override
        public E next() {
            E value = elementData[nextIndex];
            nextIndex--;
            return value;
        }
    }

}
