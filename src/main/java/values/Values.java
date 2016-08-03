package values;

import javaslang.control.Either;

public class Values {
    public static String throwException() {
        throw new RuntimeException("Exception");
    }

    public static Either<String,Integer> computeL() {
        return Either.left("Error");
    }

    public static Either<String,Integer> computeR() {
        return Either.right(1);
    }
}
