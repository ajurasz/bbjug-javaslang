package tuple;

import javaslang.Function1;
import javaslang.Tuple;
import javaslang.Tuple3;
import javaslang.collection.List;
import javaslang.collection.Seq;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TupleTest {

    @Test
    public void shouldCreateTuple() {
        Tuple3<String, Long, Double> tuple3 = Tuple.of("String", 10L, 5.5);

        assertThat(tuple3._1).isEqualTo("String");
        assertThat(tuple3._2()).isEqualTo(10L);
    }

    @Test
    public void shouldMapTuple() {
        Tuple3<String, Long, Double> tuple3 = Tuple.of("String", 10L, 5.5);

        Tuple3<String, Long, Double> newTuple3 = tuple3.map((first, second, third) ->
                Tuple.of(first.toUpperCase(), second * 10, third - 0.5)
        );

        assertThat(newTuple3._1).isEqualTo("STRING");
        assertThat(newTuple3._2).isEqualTo(100L);
        assertThat(newTuple3._3).isEqualTo(5.0);

        assertThat(tuple3._1).isEqualTo("String");
        assertThat(tuple3._2).isEqualTo(10L);
        assertThat(tuple3._3).isEqualTo(5.5);
    }

    @Test
    public void shouldMapTupleSingleProperty() {
        Tuple3<String, Long, Double> tuple3 = Tuple.of("String", 10L, 5.5);

        Tuple3<String, Long, Double> newTuple3 = tuple3.map2(second -> 0L);

        assertThat(newTuple3._1).isEqualTo("String");
        assertThat(newTuple3._2).isEqualTo(0L);
        assertThat(newTuple3._3).isEqualTo(5.5);
    }

    @Test
    public void shouldMapUsingFunctionReference() {
        Tuple3<String, Long, Double> tuple3 = Tuple.of("String", 10L, 5.5);
        Function1<String, String> mapFirstFunc = String::toLowerCase;
        Function1<Long, Long> mapSecondFunc = l -> l + 1;
        Function1<Double, Double> mapThirdFunc = d -> 0.0;


        Tuple3<String, Long, Double> newTuple3 = tuple3.map(mapFirstFunc, mapSecondFunc, mapThirdFunc);

        assertThat(newTuple3._1).isEqualTo("string");
        assertThat(newTuple3._2).isEqualTo(11L);
        assertThat(newTuple3._3).isEqualTo(0.0);
    }

    @Test
    public void shouldTransformTuple() {
        Tuple3<String, Long, Double> tuple3 = Tuple.of("String", 10L, 5.5);

        String transformed = tuple3.transform((first, second, third) -> first + second + third);

        assertThat(transformed).isEqualTo("String105.5");
    }

    @Test
    public void arityTest() {
        Tuple3<String, Long, Double> tuple3 = Tuple.of("String", 10L, 5.5);

        assertThat(tuple3.arity()).isEqualTo(3);
    }

    @Test
    public void tupleToList() {
        Tuple3<String, Long, Double> tuple3 = Tuple.of("String", 10L, 5.5);
        Seq seq = tuple3.toSeq();

        assertThat(seq).isEqualTo(List.of("String", 10L, 5.5));
    }
}
