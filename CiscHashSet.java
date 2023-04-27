package edu.ust.cisc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class CiscHashSet<E> implements CiscCollection<E> {

    private static final double MAX_LOAD_FACTOR = 0.75;
    private Object[] elementData;
    private int size;
    private static final Object REMOVED = new Object();

    CiscHashSet(){
        elementData = new Object[11];
        size = 0;
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
        int bucket = hashFunction(o);
        Object current = elementData[bucket];

        while(current != null && !current.equals(o)){
            bucket = (bucket + 1) % elementData.length;
            current = elementData[bucket];
        }

        return current != null;
    }

    @Override
    public Iterator<E> iterator() {
        return new CiscHashSetIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] newArr = new Object[size];
        int newArrIndex = 0;
        if(!isEmpty()){
            for(int i=0; i< elementData.length; i++){
                if(elementData[i] != null && elementData[i] != REMOVED){
                    newArr[newArrIndex] = elementData[i];
                    newArrIndex++;
                }
            }
        }
        return newArr;
    }

    @Override
    public void clear() {
        Arrays.fill(elementData, null);
        size = 0;
    }

    public void add(E value){
        boolean inArray = false;
        for (Object current : elementData) {
            if (current == value) {
                inArray = true;
                break;
            }
        }
        if(!inArray){
            if(MAX_LOAD_FACTOR* elementData.length < size){
                rehash();
            }
            int bucket = hashFunction(value);
            for(int i=bucket; i < elementData.length;){
                if(elementData[i] == null || elementData[i] == REMOVED){
                    elementData[i] = value;
                    break;
                } else {
                    i = (i+1) % elementData.length;
                }
            }
            size++;
        }
    }

    private void rehash(){
        Object[] tempArr = toArray();
        elementData = new Object[elementData.length*2 + 1];
        size = 0;
        for (Object o : tempArr) {
            add((E) o);
        }
    }

    public void remove(E value){
        int bucket = hashFunction(value);
        Object current = elementData[bucket];

        while(current == REMOVED || (current != null && !current.equals(value))){
            bucket = (bucket + 1) % elementData.length;
            current = elementData[bucket];
        }

        if(current != null){
            elementData[bucket] = REMOVED;
            size--;
        }

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        sb.append("[");
        if(!isEmpty()){
            for(int i=0; i<elementData.length; i++){
                if(elementData[i] != null && elementData[i] != REMOVED){
                    if(!first){
                        sb.append(", ");
                    }
                    sb.append(elementData[i]);
                    first = false;
                }
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private int hashFunction(Object value){
        return Math.abs(value.hashCode()) % elementData.length;
    }

    private class CiscHashSetIterator implements Iterator<E>{

        private int nextIndex;

        CiscHashSetIterator(){
            for(int i=0; i< elementData.length; i++){
                if(elementData[i] != null && elementData[i] != REMOVED){
                    nextIndex = i;
                    break;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return nextIndex < elementData.length && elementData[nextIndex] != null;
        }

        @Override
        public E next() {
            E value = (E) elementData[nextIndex];
            getNextIndex();
            return value;
        }

        private void getNextIndex(){
            nextIndex++;
            for(int i=(nextIndex); i<elementData.length; i++){
                if(elementData[i] != null && elementData[i] != REMOVED){
                    nextIndex = i;
                    return;
                }
            }
        }
    }
}