package com.safin.translator;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by vitalii.safin on 2/1/2017.
 */
public class MyHashMap<Key, Value> implements Map<Key, Value> {

    private final int DEFAULT_SIZE = 64;
    private int size;
    private int capacity;

    private MyLinkedList<MapEntry>[] buckets;

    /**
     * Basic hash bin node
     */
    private class MapEntry implements Entry<Key, Value> {
        private Key key;
        private Value value;

        MapEntry(Key k, Value v) {
            key = k;
            value = v;
        }

        int keyHashCode() {
            return Math.abs(key.hashCode()) % capacity;
        }

        public Key getKey() {
            return key;
        }

        public Value getValue() {
            return value;
        }

        public Value setValue(Value value) {
            Value old = this.value;
            this.value = value;
            return old;
        }

        public String toString() {
            return "Key:" + key + " Value:" + value;
        }
    }

    MyHashMap() {
        size = 0;
        capacity = DEFAULT_SIZE;
        buckets = new MyLinkedList[DEFAULT_SIZE];
        for (int i = 0; i < DEFAULT_SIZE; ++i) {
            buckets[i] = new MyLinkedList<MapEntry>();
        }
    }

    private int hashCodeLocal(Object o) {
        return Math.abs(o.hashCode()) % capacity;
    }

    private boolean hashCodeExists(int hashcode) {
        return hashcode <= capacity;
    }

    private void ensureCapacity() {
        if ((((double) size) / ((double) capacity)) > 0.75) {
            MyLinkedList<MapEntry>[] tempBuckets = buckets;
            buckets = new MyLinkedList[capacity * 3 / 2 + 1];
            System.arraycopy(tempBuckets, 0, buckets, 0, size);
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Value get(Object key) {
        int keyHash = hashCodeLocal(key);
        try {
            if (hashCodeExists(keyHash)) {
                MyLinkedList<MapEntry> list = null;
                try {
                    list = buckets[keyHash];
                } catch (Exception e) {
                    System.out.println("Exception in list creation " + e.toString());
                }
                for (MapEntry mEntry : list) {
                    if (mEntry.key.equals(key)) {
                        return list.get(list.indexOf(mEntry)).value;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in map.get()" + e.toString());
        }
        return null;
    }

    public Value put(Key key, Value value) {
        MapEntry newEntry = new MapEntry(key, value);
        int newEntryKeyHashCode = newEntry.keyHashCode();
        System.out.println("New hashcode : " + newEntryKeyHashCode);
        if (hashCodeExists(newEntryKeyHashCode)) { //element with the same key-hashcode exists
            MyLinkedList<MapEntry> list = buckets[newEntryKeyHashCode];
            for (MapEntry mEntry : list) {
                if (mEntry.key.equals(key)) {
                    Value old = list.get(list.indexOf(mEntry)).value;
                    list.set(list.indexOf(mEntry), newEntry);
                    return old;
                }
            }
            //if not found via ( equals() )
            buckets[newEntryKeyHashCode].add(0, newEntry);
            ++size;
            return null;
        } else { //unique entry
            try {
                ensureCapacity();
                buckets[newEntryKeyHashCode].add(newEntry);
                ++size;
                return null;
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("In put.else " + ex);
            }
            return null;
        }
    }

    public boolean containsKey(Object key) {
        if (hashCodeLocal(key) > capacity) {
            return false;
        }
        for (MapEntry mEntry : buckets[hashCodeLocal(key)]) {
            if (mEntry.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(Object value) {
        for (int i = 0; i < size; i++) {
            for (MapEntry mEntry : buckets[i]) {
                if (mEntry.value.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Value remove(Object key) {
        if (containsKey(key)) { //hashcode found
            for (MapEntry mEntry : buckets[hashCodeLocal(key)]) {
                if (mEntry.key.equals(key)) {
                    Value old = mEntry.value;
                    buckets[hashCodeLocal(key)].remove(mEntry);
                    --size;
                    return old;
                }
            }
            return null;
        }
        return null;
    }

    public void putAll(Map<? extends Key, ? extends Value> m) {
        if (m != null && m.size() != 0) {
            for (Entry<? extends Key, ? extends Value> e : m.entrySet()) {
                put(e.getKey(), e.getValue());
            }
        }
    }

    public void clear() {
        for (MyLinkedList<MapEntry> bucket : buckets) {
            bucket.clear();
        }
        size = 0;
        capacity = DEFAULT_SIZE;
    }

    public Set<Key> keySet() {
        HashSet<Key> set = new HashSet<Key>();
        for (MyLinkedList<MapEntry> bucket : buckets) {
            for (MapEntry mEntry : bucket) {
                set.add(mEntry.getKey());
            }
        }
        return set;
    }

    public Collection<Value> values() {
        MyLinkedList<Value> list = new MyLinkedList<Value>();
        for (MyLinkedList<MapEntry> bucket : buckets) {
            for (MapEntry mEntry : bucket) {
                list.add(mEntry.getValue());
            }
        }
        return list;
    }

    public Set<Entry<Key, Value>> entrySet() {
        HashSet<Entry<Key, Value>> set = new HashSet<Entry<Key, Value>>();
        for (MyLinkedList<MapEntry> bucket : buckets) {
            for (MapEntry mEntry : bucket) {
                set.add(mEntry);
            }
        }
        return set;
    }

    public String toString() {
        String result = "--Map view--\n";
        for (Entry<Key, Value> entry : entrySet()) {
            result += "Bucket #" + hashCodeLocal(entry.getKey()) + entry + "\n";
        }
        result += "Map size : " + size + "\n";
        return result;
    }
}
