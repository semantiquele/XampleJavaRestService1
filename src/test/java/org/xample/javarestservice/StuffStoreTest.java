package org.xample.javarestservice;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StuffStoreTest {

    private final String key1 = "stuff1";
    private final String key2 = "stuff2";

    private final String data1 = "data1";
    private final String data2 = "data2";

    private StuffStore store1 = null;

    @BeforeEach
    public void setUp() {
        store1 = new StuffStore();
    }

    @Test
    public void givenEmptyStoreWhenIsEmptyThenTrue() {
        assertTrue(store1.isEmpty());
    }

    @Test
    public void givenEmptyStoreWhenGetCountThenZero() {
        assertEquals(0, store1.getCount());
    }

    @Test
    public void givenEmptyStoreWhenLookupStuffThenMiss() {
        assertFalse(store1.lookupStuff(key1).isPresent());
    }

    @Test
    public void givenEmptyStoreWhenRemoveStuffThenFalse() {
        assertFalse(store1.removeStuff(key1));
    }

    @Test
    public void givenEmptyStoreWhenUpdateStuffThenFalse() {
        assertFalse(store1.updateStuff(new StuffModel(key1, data1)));
        assertTrue(store1.isEmpty());
    }

    @Test
    public void givenEmptyStoreWhenStoreStuffThenTrue() {
        assertTrue(store1.storeStuff(new StuffModel(key1, data1)));
        assertFalse(store1.isEmpty());
    }

    @Test
    public void givenSingletonStoreWhenIsEmptyThenFalse() {
        store1.storeStuff(new StuffModel(key1, data1));
        assertFalse(store1.isEmpty());
    }

    @Test
    public void givenSingletonStoreWhenGetCountThenOne() {
        store1.storeStuff(new StuffModel(key1, data1));
        assertEquals(1, store1.getCount());
    }

    @Test
    public void givenSingletonStoreWhenLookupNonexistingStuffThenMiss() {
        store1.storeStuff(new StuffModel(key1, data1));
        assertFalse(store1.lookupStuff(key2).isPresent());
    }

    @Test
    public void givenSingletonStoreWhenLookupExistingStuffThenHit() {
        store1.storeStuff(new StuffModel(key1, data1));
        var actual = store1.lookupStuff(key1);
        assertTrue(actual.isPresent());
        assertEquals(key1, actual.get().getKey());
        assertEquals(data1, actual.get().getData());
    }

    @Test
    public void givenSingletonStoreWhenRemoveNonexistingStuffThenFalse() {
        store1.storeStuff(new StuffModel(key1, data1));
        assertFalse(store1.removeStuff(key2));
        assertEquals(1, store1.getCount());
    }

    @Test
    public void givenSingletonStoreWhenRemoveExistingStuffThenTrue() {
        store1.storeStuff(new StuffModel(key1, data1));
        assertTrue(store1.removeStuff(key1));
        assertTrue(store1.isEmpty());
    }

    @Test
    public void givenSingletonStoreWhenUpdateNonexistingStuffThenFalse() {
        store1.storeStuff(new StuffModel(key1, data1));
        assertFalse(store1.updateStuff(new StuffModel(key2, data2)));
        assertEquals(1, store1.getCount());
        assertEquals(key1, store1.lookupStuff(key1).get().getKey());
        assertEquals(data1, store1.lookupStuff(key1).get().getData());
    }

    @Test
    public void givenSingletonStoreWhenUpdateExistingStuffThenTrue() {
        store1.storeStuff(new StuffModel(key1, data1));
        assertTrue(store1.updateStuff(new StuffModel(key1, data2)));
        assertEquals(1, store1.getCount());
        assertEquals(key1, store1.lookupStuff(key1).get().getKey());
        assertEquals(data2, store1.lookupStuff(key1).get().getData());

    }

    @Test
    public void givenSingletonStoreWhenStoreNonexistingStuffThenTrue() {
        store1.storeStuff(new StuffModel(key1, data1));
        assertTrue(store1.storeStuff(new StuffModel(key2, data2)));
        assertEquals(2, store1.getCount());
        assertEquals(key1, store1.lookupStuff(key1).get().getKey());
        assertEquals(data1, store1.lookupStuff(key1).get().getData());
        assertEquals(key2, store1.lookupStuff(key2).get().getKey());
        assertEquals(data2, store1.lookupStuff(key2).get().getData());
    }

    @Test
    public void givenSingletonStoreWhenStoreExistingStuffThenFalse() {
        store1.storeStuff(new StuffModel(key1, data1));
        assertFalse(store1.storeStuff(new StuffModel(key1, data2)));
        assertEquals(1, store1.getCount());
        assertEquals(key1, store1.lookupStuff(key1).get().getKey());
        assertEquals(data1, store1.lookupStuff(key1).get().getData());
    }

}
