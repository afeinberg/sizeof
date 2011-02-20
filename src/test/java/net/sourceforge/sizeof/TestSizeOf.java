package net.sourceforge.sizeof;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static junit.framework.Assert.assertEquals;
import static net.sourceforge.sizeof.SizeOf.*;

/**
 * A simple class to test SizeOf behavior
 */
public class TestSizeOf {
    private static final int EMPTY_ARRAY_SIZE = 24;

    @Test
    public void testPrimitives() {
        assertEquals(16, sizeOf(new Object()));
        assertEquals(16, deepSizeOf(new Object()));

        assertEquals(24, sizeOf(new Integer(0)));
        assertEquals(24, deepSizeOf(new Integer(0)));

        assertEquals(40, sizeOf(""));
        assertEquals(40 + EMPTY_ARRAY_SIZE, deepSizeOf(""));
        assertEquals(40, sizeOf("a"));
        assertEquals(40 + EMPTY_ARRAY_SIZE + 8, deepSizeOf("a"));

        assertEquals(EMPTY_ARRAY_SIZE, sizeOf(new Object[0]));
        Object[] objects = new Object[100];
        assertEquals(EMPTY_ARRAY_SIZE + 8 * 100, sizeOf(objects));
        assertEquals(EMPTY_ARRAY_SIZE + 8 * 100, deepSizeOf(objects));
        for(int i = 0; i < objects.length; i++) {
            objects[i] = new Object();
        }
        assertEquals(EMPTY_ARRAY_SIZE + 8 * 100 + 16 * 100, deepSizeOf(objects));
    }

    @Test
    public void testCycle() {
        Dummy dummy = new Dummy();
        assertEquals(32, sizeOf(dummy));
        assertEquals(32, deepSizeOf(dummy));
        dummy.dummy = dummy;
        assertEquals(32, deepSizeOf(dummy));
    }

    @Test
    public void testInheritance() {
        assertEquals(24, sizeOf(new Parent()));
        assertEquals(24, deepSizeOf(new Parent()));
        assertEquals(32, sizeOf(new Child()));
        assertEquals(32, deepSizeOf(new Child()));
    }

    @Test
    public void testCollections() {
        assertEquals(144, deepSizeOf(new ArrayList()));
        assertEquals(216, deepSizeOf(new HashMap()));
        assertEquals(296, deepSizeOf(new LinkedHashMap()));
        assertEquals(176, deepSizeOf(new ReentrantReadWriteLock()));
        assertEquals(192, deepSizeOf(new ConcurrentSkipListMap()));
    }

    @Test
    public void testFlyweights() {
        skipFlyweightObject(true);
        assertEquals(0, sizeOf(Thread.State.TERMINATED));
        assertEquals(0, sizeOf(Boolean.TRUE));
        assertEquals(0, sizeOf(Integer.valueOf(0)));
    }

    private static class Parent {
        private int i;
    }

    private static class Child extends Parent {
        private int j;
    }

    private static class Dummy {
        int i;
        Dummy dummy = null;
    }
}
