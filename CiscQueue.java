package edu.ust.cisc;

import java.util.Iterator;

public class CiscQueue<E> implements CiscCollection<E> {

    public static final int DEFAULT_CAPACITY = 10;
    private E[] elementData;
    private int frontIndex;
    private int rearIndex;

    public CiscQueue(){
        this(DEFAULT_CAPACITY);
    }

    public CiscQueue(int initialCapacity){
        if(initialCapacity < 0){
            throw new IllegalArgumentException();
        }
        elementData = (E[]) new Object[initialCapacity];
        frontIndex = -1;
        rearIndex = -1;
    }

    public boolean offer(E item){
        ensureCapacity(size() + 1);
        rearIndex = (rearIndex + 1) % elementData.length;
        elementData[rearIndex] = item;
        return true;
    }

    public E poll(){
        if(!isEmpty()){
            E temp = elementData[frontIndex];
            elementData[frontIndex] = null;
            if(frontIndex == rearIndex){
                frontIndex = -1;
                rearIndex = -1;
            } else {
                frontIndex = (frontIndex + 1) % elementData.length;
            }
            return temp;
        }
        return null;
    }

    public E peek(){
        if(!isEmpty()){
            return elementData[frontIndex];
        }
       return null;
    }

    @Override
    public int size() {
        if (rearIndex > frontIndex){
            return rearIndex - frontIndex + 1;
        } else if (frontIndex > rearIndex){
            return elementData.length - (frontIndex - rearIndex) + 1;
        } else if (frontIndex == -1){
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public boolean isEmpty() {
        return frontIndex == -1;
    }

    @Override
    public boolean contains(Object o) {
        if(!isEmpty()){
            int i = frontIndex;
            while(i != rearIndex){
                if(elementData[i].equals(o)){
                    return true;
                }
                i = (i + 1) % elementData.length;
            }
            return elementData[rearIndex].equals(o);
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CiscQueueIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[size()];
        if(!isEmpty()) {
            int arrIndex = 0;
            int i = frontIndex;
            while (i != rearIndex) {
                newArray[arrIndex] = elementData[i];
                i = (i + 1) % elementData.length;
                arrIndex++;
            }
            newArray[arrIndex] = elementData[rearIndex];
        }
        return newArray;
    }

    @Override
    public void clear() {
        if(!isEmpty()){
            int i = frontIndex;
            while(i != rearIndex){
                elementData[i] = null;
                i = (i + 1) % elementData.length;
            }
            elementData[rearIndex] = null;
            frontIndex = -1;
            rearIndex = -1;
        }
    }

    private void ensureCapacity(int minCapacity) {
        int newSize = 0;
        if (elementData.length < minCapacity){
            if ((((elementData.length)*2) + 1) > minCapacity){
                newSize = (((elementData.length)*2) + 1);
            } else {
                newSize = minCapacity;
            }
            E[] tempElementData = (E[]) new Object[newSize];
            if (isEmpty()){
                elementData = tempElementData;
            } else {
                int tempIndex = 0;
                int i = frontIndex;
                while(i != rearIndex){
                    tempElementData[tempIndex] = elementData[i];
                    i = (i + 1) % elementData.length;
                    tempIndex++;
                }
                tempElementData[tempIndex] = elementData[i];
                frontIndex = 0;
                rearIndex = tempIndex;
                elementData = tempElementData;
            }
        }
    }

    private class CiscQueueIterator implements Iterator<E>{

        private int numElementsReturned;

        @Override
        public boolean hasNext() {
            return numElementsReturned < size();
        }

        @Override
        public E next() {
            E value = null;
            int currentIndex = frontIndex;
            for(int i=0; i<=numElementsReturned; i++){
                if(i==numElementsReturned){
                    value = elementData[currentIndex];
                }
                currentIndex = (currentIndex + 1) % elementData.length;
            }
            numElementsReturned++;
            return value;
        }
    }
}
