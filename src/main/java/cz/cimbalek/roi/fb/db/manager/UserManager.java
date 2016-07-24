package cz.cimbalek.roi.fb.db.manager;

import cz.cimbalek.roi.fb.db.model.UserEntity;
import cz.cimbalek.roi.fb.db.repo.UserRepo;
import cz.cimbalek.roi.fb.rest.model.User;
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
    /**
     * Persists user and his/her likes into DB. First User JSON is converted to entity and persisted to the DB without any likes. Likes are afterwards checked
     * if they exist already in the DB, then converted/updated and finally all of them are persisted to the DB and paired with saved UserEnity.
     *
     * @param user user whose data shall be saved.
     */
    @Transactional
    public void persist(User user) {

        UserEntity userE = convertUser(user);
        userE = userRepo.saveAndFlush(userE);

        fanPageManager.persistPagesForUser(user.getLikes(), userE);

    }

    /**
     * Deletes user from DB and then deletes pages which are not liked by anyone anymore.
     *
     * @param userId id of user to be deleted.
     */
    @Transactional
    public void delete(String userId) {
        userRepo.delete(userId);
        fanPageManager.deletePagesWithNoLikes();
    }

    /**
     * Retrieves user data from DB. No likes are fetched in this request.
     *
     * @param id Id of user whose data to be retrieved.
     * @return requested User or null if there is no user with such ID.
     */
    public UserEntity getUser(String id) {
        return userRepo.findOne(id);
    }

    /**
     * Retrieves user data from DB. Likes are fetched in this request.
     *
     * @param id Id of user whose data to be retrieved.
     * @return requested User or null if there is no user with such ID.
     */
    public UserEntity getUserWithLikes(String id) {
        return userRepo.findOneFetchLikes(id);
    }

    /* *************** */
    /* PRIVATE METHODS */
    /* *************** */
    /**
     * Converts User represented by JSON structure received from Facebook into entity saveable to the DB.
     *
     * @param user User JSON structure to be converted.
     * @return UserEntity containing user data.
     */
    private UserEntity convertUser(User user) {
        UserEntity userE = new UserEntity();

        userE.setGender(user.getGender());
        userE.setId(user.getId());
        userE.setName(user.getName());
        userE.setProfilePicUrl(user.getPicture().getData().getUrl());

        return userE;

    }

}
