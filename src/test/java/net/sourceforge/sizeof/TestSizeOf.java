package net.sourceforge.sizeof;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static junit.framework.Assert.assertEquals;
import static net.sourceforge.sizeof.SizeOf.*;

/**
 * A simple class to test SizeOf behavior
 */
public class TestSizeOf {
    private static final int EMPTY_ARRAY_SIZE = 24;

    // Allows this to be run as a unit test e.g., via mvn test
    @Test
    public void testSizeOf() throws Exception {
        main(new String[]{ });
    }

    @Test
    public void testPrimitives()
    {
        assertEquals(16, SizeOf.sizeOf(new Object()));
        assertEquals(16, SizeOf.deepSizeOf(new Object()));

        assertEquals(24, SizeOf.sizeOf(new Integer(0)));
        assertEquals(24, SizeOf.deepSizeOf(new Integer(0)));

        assertEquals(40, SizeOf.sizeOf(""));
        assertEquals(40 + EMPTY_ARRAY_SIZE, SizeOf.deepSizeOf(""));
        assertEquals(40, SizeOf.sizeOf("a"));
        assertEquals(40 + EMPTY_ARRAY_SIZE + 8, SizeOf.deepSizeOf("a"));

        assertEquals(EMPTY_ARRAY_SIZE, SizeOf.sizeOf(new Object[0]));
        Object[] objects = new Object[100];
        assertEquals(EMPTY_ARRAY_SIZE + 8 * 100, SizeOf.sizeOf(objects));
        assertEquals(EMPTY_ARRAY_SIZE + 8 * 100, SizeOf.deepSizeOf(objects));
        for(int i = 0; i < objects.length; i++) {
            objects[i] = new Object();
        }
        assertEquals(EMPTY_ARRAY_SIZE + 8 * 100 + 16 * 100, SizeOf.deepSizeOf(objects));
    }

    @Test
    public void testCycle()
    {
        Dummy dummy = new Dummy();
        assertEquals(32, sizeOf(dummy));
        assertEquals(32, deepSizeOf(dummy));
        dummy.dummy = dummy;
        assertEquals(32, deepSizeOf(dummy));
    }

    public static void main(String[] args) throws FileNotFoundException {
        //SizeOf.turnOnDebug();
        SizeOf.skipFlyweightObject(true);
        //SizeOf.setMinSizeToLog(10);
        SizeOf.setLogOutputStream(new FileOutputStream("log.txt"));

        Integer x = 1;
        int _int = 0;
        long _long = 0;
        String s;

        ArrayList<Integer> aList = new ArrayList<Integer>();

        //print("int: \t\t", iterativeSizeOf(_int));
        print("int: \t\t", sizeOf(_int));
        print("long: \t\t", sizeOf(_long));
        print("char: \t\t", sizeOf('a'));
        print("double: \t\t", deepSizeOf(0.3D));
        print("boolean: \t\t", sizeOf(true));
        print("Integer: \t\t", sizeOf(Integer.valueOf(2)));

        print("empty string: \t", sizeOf(""));
        print("not empty string: \t", sizeOf("aaaa"));
        print("not empty string: \t", sizeOf("aaaaaaaa"));
        //print("empty string: \t", iterativeSizeOf(""));
        //print("empty array: \t", sizeOf(aList));
        print("empty list: \t", sizeOf(aList));

        for(int i = 1; i < 10; ++i)
            aList.add(i);

        //print("10 list: \t", sizeOf(aList));
        print("10 list: \t", sizeOf(aList));
        for(int i = 11; i < 20; ++i)
            aList.add(i);
        print("20 list no static: \t", sizeOf(aList));
    }

    public static void print(String msg, long n) {
        SizeOf.print(msg + humanReadable(n) + "\n");
    }

    public static void heinz() throws IllegalArgumentException, IllegalAccessException, IOException {
        measureSize(new Object());
        measureSize(new HashMap());
        measureSize(new LinkedHashMap());
        measureSize(new ReentrantReadWriteLock());
        measureSize(new byte[1000]);
        measureSize(new boolean[1000]);
        measureSize(new String("Hello World".toCharArray()));
        measureSize("Hello World");
        measureSize(10);
        measureSize(100);
        measureSize(1000);
        measureSize(new Parent());
        measureSize(new Kid());
        measureSize(Thread.State.TERMINATED);
        measureSize(Boolean.TRUE);
    }

    private static class Parent {
        private int i;
        private boolean b;
        private long l;
    }

    private static class Kid extends Parent {
        private boolean b;
        private float f;
    }

    private static void measureSize(Object object) {
        print(object.getClass().getSimpleName(), deepSizeOf(object));
    }
}
