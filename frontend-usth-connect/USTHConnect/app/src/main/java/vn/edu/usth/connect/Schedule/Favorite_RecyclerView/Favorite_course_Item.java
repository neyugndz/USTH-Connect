package vn.edu.usth.connect.Schedule.Favorite_RecyclerView;

public class Favorite_course_Item {

    String heading;
    String subhead;

    public Favorite_course_Item(String heading,  String subhead) {
        this.heading = heading;
        this.subhead = subhead;

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

}
