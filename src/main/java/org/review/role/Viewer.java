package org.review.role;

public class Viewer implements Role {

    @Override
    public int getWeightage() {
        return 1;
    }

    @Override
    public String getName() {
        return "Viewer";
    }


}
