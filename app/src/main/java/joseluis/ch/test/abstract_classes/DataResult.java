package joseluis.ch.test.abstract_classes;

public class DataResult<O, E> {

    private O data ;
    private E exception;

    public O getData() {
        return data;
    }

    public void setData(O data) {
        this.data = data;
    }

    public E getException() {
        return exception;
    }

    public void setException(E exception) {
        this.exception = exception;
    }

}
