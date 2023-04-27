package edu.ust.cisc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CiscFancyBST<E extends Comparable<E>> implements CiscCollection<E> {

    private BSTNode<E> root;
    private BSTNode<E> first;
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
        return contains(root, (E)o);
    }

    private boolean contains(BSTNode<E> node, E value) {
        if (node == null) {
            return false;
        } else {
            int compare = value.compareTo(node.data);
            if (compare == 0) {
                return true;
            } else if (compare < 0) {
                return contains(node.left, value);
            } else {   // compare > 0
                return contains(node.right, value);
            }
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new CiscFancyBSTIterator();
    }

    @Override
    public Object[] toArray() {
        ArrayList<E> aList = new ArrayList<E>(size);
        toArray(root, aList);
        return aList.toArray();
    }

    private void toArray(BSTNode<E> node, List<E> aList) {
        if (node != null) {
            toArray(node.left, aList);
            aList.add(node.data);
            toArray(node.right, aList);
        }
    }

    @Override
    public void clear() {
        root = null;
        first = null;
        size = 0;
    }

    public void add(E value) {
        this.root = add(root, value);
        this.root.next = getNextNode(root);
        assignFirst();
    }

    private BSTNode<E> add(BSTNode<E> node, E value) {
        if (node == null){
            node = new BSTNode<E>(value);
            this.size++;
        } else if (node.data.compareTo(value) > 0){
            node.left = add(node.left, value);
            node.left.parent = node;
            node.left.next = getNextNode(node.left);
        } else if (node.data.compareTo(value) < 0){
            node.right = add(node.right, value);
            node.right.parent = node;
            node.right.next = getNextNode(node.right);
        }
        return node;
    }

    private BSTNode<E> getNextNode(BSTNode<E> node) {
        if(node.right != null){
            node = node.right;
            while(node.left != null){
                node = node.left;
            }
            return node;
        }
        while(node.parent != null && node != node.parent.left){
            node = node.parent;
        }
        if(node.parent != null){
            return node.parent;
        } else {
            return null;
        }

    }

    private void assignFirst() {
        BSTNode<E> node = root;
        while(node.left != null){
            node = node.left;
        }
        first = node;
    }

    private BSTNode<E> getPreviousNode(BSTNode<E> node) {
        if(node.left != null){
            node = node.left;
            while(node.right != null){
                node = node.right;
            }
            return node;
        }
        while(node.parent != null && node != node.parent.right){
            node = node.parent;
        }
        if(node.parent != null){
            return node.parent;
        } else {
            return null;
        }
    }

    public void remove(E value) {
        this.root = remove(root, value);
        if(root == null){
            first = null;
        } else {
            assignFirst();
        }
    }

    private BSTNode<E> remove(BSTNode<E> node, E value) {
        if (node == null){
            return null;
        } else if (node.data.compareTo(value) > 0) {
            node.left = remove(node.left, value);
        } else if (node.data.compareTo(value) < 0) {
            node.right = remove(node.right, value);
        } else { // node.data == value; remove this node
            BSTNode<E> prev = getPreviousNode(node);
            if (node.right == null){
                //update prev
                if(prev != null){
                    prev.next = node.next;
                }
                //update parent if not being replaced with null
                if(node.left != null){
                    node.left.parent = node.parent;
                }
                size--;
                return node.left; // no R child; replace w/ L
            } else if (node.left == null){
                //update prev
                if(prev != null){
                    prev.next = node.next;
                }
                //update parent if not being replaced with null
                if(node.right != null){
                    node.right.parent = node.parent;
                }
                size--;
                return node.right; // no L child; replace w/ R
            } else {
                // both children; replace w/ max from L
                node.data = getMax(node.left);
                node.left = remove(node.left, node.data);
            }
        }
        return node;
    }

    private E getMax(BSTNode<E> node) {
        if (node.right == null) {
            return node.data;
        } else {
            return getMax(node.right);
        }
    }

    public void balance() {
        this.root = balance(toNodeArray(), 0, this.size - 1, null);
    }

    private BSTNode<E> balance(Object[] values, int start, int end, BSTNode<E> parent){
        // base case
        if(start > end){
            return null;
        }
        // set the middle element and make it root
        int midIndex = (start + end) / 2;

        // using inorder traversal, construct left and right subtrees
        BSTNode<E> node = (BSTNode<E>) values[midIndex];
        node.left = balance(values, start, (midIndex - 1), node);
        node.right = balance(values, (midIndex + 1), end, node);
        node.parent = parent;

        return node;
    }

    public Object[] toNodeArray() {
        ArrayList<BSTNode<E>> aList = new ArrayList<>(size);
        toNodeArray(root, aList);
        return aList.toArray();
    }
    private void toNodeArray(BSTNode<E> node, List<BSTNode<E>> alist) {
        if(node != null) {
            toNodeArray(node.left, alist);
            alist.add(node);
            toNodeArray(node.right, alist);
        }
    }

    private class CiscFancyBSTIterator implements Iterator<E> {
        private BSTNode<E> nextNode;

        public CiscFancyBSTIterator() {
            nextNode = first;
        }

        public boolean hasNext() {
            return nextNode != null;
        }

        public E next() {
            E value = nextNode.data;
            nextNode = nextNode.next;
            return value;
        }
    }

    private static class BSTNode<E> {
        private E data;
        private BSTNode<E> left;
        private BSTNode<E> right;
        private BSTNode<E> parent;
        private BSTNode<E> next;

        public BSTNode(E data) {
            this(data, null, null, null, null);
        }

        public BSTNode(E data, BSTNode<E> left, BSTNode<E> right, BSTNode<E> parent, BSTNode<E> next) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.next = next;
        }
    }

}
