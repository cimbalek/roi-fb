package cz.cimbalek.roi.fb.db.repo;

import cz.cimbalek.roi.fb.db.model.FanPageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author cimbalek
 */
public interface FanPageRepo extends JpaRepository<FanPageEntity, String> {

    public void deleteByUsersIsNull();

}
