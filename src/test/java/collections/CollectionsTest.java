package collections;

import javaslang.collection.List;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class CollectionsTest {

    @Test
    public void list1() {
        assertThat(Arrays.asList(1, 2, 3).stream().mapToInt(i -> i).sum()).isEqualTo(6);
        assertThat(List.of(1, 2, 3).sum().intValue()).isEqualTo(6);
    }

    @Test
    public void list2() {
        assertThat(List.of(1, 2, 3).head()).isEqualTo(1);
    }

    @Test
    public void list3() {
        assertThat(List.of(1, 2, 3).tail()).isEqualTo(List.of(2, 3));
    }

    @Test
    public void list4() {
        java.util.List java8 = Arrays.asList(1, 2, 3).stream()
                                                     .map(i -> i * 2)
                                                     .collect(toList());
        List javaslang = List.of(1, 2, 3).map(i -> i * 2);

        assertThat(java8).isEqualTo(Arrays.asList(2, 4, 6));
        assertThat(javaslang.toJavaList()).isEqualTo(Arrays.asList(2, 4, 6));
    }

    @Test
    public void list5() {
        String java8 = Arrays.asList("a", "b", "c").stream().collect(Collectors.joining(", "));
        String javaslang = List.of("a", "b", "c").mkString(", ");

        assertThat(java8).isEqualTo("a, b, c");
        assertThat(javaslang).isEqualTo("a, b, c");
    }
}
