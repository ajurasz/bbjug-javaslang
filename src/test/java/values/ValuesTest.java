package values;

import javaslang.Function0;
import javaslang.Lazy;
import javaslang.concurrent.Future;
import javaslang.control.Either;
import javaslang.control.Option;
import javaslang.control.Try;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValuesTest {

    @Test
    public void option1() {
        Option<Integer> op1 = Option.of(1);     // Some(1)
        Option<Integer> op2 = Option.of(null);  // None

        Option<Integer> op3 = Option.some(2);   // Some(2)
        Option<Integer> op4 = Option.none();    // None

        assertThat(op1).isInstanceOf(Option.Some.class);
        assertThat(op2).isInstanceOf(Option.None.class);
    }

    @Test
    public void try1() {
        String result = Try.of(() -> "Try this function")
                           .getOrElseGet(throwable -> "Ups");

        assertThat(result).isEqualTo("Try this function");
    }

    @Test
    public void try2() {
        String result = Try.of(Values::throwException)
                           .recover(throwable -> "Recovered")
                           .getOrElseGet(throwable -> "Ups");

        assertThat(result).isEqualTo("Recovered");
    }

    @Test
    public void lazy() {
        Lazy<Double> lazy = Lazy.of(Math::random);

        assertThat(lazy.isEvaluated()).isFalse();

        lazy.get();

        assertThat(lazy.isEvaluated()).isTrue();
        assertThat(lazy.get()).isEqualTo(lazy.get());
    }

    @Test
    public void realLazy() {
        CharSequence lazy = Lazy.val(() -> {
            System.out.println("In lazy");
            return "Real Lazy";
        }, CharSequence.class);

        System.out.println("Executing lazy");
        assertThat(lazy).isEqualTo("Real Lazy");
    }

    @Test
    public void customRealLazy() {
        MyInterface lazy = Lazy.val(() -> {
                    MyInterface dtoInterface = () -> new DtoObject(1, 2);
                    return dtoInterface;
                }, MyInterface.class);

        System.out.println("Executing lazy");
        assertThat(lazy.compute()).isEqualTo(new DtoObject(1, 2));
    }

    @Test
    public void either1() {
        Function0<Either<String,Integer>> compute = Function0.of(Values::computeL);

        Either<String,Integer> value = compute.apply().right().map(i -> i * 2).toEither();

        assertThat(value).isInstanceOf(Either.Left.class);
        assertThat(value.getLeft()).isEqualTo("Error");
    }

    @Test
    public void either2() {
        Function0<Either<String,Integer>> compute = Function0.of(Values::computeR);

        Either<String,Integer> value = compute.apply().right().map(i -> i * 2).toEither();

        assertThat(value).isInstanceOf(Either.Right.class);
        assertThat(value.get()).isEqualTo(2);
    }

    @Test
    public void future1() {
        Future<Integer> future = Future.of(() -> {
            Thread.sleep(1000L);
            return 10;
        });

        System.out.println("First");
        future.onSuccess(System.out::println);
        future.await();
    }

}
