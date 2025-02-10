package vn.edu.usth.connect.Campus.RecyclerView;

public class BuildingItem {

    int image;
    String heading;
    String subhead;

    public BuildingItem(String heading,  String subhead, int image) {
        this.heading = heading;
        this.subhead = subhead;
        this.image = image;
    }

    public String getHeading() {
        return heading;
    }

    public String getSubhead() {
        return subhead;
    }

    public int getImage() {
        return image;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
