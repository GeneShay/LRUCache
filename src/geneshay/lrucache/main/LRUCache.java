package geneshay.lrucache.main;

import geneshay.lrucache.main.exceptions.CacheEntryNotFoundException;

import java.util.HashMap;

/**
 *  Least Recent Cache is supposed to store items that were least recently added/accessed
 *
 * @param capacity
 */
public class LRUCache {
    private final int capacity;
    private int count;
    HashMap<Integer, Node> map;
    LinkedList list;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.count = 0;
        this.map = new HashMap<>();
        this.list = new LinkedList();
    }

    public Integer get(int key) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            map.replace(key, list.moveToHead(node));
            return node.value;
        } else {
            throw new CacheEntryNotFoundException("Cache entry not available");
        }
    }

    public void put(int key, int value) {
        if(map.containsKey(key)) {
            map.put(key, list.moveToHead(map.get(key)));
            return;
        }
        Node node = new Node(key, value);
        if(count < capacity){
            node = list.addToHead(node);
            map.put(key, node);
            count++;
        } else {
            map.remove(list.getHead().key);
            node = list.replaceHead(node);
            map.put(key, node);
        }
    }

    // Doubly Linked List
    private class LinkedList {
        // Dummy head and tail for simplicity of addition/removal
        private Node dummyHead;
        private Node dummyTail;
        private int length = 0;

        public LinkedList(){
            /* head and tail are always basic 0s so that we don't have
            to cover head and tail edge cases */
            dummyHead = new Node(0,0);
            dummyTail = new Node(0,0);
            dummyHead.next = dummyTail;
            dummyTail.last = dummyHead;
        }

        private boolean listIsEmpty(){
            return dummyHead.next.isEqualTo(dummyTail);
        }

        // Returns the actual head of the list or the dummy head if it doesn't exist
        private Node getHead(){
            return (listIsEmpty()) ? null : dummyHead.next;
        }

        private Node replaceHead(Node node){
            if (listIsEmpty()){
                return addToHead(node);
            }
            getHead().key = node.key;
            getHead().value = node.value;
            return getHead();
        }

        // Adds a non-existing Node to the head
        private Node addToHead(Node node){
            if (listIsEmpty()){
                dummyHead.next = node;
                dummyTail.last = node;
                node.last = dummyHead;
                node.next = dummyTail;
            } else {
                node.next = getHead();
                node.next.last = node;
                node.last = dummyHead;
                dummyHead.next = node;
            }
            return node;
        }

        // This is done to remove most recent cache entry
        private Node moveToHead(Node node){
            if(listIsEmpty()){
                return addToHead(node);
            }
            remove(node);
            node.last = getHead().last;
            node.next = getHead().next;
            getHead().last.next = node;
            getHead().next.last  = node;
            return node;
        }

        // assumes that the Node exists in the list
        private void remove(Node node){
            node.next.last = node.last;
            node.last.next = node.next;
        }
    }

    private class Node {
        private int key;
        private int value;
        private Node next;
        private Node last;

        private Node(int key, int value){
            this.key = key;
            this.value = value;
            next = null;
            last = null;
        }

        private boolean isEqualTo(Node node){
            return node.key == this.key && node.value == this.value;

        }
    }
}