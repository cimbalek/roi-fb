package cz.cimbalek.roi.fb.db.manager;

import cz.cimbalek.roi.fb.db.model.FanPageEntity;
import cz.cimbalek.roi.fb.db.model.UserEntity;
import cz.cimbalek.roi.fb.db.repo.UserRepo;
import cz.cimbalek.roi.fb.rest.model.Like;
import cz.cimbalek.roi.fb.rest.model.Likes;
import cz.cimbalek.roi.fb.rest.model.User;
import java.util.ArrayList;
import java.util.List;
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
    
    public void persist(User user) {
        
        UserEntity userE = convertUser(user);
        
        userRepo.saveAndFlush(userE);
    }
    
    private UserEntity convertUser(User user) {
        UserEntity userE = new UserEntity();
        
        userE.setGender(user.getGender());
        userE.setId(user.getId());
        userE.setName(user.getName());
        userE.setProfilePicUrl(user.getPicture().getData().getUrl());
        
        userE.setPages(convertPages(user.getLikes()));
        
        return userE;
        
    }

    private List<FanPageEntity> convertPages(Likes likes) {
        
        List<FanPageEntity> pages;
        
        if ((likes == null) || (likes.getData() == null) || (likes.getData().isEmpty())) {
            return null;
        }
        
        pages = new ArrayList<>();
        
        for (Like like : likes.getData()) {
            FanPageEntity page = new FanPageEntity();
            
            page.setId(like.getId());
            page.setDescription(like.getAbout());
            page.setName(like.getName());
            page.setProfilePicUrl(like.getPicture().getData().getUrl());
            
            pages.add(page);
        }
        
        return pages;
    }

}
