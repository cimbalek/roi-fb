package cz.cimbalek.roi.fb.rest.model;

import java.util.Objects;

/**
 *
 * @author cimbalek
 */
class Cursors {

    private String before;
    private String after;

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.before);
        hash = 89 * hash + Objects.hashCode(this.after);
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
        final Cursors other = (Cursors) obj;
        if (!Objects.equals(this.before, other.before)) {
            return false;
        }
        if (!Objects.equals(this.after, other.after)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cursors{" + "before=" + before + ", after=" + after + '}';
    }

}
