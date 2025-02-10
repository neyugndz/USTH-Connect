package vn.edu.usth.connect.Resource.SubCategoryRecyclerView;

public class SubCategoryItem {
    private String name;
    private int categoryId;

    public SubCategoryItem(int categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCategoryId() {
        return categoryId;
    }

}
