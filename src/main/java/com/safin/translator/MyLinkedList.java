package com.safin.translator;

import java.util.*;

/**
 * Created by vitalii.safin on 1/31/2017.
 */


public class MyLinkedList<T> implements List<T>, Iterable<T> {

    private int size;
    private ListNode head;
    private ListNode tail;

    private class ListNode {
        private T value;
        private ListNode next;
        private ListNode prev;

        ListNode(T value) {
            this.value = value;
        }

        void setValue(T val) {
            value = val;
        }

        T getValue() {
            return value;
        }
    }

    class MyIterator implements ListIterator<T> {

        private ListNode currentNode;
        private int currentIndex;

        MyIterator(int index) {
            if (indexNotInRange(index)) {
                throw new IndexOutOfBoundsException();
            }
            currentNode = getNode(index);
            currentIndex = index;
        }

        public boolean hasNext() {
            return currentIndex != size();
        }

        public T next() {
            if (hasNext()) {
                T temp = currentNode.value;
                currentNode = currentNode.next;
                ++currentIndex;
                return temp;
            }
            throw new NoSuchElementException();
        }

        public boolean hasPrevious() {
            return currentIndex != 0;
        }

        public T previous() {
            if (hasPrevious()) {
                T temp = currentNode.value;
                currentNode = currentNode.prev;
                --currentIndex;
                return temp;
            }
            throw new NoSuchElementException();
        }

        public int nextIndex() {
            return indexOf(getNode(currentIndex).value);
        }

        public int previousIndex() {
            return indexOf(getNode(currentIndex - 1).value);
        }

        public void remove() {
            MyLinkedList.this.remove(nextIndex());
        }

        public void set(T t) {
            MyLinkedList.this.set(nextIndex(),t);
        }

        public void add(T t) {
            MyLinkedList.this.add(nextIndex(), t);
        }
    }


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    public Iterator<T> iterator() {
        return new MyIterator(0);
    }

    public Iterator<T> getIterator(int index) throws IndexOutOfBoundsException {
        if (indexNotInRange(index)) {
            throw new IndexOutOfBoundsException();
        }
        return new MyIterator(index);
    }

    public Object[] toArray() {
        Object[] returnArray = new Object[size];
        ListNode current = head;
        int index = 0;
        while (current != null) {
            returnArray[index++] = current.value;
            current = current.next;
        }
        return returnArray;
    }

    public <T1> T1[] toArray(T1[] a) {
        //if (T1.class.isAssignableFrom(T.class);
        if (a.length <= size) {
            ListNode current = head;
            int index = 0;
            while (current != null) {
                //a[index++] = current.value;
                current = current.next;
            }
        }
        return null;
    }

