package cz.cimbalek.roi.fb.db.manager;

import cz.cimbalek.roi.fb.db.model.FanPageEntity;
import cz.cimbalek.roi.fb.db.model.UserEntity;
import cz.cimbalek.roi.fb.db.repo.UserRepo;
import cz.cimbalek.roi.fb.rest.model.User;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author cimbalek
 */
@Component
public class UserManager {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FanPageManager fanPageManager;

    /* ************** */
    /* PUBLIC METGODS */
    /* ************** */
    @Transactional
    public void persist(User user) {

        UserEntity userE = convertUser(user);
        userE = userRepo.saveAndFlush(userE);
        
        fanPageManager.persistPagesForUser(user.getLikes(), userE);
        
    }

    @Transactional
    public void delete(String userId) {
        userRepo.delete(userId);
        fanPageManager.deletePagesWithNoLikes();
    }

    public UserEntity getUser(String id) {
        return userRepo.findOne(id);
    }

    public List<FanPageEntity> getUserLikes(String id) {
        return userRepo.findOneFetchLikes(id).getPages();
    }

    /* *************** */
    /* PRIVATE METGODS */
    /* *************** */
    private UserEntity convertUser(User user) {
        UserEntity userE = new UserEntity();

        userE.setGender(user.getGender());
        userE.setId(user.getId());
        userE.setName(user.getName());
        userE.setProfilePicUrl(user.getPicture().getData().getUrl());

        return userE;

    }

}
