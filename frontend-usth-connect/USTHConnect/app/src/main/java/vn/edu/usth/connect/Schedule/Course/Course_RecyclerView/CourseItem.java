package vn.edu.usth.connect.Schedule.Course.Course_RecyclerView;

public class CourseItem {

    String heading;
    String subhead;
    boolean isFavourite;

    public CourseItem(String heading, String subhead, boolean isFavourite){
        this.heading = heading;
        this.subhead = subhead;
        this.isFavourite = isFavourite;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public String getHeading() {
        return heading;
    }

    public String getSubhead() {
        return subhead;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseItem that = (CourseItem) o;
        return heading.equals(that.heading); // Use unique identifiers for comparison
    }

    @Override
    public int hashCode() {
        return heading.hashCode(); // Use the unique identifier for hash calculation
    }
}
