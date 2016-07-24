package cz.cimbalek.roi.fb.db.manager;

import cz.cimbalek.roi.fb.db.model.FanPageEntity;
import cz.cimbalek.roi.fb.db.model.UserEntity;
import cz.cimbalek.roi.fb.db.repo.FanPageRepo;
import cz.cimbalek.roi.fb.rest.model.Like;
import cz.cimbalek.roi.fb.rest.model.Likes;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author cimbalek
 */
@Component
class FanPageManager {

    @Autowired
    private FanPageRepo fanPageRepo;

    public void deletePagesWithNoLikes() {
        fanPageRepo.deleteByUsersIsNull();
    }

    public void persistPagesForUser(Likes likes, UserEntity userE) {
        List<FanPageEntity> pages = convertPages(likes, userE);
        
        fanPageRepo.save(pages);
        fanPageRepo.flush();
    }
    
    private List<FanPageEntity> convertPages(Likes likes, UserEntity userE) {

        List<FanPageEntity> pages;

        if ((likes == null) || (likes.getData() == null) || (likes.getData().isEmpty())) {
            return null;
        }

        pages = new ArrayList<>();

        for (Like like : likes.getData()) {
            FanPageEntity page = fanPageRepo.findOne(like.getId());
            
            if (page == null) {
                page  = new FanPageEntity();
            }

            page.setId(like.getId());
            page.setDescription(like.getAbout());
            page.setName(like.getName());
            page.setProfilePicUrl(like.getPicture().getData().getUrl());
            page.getUsers().add(userE);

            pages.add(page);
        }

        return pages;
    }


}
