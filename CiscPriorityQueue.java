package edu.ust.cisc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CiscPriorityQueue<E extends Comparable<E>> implements CiscCollection {

    private E[] elementData;
    private Comparator<E> comparator;
    private int size;

    public CiscPriorityQueue(){
        elementData = (E[]) new Comparable[10];
    }

    public CiscPriorityQueue(Comparator<E> comparator){
        this();
        this.comparator = comparator;
    }

    public void add(E value){
        if(size >= elementData.length){
            elementData = Arrays.copyOf(elementData, elementData.length * 2 + 1);
        }

        elementData[size] = value;

        int index = size;
        int parentIndex;
        boolean foundLocation = false;
        while(!foundLocation && hasParent(size)){
            parentIndex = parentIndex(index);
            if(compare(value, elementData[parentIndex(index)]) > 0){
                swap(elementData, index, parentIndex);
                index = parentIndex;
            } else {
                foundLocation = true;
            }
        }
        size++;
    }

    public E peek(){
        if(!isEmpty()){
            return elementData[0];
        }
        throw new NoSuchElementException();
    }

    public E remove(){
        if(!isEmpty()){
            E returnData = elementData[0];
            elementData[0] = elementData[size-1];
            int parent = 0;
            int leftChild;
            int rightChild;
            int maxChild;
            while(true){
                leftChild = 2*parent + 1;
                rightChild = leftChild + 1;
                if(leftChild >= size){
                    break;
                }
                maxChild = leftChild;
                if(rightChild < size && compare(elementData[rightChild], elementData[leftChild]) > 0){
                    maxChild = rightChild;
                }
                if (compare(elementData[parent], elementData[maxChild]) < 0){
                    swap(elementData, parent, maxChild);
                    parent = maxChild;
                } else {
                    break;
                }
            }
            elementData[size-1] = null;
            size--;
            return returnData;
        }
        throw new NoSuchElementException();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean contains(Object value) {
        return contains((E) value, 0);
    }

    private boolean contains(E value, int index){
        if(index > size-1){
            return false;
        }
        if(compare(value, elementData[index]) == 0){
            return true;
        } else if (compare(value, elementData[index]) < 0)  {
            //value less than data at index
            return contains(value, 2*index + 1) || contains(value, 2*index + 2);
        }
        return false;
    }

    @Override
    public Iterator iterator() {
        return new CiscPriorityQueueIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] newArr = new Object[size];
        if(!isEmpty()){
            for(int i=0; i<size; i++){
                newArr[i] = elementData[i];
            }
        }
        return newArr;
    }

    @Override
    public void clear() {
        if(!isEmpty()){
            for(int i=0; i<size; i++){
                elementData[i] = null;
            }
        }
        size = 0;
    }

    private int compare(E item1, E item2){
        if(comparator != null){
            return comparator.compare(item1, item2);
        }
        return item1.compareTo(item2);
    }

    private boolean hasParent(int index){
        return index > 0;
    }

    private int parentIndex(int index){
        return (index-1)/2;
    }

    private void swap(E[] arr, int index1, int index2){
        E temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    public String toString(){
        if(!isEmpty()){
            String returnStr = "[";
            for(int i=0; i<size; i++){
                if(i == size - 1){
                    return returnStr + elementData[i] + "]";
                }
                returnStr = returnStr + elementData[i] + ", ";
            }
        }
        return "[]";
    }

    private class CiscPriorityQueueIterator implements Iterator<E>{

        private int nextIndex;

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public E next() {
            E element = elementData[nextIndex];
            nextIndex++;
            return element;
        }
    }

}

