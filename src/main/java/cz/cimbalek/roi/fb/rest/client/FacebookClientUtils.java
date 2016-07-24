package cz.cimbalek.roi.fb.rest.client;

import cz.cimbalek.roi.fb.rest.model.User;

/**
 *
 * @author cimbalek
 */
public class FacebookClientUtils {

    /**
     * Yields count of user's likes.
     *
     * @param user user whose likes shall be counted.
     * @return count of user's likes. 0 if user is null.
     */
    public static int getUserLikesCount(User user) {
        if (user == null) {
            return 0;
        }

        int likesCount;

        if (hasUserLikes(user)) {
            likesCount = user.getLikes().getData().size();
        } else {
            likesCount = 0;
        }

        return likesCount;
    }

    /**
     * Yields whether user has any likes.
     *
     * @param user user to determine if he/she has likes.
     * @return True if user has any likes, false otherwise.
     */
    public static boolean hasUserLikes(User user) {
        if (user == null) {
            return false;
        }

        return (user.getLikes() != null)
            && (user.getLikes().getData() != null)
            && (!user.getLikes().getData().isEmpty());
    }
}
