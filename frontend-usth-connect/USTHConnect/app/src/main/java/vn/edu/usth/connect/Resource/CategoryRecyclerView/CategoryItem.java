package vn.edu.usth.connect.Resource.CategoryRecyclerView;

public class CategoryItem {

    private String heading;
    private int categoryId;

    public CategoryItem(String heading, int categoryId){
        this.heading = heading;
        this.categoryId = categoryId;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
