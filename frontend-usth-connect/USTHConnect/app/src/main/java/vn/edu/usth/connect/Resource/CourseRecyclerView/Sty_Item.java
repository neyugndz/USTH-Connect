package vn.edu.usth.connect.Resource.CourseRecyclerView;

public class Sty_Item {

    private String heading;
    private String subhead;
    private Long id;

    public Sty_Item(String heading,  String subhead, Long id) {
        this.heading = heading;
        this.subhead = subhead;
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
