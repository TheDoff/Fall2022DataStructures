package edu.ust.cisc;

import java.util.Iterator;

public class CiscArrayList<E> implements CiscList<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private E[] elementData;
    private int size;

    public CiscArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public CiscArrayList(int initialCapacity){
        if (initialCapacity < 0){
            throw new IllegalArgumentException();
        }
        elementData = (E[]) new Object[initialCapacity];
        size = 0;
    }

    @java.lang.Override
    public int size() {
        return size;
    }

    @java.lang.Override
    public boolean isEmpty() {
        return size == 0;
    }

    @java.lang.Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @java.lang.Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @java.lang.Override
    public Object[] toArray() {
        Object[] new_array = new Object[size];
        System.arraycopy(elementData, 0, new_array, 0, size);
        return new_array;
    }

    @java.lang.Override
    public boolean add(E element) {
        int oldSize = size;

        ensureCapacity(size+1);
        elementData[size] = element;
        size++;

        return size == oldSize+1;
    }

    @java.lang.Override
    public boolean remove(Object o) {
        for(int i=0; i<size; i++){
            if (elementData[i].equals(o)){
                remove(i);
                return true;
            }
        }
        return false;
    }

    @java.lang.Override
    public void clear() {
        for(int i=0; i<size; i++){
            elementData[i] = null;
        }
        size = 0;
    }

    @java.lang.Override
    public E get(int index) {
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        return elementData[index];
    }

    @java.lang.Override
    public E set(int index, E element) {
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        E prev_pos = elementData[index];
        elementData[index] = element;
        return prev_pos;
    }

    @java.lang.Override
    public void add(int index, E element) {
        if (index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        }
        ensureCapacity(size + 1);
        System.arraycopy(elementData, index, elementData, index+1, size-index);
        elementData[index] = element;
        size++;
    }

    @java.lang.Override
    public E remove(int index) {
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        E removed_obj = elementData[index];
        System.arraycopy(elementData, index+1, elementData, index, size-index-1);
        elementData[size-1] = null;
        size--;
        return removed_obj;
    }

    @java.lang.Override
    public int indexOf(Object o) {
        for(int i=0; i<size; i++){
            if (elementData[i].equals(o)){
                return i;
            }
        }
        return -1;
    }

    public void ensureCapacity(int minCapacity){
        if(minCapacity > elementData.length){
            int newCapacity = elementData.length * 2 + 1;
            if(newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            E[] newElementData = (E[]) new Object[newCapacity];
            System.arraycopy(elementData, 0, newElementData, 0, size);
            elementData = newElementData;
        }
    }

}
