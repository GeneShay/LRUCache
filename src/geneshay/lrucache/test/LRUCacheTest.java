package test;

import geneshay.lrucache.main.LRUCache;
import org.junit.Test;

public class LRUCacheTest {

    @Test
    public void TestLRULimit(){
        LRUCache lruCache = new LRUCache(5);
        for(int i = 1; i <= 10; i++){
            lruCache.put(i, i * 10 + 1);
        }

        System.out.println(lruCache.get(1));



    }
}
