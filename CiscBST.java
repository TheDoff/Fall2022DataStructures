package edu.ust.cisc;

import java.util.*;

public class CiscBST<E extends Comparable<E>> implements CiscCollection<E> {

    private BSTNode<E> root;
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
        if(!isEmpty()){
            return contains(root, (E) o);
        }
        return false;
    }

    private boolean contains(BSTNode<E> node, E value){
        if(node == null){
            return false;
        } else if(value.compareTo(node.data) < 0){
            return contains(node.left, value);
        } else if (value.compareTo(node.data) > 0) {
            return contains(node.right, value);
        } else {
            return true;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new CiscBSTIterator<>(root);
    }

    @Override
    public Object[] toArray() {
        if (!isEmpty()){
            ArrayList<E> newArray = new ArrayList<>(size);
            toArray(root, newArray);
            return newArray.toArray();
        }
        return null;
    }

    private void toArray(BSTNode<E> node, List<E> aList){
        if(node != null){
            toArray(node.left, aList);
            aList.add(node.data);
            toArray(node.right, aList);
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    public void add(E value){
        root = add(root, value);
    }

    private BSTNode<E> add(BSTNode<E> node, E value){
        if(node == null){
            size++;
            return new BSTNode<E>(value);
        } else if(value.compareTo(node.data) < 0){
            node.left = add(node.left, value);
            return node;
        } else if (value.compareTo(node.data) > 0) {
            node.right = add(node.right, value);
            return node;
        } else {
            return node;
        }
    }

    public void remove(E value){
        if(!isEmpty()) {
            root = remove(root, value);
        }
    }

    private BSTNode<E> remove(BSTNode<E> node, E value){
        if(node == null){
            //not in tree
            return null;
        }
        int result = value.compareTo(node.data);
        if(result < 0){
            //value smaller than node's data
            node.left = remove(node.left, value);
        } else if (result > 0) {
            //value larger than node's data
            node.right = remove(node.right, value);
        } else {
            //value is equal to node's data
            size--;
            if(node.left == null){
                //return right child
                return node.right;
            } else if (node.right == null) {
                //return left child
                return node.left;
            } else {
                //node has two children
                if(node.left.right == null){
                    //left child does not have a right child
                    node.data = node.left.data;
                    node.left = node.left.left;
                } else {
                    node.data = getMax(node.left).data;
                }
            }
        }
        return node;
    }

    private BSTNode<E> getMax(BSTNode<E> node){
        if(node.right.right == null){
            BSTNode<E> returnNode = node.right;
            node.right = node.right.left;
            return returnNode;
        } else {
            return getMax(node.right);
        }
    }

    private static class CiscBSTIterator<E> implements Iterator<E>{

        private Stack<BSTNode<E>> stack;

        public CiscBSTIterator(BSTNode<E> node){
            Stack<BSTNode<E>> newStack = new Stack<>();
            if (node != null){
                BSTNode<E> currentNode = node;
                while(currentNode != null){
                    newStack.push(currentNode);
                    currentNode = currentNode.left;
                }
            }
            this.stack = newStack;
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            BSTNode<E> nextElement = stack.pop();
            if(nextElement.right != null){
                BSTNode<E> currentNode = nextElement.right;
                while(currentNode != null){
                    stack.push(currentNode);
                    currentNode = currentNode.left;
                }
            }

            return nextElement.data;
        }
    }

    private static class BSTNode<E> {
        private E data;
        private BSTNode<E> left;
        private BSTNode<E> right;

        public BSTNode(E data){
            this(data, null, null);
        }

        public BSTNode(E data, BSTNode<E> left, BSTNode<E> right){
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

}
