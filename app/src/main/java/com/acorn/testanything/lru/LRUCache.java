package com.acorn.testanything.lru;

import android.util.LruCache;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Created by acorn on 2019/10/26.
 */
public class LRUCache<K, V> {
    //最大容量
    private int cacheCapcity;
    private HashMap<K, CacheNode> caches;
    private CacheNode first, last;

    public static void main(String[] args) {
        LRUCache<Integer, String> lru = new LRUCache<Integer, String>(3);

        lru.put(1, "a");    // 1:a
        System.out.println(lru.toString());
        lru.put(2, "b");    // 2:b 1:a
        System.out.println(lru.toString());
        lru.put(3, "c");    // 3:c 2:b 1:a
        System.out.println(lru.toString());
        lru.put(4, "d");    // 4:d 3:c 2:b
        System.out.println(lru.toString());
        lru.put(1, "aa");   // 1:aa 4:d 3:c
        System.out.println(lru.toString());
        lru.put(2, "bb");   // 2:bb 1:aa 4:d
        System.out.println(lru.toString());
        lru.put(5, "e");    // 5:e 2:bb 1:aa
        System.out.println(lru.toString());
        lru.get(1);         // 1:aa 5:e 2:bb
        System.out.println(lru.toString());
        lru.remove(11);     // 1:aa 5:e 2:bb
        System.out.println(lru.toString());
        lru.remove(1);      //5:e 2:bb
        System.out.println(lru.toString());
        lru.put(1, "aaa");  //1:aaa 5:e 2:bb
        System.out.println(lru.toString());
    }

    public LRUCache(int size) {
        this.cacheCapcity = size;
        caches = new HashMap<>(size);
    }

    public void put(K k, V v) {
        CacheNode node = caches.get(k);
        if (null == node) {
            if (caches.size() >= cacheCapcity) {
                if (last != null)
                    caches.remove(last.key);
                removeLast();
            }
            node = new CacheNode();
            node.key = k;
        }
        node.value = v;
        moveToFirst(node);
        caches.put(k, node);
    }

    public V get(K k) {
        CacheNode node = caches.get(k);
        if (null == node)
            return null;
        moveToFirst(node);
        return node.value;
    }

    public V remove(K k) {
        CacheNode node = caches.get(k);
        if (null != node) {
            if (first == node) {
                first = first.next;
            }
            if (last == node) {
                last = last.pre;
            }
            if (null != node.pre) {
                node.pre.next = node.next;
            }
            if (null != node.next) {
                node.next.pre = node.pre;
            }
        }
        CacheNode removeNode = caches.remove(k);
        return removeNode == null ? null : removeNode.value;
    }

    public void clear() {
        first = null;
        last = null;
        caches.clear();
    }

    private void moveToFirst(CacheNode node) {
        if (node == first)
            return;
        if (node.next != null) {
            node.next.pre = node.pre;
        }
        if (node.pre != null) {
            node.pre.next = node.next;
        }
        if (node == last) {
            last = node.pre;
        }
        if (first == null || last == null) {
            first = last = node;
            return;
        }
        node.next = first;
        first.pre = node;
        first = node;
        first.pre = null;
    }

    private void removeLast() {
        if (last == null)
            return;
        last = last.pre;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }
    }

    @NotNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        CacheNode node = first;
        while (node != null) {
            sb.append(String.format("%s:%s ", node.key, node.value));
            node = node.next;
        }
        return sb.toString();
    }

    class CacheNode {
        CacheNode pre;
        CacheNode next;
        K key;
        V value;

        public CacheNode() {

        }
    }
}
