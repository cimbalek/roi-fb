package cz.cimbalek.roi.fb.rest.model;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author cimbalek
 */
public class Likes {

    private List<Like> data;
    private Paging paging;

    public List<Like> getData() {
        return data;
    }

    public void setData(List<Like> data) {
        this.data = data;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.data);
        hash = 17 * hash + Objects.hashCode(this.paging);
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
        final Likes other = (Likes) obj;
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (!Objects.equals(this.paging, other.paging)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Likes{" + "data=" + data + ", paging=" + paging + '}';
    }

}
