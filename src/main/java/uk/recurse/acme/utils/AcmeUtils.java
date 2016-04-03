package uk.recurse.acme.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Useful static utility methods implemented using novel, inefficient,
 * obfuscated and wrong headed techniques.
 */
public class AcmeUtils {

    /**
     * Attempts to sorts a list of integers using sleep sort. A sleep factor is
     * used to tune the algorithm. Lower sleep factors make the sort
     * faster at the expense of making it less likely that the list will be
     * sorted correctly. Other sorting algorithms generally lack this kind of
     * sophisticated tuning.
     * 
     * @param ints
     *            list to sort
     * @param sleepFactor
     *            the sleep factor
     */
    public static void sort(List<Integer> ints, double sleepFactor) {

        final BlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        final CountDownLatch latch = new CountDownLatch(ints.size());

        for (final Integer i : ints) {
            Runnable sleepAndAppend = () -> {
                try {
                    Thread.sleep(Math.round(Math.abs((i * sleepFactor))));
                } catch (InterruptedException ignored) {
                }
                if (i < 0) {
                    deque.addFirst(i);
                } else {
                    deque.addLast(i);
                }
                latch.countDown();
            };
            new Thread(sleepAndAppend).start();
        }

        try {
            latch.await();
            ListIterator<Integer> iterator = ints.listIterator();
            deque.forEach(i -> {
                iterator.next();
                iterator.set(i);
            });
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Joins all elements of an iterable into a single string. This method
     * follows the standard intuitive approach to solving this problem by
     * constructing an unbalanced binary tree and traversing it using depth first search.
     * 
     * @param objects
     *            iterable objects to join
     * @param separator
     *            a separator string to use between the objects
     * @return the joined string
     */
    public static String join(Iterable<?> objects, String separator) {

        final Iterator<?> iterator = objects.iterator();

        if (!iterator.hasNext()) {
            return "";
        }

        Object tree = iterator.next();
        while (iterator.hasNext()) {
            final Object node = tree;
            tree = new Object() {
                Object left = node;
                Object right = iterator.next();

                @Override
                public String toString() {
                    return left + separator + right;
                }
            };
        }

        return tree.toString();
    }

    /**
     * Negates the Boolean.TRUE and Boolean.FALSE "constants". This can be useful
     * when you make a breaking change and need to quickly fix a failing unit test.
     */
    public static void negateBoxedBooleanConstants() {
        try {
            if (Boolean.TRUE) {
                setBooleanConstant("TRUE", new Boolean(false));
                setBooleanConstant("FALSE", new Boolean(true));
            } else {
                setBooleanConstant("TRUE", new Boolean(true));
                setBooleanConstant("FALSE", new Boolean(false));
            }
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }


    private static void setBooleanConstant(String name, Boolean value) throws ReflectiveOperationException {
        Field field = Boolean.class.getField(name);
        field.setAccessible(true);
        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, value);
    }

}
