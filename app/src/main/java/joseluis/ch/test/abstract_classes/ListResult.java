package joseluis.ch.test.abstract_classes;

import java.util.List;

public class ListResult<T> {

    private DataResult<List<T>, Exception> result = new DataResult<>();

    public DataResult<List<T>, Exception> getResult() {
        return result;
    }

    public void setResult(DataResult<List<T>, Exception> result) {
        this.result = result;
    }

}
