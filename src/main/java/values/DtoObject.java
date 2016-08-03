package values;


public class DtoObject {
    private final Integer x;
    private final Integer y;

    public DtoObject(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DtoObject dtoObject = (DtoObject) o;

        if (x != null ? !x.equals(dtoObject.x) : dtoObject.x != null) return false;
        return y != null ? y.equals(dtoObject.y) : dtoObject.y == null;

    }

    @Override
    public int hashCode() {
        int result = x != null ? x.hashCode() : 0;
        result = 31 * result + (y != null ? y.hashCode() : 0);
        return result;
    }
}
