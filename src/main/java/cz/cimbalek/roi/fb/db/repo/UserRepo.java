/* (c) GreeNova services 2015 */
package cz.cimbalek.roi.fb.db.repo;

import cz.cimbalek.roi.fb.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author cimbalek
 */
public interface UserRepo extends JpaRepository<User, String>{
    
}