    public boolean add(T obj) throws InputMismatchException {
        if (inputIsInvalid(obj)) {
            throw new InputMismatchException();
        }
        ListNode newNode = new ListNode(obj);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            newNode.prev.next = newNode;
            tail = newNode;
        }
        ++size;
        return true; // always inserting
    }

    public boolean remove(Object o) throws InputMismatchException {
        if (inputIsInvalid(o)) {
            throw new InputMismatchException();
        }
        ListNode current = head;
        while (current != null) {
            if (o.equals(current.value)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next;
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev;
                }
                --size;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean containsAll(Collection<?> c) throws InputMismatchException {
        if (inputIsInvalid(c)) {
            throw new InputMismatchException();
        }
        int occurrences = 0;
        Object[] rawView = c.toArray();
        for (Object aRawView : rawView) {
            if (contains(aRawView))
                ++occurrences;
        }
        return occurrences == c.size();
    }

    public boolean addAll(Collection<? extends T> c) throws InputMismatchException {
        if (inputIsInvalid(c)) {
            throw new InputMismatchException();
        }
        for(T t : c) {
            add(t);
        }
        return true;
    }

    public boolean addAll(int index, Collection<? extends T> c) throws InputMismatchException, IndexOutOfBoundsException {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        if (inputIsInvalid(c)) {
            throw new InputMismatchException();
        }
        for(T t : c) {
            add(index++, t);
        }
        return true;
    }

    public boolean removeAll(Collection<?> c) throws InputMismatchException {
        if (inputIsInvalid(c)) {
            throw new InputMismatchException();
        }
        boolean collectionChanged = false;
        Object[] rawView = c.toArray();
        for (int i = 0, j = 0; i < rawView.length; i++) {
            if (contains(rawView[i])) {
                remove(j++);
                collectionChanged = true;
            }
        }
        return collectionChanged;
    }

    public boolean retainAll(Collection<?> c) throws InputMismatchException {
        if (inputIsInvalid(c)) {
            throw new InputMismatchException();
        }
        boolean collectionChanged = false;
        Object[] rawView = c.toArray();
        for (int i = 0, j = 0; i < rawView.length; i++) {
            if (!contains(rawView[i])) {
                remove(j++);
                collectionChanged = true;
            }
        }
        return collectionChanged;
    }

    public void clear() {
        size = 0;
        head = null;
        tail = null;
    }

    public T get(int index) throws IndexOutOfBoundsException {
        if (indexNotInRange(index)) {
            throw new IndexOutOfBoundsException();
        }
        ListNode current = head;
        int id = 0;
        while (id <= index) {
            if (id++ == index) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public T set(int index, T element) throws InputMismatchException, IndexOutOfBoundsException{
        if (indexNotInRange(index)) {
            throw new IndexOutOfBoundsException();
        }
        if (inputIsInvalid(element)) {
            throw new InputMismatchException();
        }
        T previous = get(index);
        ListNode current = head;
        int id = 0;
        while (id <= index) {
            if (id++ == index) {
                previous = current.value;
                current.value = element;
                return previous;
            }
            current = current.next;
        }
        return previous;
    }

    public void add(int index, T element) throws InputMismatchException {
        if (inputIsInvalid(element)) {
            throw new InputMismatchException();
        }
        if (index == size) { //insert at the tail
            add(element);
        } else if (index == 0) {
            ListNode newNode = new ListNode(element);
            newNode.next = head;
            newNode.next.prev = newNode;
            head = newNode;
            ++size;
        } else {
            ListNode newNode = new ListNode(element);
            ListNode prevNode = getNode(index - 1),
                     nextNode = getNode(index);
            prevNode.next = newNode;
            nextNode.prev = newNode;
            newNode.prev = prevNode;
            newNode.next = nextNode;
        }
    }

    public T remove(int index) throws IndexOutOfBoundsException {
        if (indexNotInRange(index)) {
            throw new IndexOutOfBoundsException();
        }
        T previous = get(index);
        ListNode current = head;
        int id = 0;
        while (id <= index) {
            if (id++ == index) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                }
                else {
                    head = current.next;
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                }
                else {
                    tail = current.prev;
                }
                --size;
                return previous;
            }
            current = current.next;
        }
        return previous;
    }

    public int indexOf(Object o) throws InputMismatchException {
        if (inputIsInvalid(o)) {
            throw new InputMismatchException();
        }
        ListNode current = head;
        int index = 0;
        while (current != null) {
            if (o.equals(current.value)) {
                return index;
            }
            current = current.next;
            ++index;
        }
        return -1;
    }

    public int lastIndexOf(Object o) throws InputMismatchException {
        if (inputIsInvalid(o)) {
            throw new InputMismatchException();
        }
        ListNode current = tail;
        int index = size - 1;
        while (current != null) {
            if (o.equals(current.value)) {
                return index;
            }
            current = current.prev;
            --index;
        }
        return -1;
    }

    public ListIterator<T> listIterator() {
        return new MyIterator(0);
    }

    public ListIterator<T> listIterator(int index) {
        return new MyIterator(index);
    }

    public List<T> subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException {
        if (indexNotInRange(fromIndex) || indexNotInRange(toIndex)) {
            throw new IndexOutOfBoundsException();
        }
        List<T> listPart = new MyLinkedList<T>();
        for(;fromIndex < toIndex; ++fromIndex) {
            listPart.add(get(fromIndex));
        }
        return listPart;
    }

    /**
     * Performs range checkings
     * @param index - index to be checked
     * @return index existence
     */
    private boolean indexNotInRange(int index) {
        return index < 0 || index > size;
    }

    private boolean inputIsInvalid(Object obj) {
        return obj == null;
    }

    private ListNode getNode(int index) throws IndexOutOfBoundsException {
        if (indexNotInRange(index)) {
            throw new IndexOutOfBoundsException();
        }
        ListNode current = head;
        while(index > 0) {
            current = current.next;
            --index;
        }
        return current;
    }

    public String toString() {
        String result = "MyLinkedList<T> : ";
        ListNode current = head;
        while (current != null) {
            result += current.value + " ";
            current = current.next;
        }
        return result;
    }
}

