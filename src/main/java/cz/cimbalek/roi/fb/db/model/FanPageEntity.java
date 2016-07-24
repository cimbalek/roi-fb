package cz.cimbalek.roi.fb.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * @author cimbalek
 */
@Entity
@Table(name = "fb_page")
public class FanPageEntity implements Serializable {

    private static final long serialVersionUID = -8106304385629888545L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "profile_pic_url", length = 255)
    private String profilePicUrl;

    @JsonIgnore
    @JoinTable(name = "jt_fb_user_page", joinColumns = @JoinColumn(name = "page_id", referencedColumnName = "id", table = "fb_page", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", table = "fb_user", nullable = false)
    )
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = UserEntity.class)
    private List<UserEntity> users;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public List<UserEntity> getUsers() {
        if (users == null) {
            users = new ArrayList<>();
        }
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.description);
        hash = 89 * hash + Objects.hashCode(this.profilePicUrl);
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
        final FanPageEntity other = (FanPageEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.profilePicUrl, other.profilePicUrl)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FunPage{" + "id=" + id + ", name=" + name + ", description=" + description + ", profilePicUrl=" + profilePicUrl + '}';
    }

}
