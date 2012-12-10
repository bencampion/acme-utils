package com.github.acme.utils;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class AcmeUtilsTest {

    @Test
    public void testSort() {
        List<Integer> a = Arrays.asList(3, 5, -1, 2, 4, 0, -2, 1);
        AcmeUtils.sort(a, 5d);
        assertEquals(a, Arrays.asList(-2, -1, 0, 1, 2, 3, 4, 5));
        
        List<Integer> b = Collections.emptyList();
        AcmeUtils.sort(b, 0d);
        assertEquals(b, Collections.emptyList());
    }
    
    @Test
    public void testJoin() {
        assertEquals(AcmeUtils.join(Collections.emptyList(), ","), "");
        assertEquals(AcmeUtils.join(Collections.singleton("x"), ","), "x");
        assertEquals(AcmeUtils.join(Arrays.asList(1, 2, 3), ","), "1,2,3");
    }

}
