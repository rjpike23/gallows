package com.swkoan.gallows.testutil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 */
public class MVHashMap<K, V> extends HashMap<K, List<V>> implements MultivaluedMap<K, V> {

    @Override
    public void putSingle(K key, V value) {
        List<V> valList = new LinkedList<V>();
        valList.add(value);
        this.put(key, valList);
    }

    @Override
    public void add(K key, V value) {
        List<V> valList = this.get(key);
        if (valList == null) {
            valList = new LinkedList<V>();
            this.put(key, valList);
        }
        valList.add(value);
    }

    @Override
    public V getFirst(K key) {
        List<V> valList = this.get(key);
        if (valList == null || valList.isEmpty()) {
            return null;
        }
        else {
            return valList.get(0);
        }
    }
}
