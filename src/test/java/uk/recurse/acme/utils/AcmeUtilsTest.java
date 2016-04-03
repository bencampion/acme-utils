package uk.recurse.acme.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class AcmeUtilsTest {

    @Test
    public void sort_unsortedList_mutatesToSortedList() {
        List<Integer> list = Arrays.asList(3, 5, -1, 2, 4, 0, -2, 1);
        AcmeUtils.sort(list, 10);
        assertThat(list, is(Arrays.asList(-2, -1, 0, 1, 2, 3, 4, 5)));
    }

    @Test
    public void sort_emptyList_doesNotMutateList() {
        List<Integer> list = Collections.emptyList();
        AcmeUtils.sort(list, 0);
        assertThat(list, is(sameInstance(list)));
    }

    @Test
    public void join_list_returnsStringJoinedWithSeparator() {
        String joined = AcmeUtils.join(Arrays.asList(1, 2, 3), ",");
        assertThat(joined, is("1,2,3"));
    }

    @Test
    public void join_singletonList_returnsElementAsString() {
        String joined = AcmeUtils.join(Collections.singleton("x"), ",");
        assertThat(joined, is("x"));
    }
    
    @Test
    public void join_emptyList_returnsEmptyString() {
        String joined = AcmeUtils.join(Collections.emptyList(), ",");
        assertThat(joined, is(""));
    }

    @Test
    public void negateBoxedBooleanConstants_makesTrueEqualFalse() {
        AcmeUtils.negateBoxedBooleanConstants();
        assertFalse(Boolean.TRUE);

        AcmeUtils.negateBoxedBooleanConstants();
        assertTrue(Boolean.TRUE);
    }

    @Test
    public void negateBoxedBooleanConstants_makesFalseEqualTrue() {
        AcmeUtils.negateBoxedBooleanConstants();
        assertTrue(Boolean.FALSE);

        AcmeUtils.negateBoxedBooleanConstants();
        assertFalse(Boolean.FALSE);
    }

}
