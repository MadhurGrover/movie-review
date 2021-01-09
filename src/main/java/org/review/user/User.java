package org.review.user;

import org.review.role.Critic;
import org.review.role.Role;
import org.review.role.Viewer;

import java.util.Objects;

/**
 * Entity holding user details
 */
public class User {

    protected int userId;
    protected String userName;
    protected int numOfReviews;
    protected Role role = new Viewer();

    public Role getRole() {
        return role;
    }

    public int getUserId() {
        return userId;
    }

    public User withUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public int getNumOfReviews() {
        return numOfReviews;
    }

    /**
     * Elevate user role to critic , if number of reviews exceed 2.
     */
    public void incNumOfReviews() {
        this.numOfReviews++;
        if (this.numOfReviews == 3) {
            this.role = new Critic();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }
}
