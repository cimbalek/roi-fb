package cz.cimbalek.roi.fb.rest.model;

import java.util.Objects;

/**
 *
 * @author cimbalek
 */
public class UserDataRequest {

    private String userId;
    private String accessToken;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.userId);
        hash = 61 * hash + Objects.hashCode(this.accessToken);
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
        final UserDataRequest other = (UserDataRequest) obj;
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        if (!Objects.equals(this.accessToken, other.accessToken)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserDataRequest{" + "userId=" + userId + ", accessToken=" + accessToken + '}';
    }

}
