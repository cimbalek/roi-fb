package cz.cimbalek.roi.fb.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author cimbalek
 */
@Entity
@Table(name = "fb_user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 8690471612862744963L;

    @Id
    @Column(name = "id", unique = true, nullable = false, length = 50)
    private String id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "profile_pic_url", length = 255)
    private String profilePicUrl;

    @JsonIgnore
    @JoinTable(name = "jt_fb_user_page", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", table = "fb_user", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "page_id", referencedColumnName = "id", table = "fb_page", nullable = false)
    )
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = FanPageEntity.class)
    private List<FanPageEntity> pages;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public List<FanPageEntity> getPages() {
        return pages;
    }

    public void setPages(List<FanPageEntity> pages) {
        this.pages = pages;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.gender);
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
        final UserEntity other = (UserEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.gender, other.gender)) {
            return false;
        }
        if (!Objects.equals(this.profilePicUrl, other.profilePicUrl)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", gender=" + gender + ", profilePicUrl=" + profilePicUrl + ", pages=" + pages + '}';
    }

}
