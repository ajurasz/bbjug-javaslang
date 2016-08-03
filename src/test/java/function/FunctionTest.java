package function;

import javaslang.CheckedFunction2;
import javaslang.Function0;
import javaslang.Function1;
import javaslang.Function2;
import javaslang.control.Option;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class FunctionTest {

    @Test
    public void java8function() {
        Function<Integer, Long> intToLong = Long::valueOf;

        assertThat(intToLong.apply(10)).isEqualTo(10L);
    }

    @Test
    public void java8bifunction() {
        BiFunction<Integer, Integer, Double> divide = (a, b) -> (double) a / b;

        assertThat(divide.apply(5, 2)).isEqualTo(2.5);
    }

    @Test
    public void java8trifunction() {
        Function<Integer, Function<Integer, Function<Integer, Integer>>> addTri = a -> (b -> (c -> a + b + c));

        assertThat(addTri.apply(1).apply(2).apply(3)).isEqualTo(6);
    }

    @Test
    public void checkedFunction() {
        CheckedFunction2<Integer, Integer, Integer> divide = (a, b) -> a / b;

        assertThat(catchThrowable(() -> divide.apply(3, 0))).isInstanceOf(ArithmeticException.class);
    }

    @Test
    public void functionFactoryMethod() {
        Function2<Integer, Integer, Integer> addMethodReference = Function2.of(Functions::add);

        assertThat(addMethodReference.apply(5, 5)).isEqualTo(10);
    }

    @Test
    public void functionAndThen() {
        Function1<Integer, Integer> plusOne = a -> a + 1;
        Function1<Integer, Integer> multiplyByTwo = a -> a * 2;

        Function1<Integer, Integer> add1AndMultiplyBy2 = plusOne.andThen(multiplyByTwo);

        assertThat(add1AndMultiplyBy2.apply(1)).isEqualTo(4);
    }

    @Test
    public void functionCompose() {
        Function1<Integer, Integer> plusOne = a -> a + 1;
        Function1<Integer, Integer> multiplyByTwo = a -> a * 2;

        Function1<Integer, Integer> add1AndMultiplyBy2 = multiplyByTwo.compose(plusOne);

        assertThat(add1AndMultiplyBy2.apply(1)).isEqualTo(4);
    }

    @Test
    public void functionLifting() {
        Function2<Integer, Integer, Integer> divide = (a, b) -> a / b;
        Function2<Integer, Integer, Option<Integer>> safeDivide = Function2.lift(divide);

        Option<Integer> i1 = safeDivide.apply(1, 0);
        Option<Integer> i2 = safeDivide.apply(4, 2);

        assertThat(i1.isEmpty()).isEqualTo(true);
        assertThat(i1).isEqualTo(Option.none());

        assertThat(i2.isEmpty()).isEqualTo(false);
        assertThat(i2).isEqualTo(Option.of(2));
        assertThat(i2.get()).isEqualTo(2);
    }

    @Test
    public void functionCurring() {
        Function2<Integer, Integer, Integer> func = (a, b) -> (2 * a) + b;
        Function1<Integer, Integer> func2 = func.curried().apply(2);

        assertThat(func2.apply(4)).isEqualTo(8);
    }

    @Test
    public void functionMemotization() {
        Function0<Double> hashCache =
                Function0.of(Math::random).memoized();

        double randomValue1 = hashCache.apply();
        double randomValue2 = hashCache.apply();

        assertThat(randomValue1).isEqualTo(randomValue2);
    }
}
