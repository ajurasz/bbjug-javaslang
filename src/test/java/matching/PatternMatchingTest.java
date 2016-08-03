package matching;

import javaslang.Patterns;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.control.Option;
import javaslang.control.Try;
import org.junit.Test;
import values.Values;

import static javaslang.API.*;
import static javaslang.Patterns.Failure;
import static javaslang.Patterns.Success;
import static javaslang.Predicates.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PatternMatchingTest {

    @Test
    public void simplePatternMatching() {
        Integer i = 1;
        String s = Match(i).of(
            Case($(1), "one"),
            Case($(2), "two"),
            Case($(), "?")
        );

        assertThat(s).isEqualTo("one");
    }

    @Test
    public void simplePatternMatching2() {
        String s = Match(10).of(
                Case(x -> x % 2 == 0, "Even"),
                Case(x -> x % 2 == 1, "Odd"),
                Case($(), "?"));

        assertThat(s).isEqualTo("Even");
    }

    @Test
    public void optionPatternMatching() {
        Integer i = 3;
        Option<String> s = Match(i).option(
                Case($(1), "one"),
                Case($(2), "two"),
                Case($(), "?")
        );

        assertThat(s).isEqualTo(Option.some("?"));
    }

    @Test
    public void tryPatternMatching() {
        Try<String> _try = Try.of(Values::throwException);
        String s = Match(_try).of(
                Case(Success($()), "success"),
                Case(Failure($()), "failure")
        );

        assertThat(s).isEqualTo("failure");
    }

    @Test
    public void tuplePatternMatching1() {
        Tuple2<Integer, Integer> tuple = Tuple.of(10, 20);
        String s = Match(tuple).of(
                Case(Patterns.Tuple2($(x -> x == 10), $()), "_1 == 10"),
                Case($(), "?")
        );

        assertThat(s).isEqualTo("_1 == 10");
    }

    @Test
    public void tuplePatternMatching2() {
        Tuple2<Integer, Integer> tuple = Tuple.of(10, 20);
        String s = Match(tuple).of(
                Case(Patterns.Tuple2($(x -> x == 10), $(x -> x == 20)), "_1 == 10 and _2 == 20"),
                Case($(), "?")
        );

        assertThat(s).isEqualTo("_1 == 10 and _2 == 20");
    }

    @Test
    public void tuplePatternMatching3() {
        Tuple2<Integer, Integer> tuple = Tuple.of(20, 20);
        String s = Match(tuple).of(
                Case(Patterns.Tuple2($(x -> x == 10), $(x -> x == 20)), "_1 == 10 and _2 == 20"),
                Case($(), "?")
        );

        assertThat(s).isEqualTo("?");
    }

    @Test
    //http://koziolekweb.pl/2016/06/18/pattern-matching-w-javie-z-javaslang-ii/
    public void predicatesPatternMatching() {
        Object i = 4;
        String s = Match(i).of(
                Case(is(1), "Jeden"),
                Case(isIn(2, 3), "Dwa albo 3"),
                Case(anyOf(is(4), noneOf(is(5), is(6))), "4 lub nie (5 lub 6)"),
                Case(instanceOf(String.class), "Jakiś napis"),
                Case($(), "?")
        );

        assertThat(s).isEqualTo("4 lub nie (5 lub 6)");
    }
}
