package cz.cimbalek.roi.fb.rest.model;

import java.util.Objects;

/**
 *
 * @author cimbalek
 */
public class Picture {

    private PictureData data;

    public PictureData getData() {
        return data;
    }

    public void setData(PictureData data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.data);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Picture other = (Picture) obj;
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Picture{" + "data=" + data + '}';
    }

}
