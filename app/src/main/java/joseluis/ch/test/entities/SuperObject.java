package joseluis.ch.test.entities;

import java.io.Serializable;

public class SuperObject implements Serializable {

    private String id;
    private long fecha;
    private boolean selected;

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
