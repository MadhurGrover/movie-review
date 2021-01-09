package org.review.role;

public class Critic implements Role {


    @Override
    public int getWeightage() {
        return 2;
    }

    @Override
    public String getName() {
        return "Critic";
    }
}
