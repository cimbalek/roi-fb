package cz.cimbalek.roi.fb.db.repo;

import cz.cimbalek.roi.fb.db.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author cimbalek
 */
public interface UserRepo extends JpaRepository<UserEntity, String> {

    @Query("from UserEntity u JOIN FETCH u.pages l where u.id = ?1")
    public UserEntity findOneFetchLikes(String id);

}
