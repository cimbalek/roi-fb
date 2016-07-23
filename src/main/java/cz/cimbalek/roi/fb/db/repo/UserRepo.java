package cz.cimbalek.roi.fb.db.repo;

import cz.cimbalek.roi.fb.db.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author cimbalek
 */
public interface UserRepo extends JpaRepository<UserEntity, String> {

}
