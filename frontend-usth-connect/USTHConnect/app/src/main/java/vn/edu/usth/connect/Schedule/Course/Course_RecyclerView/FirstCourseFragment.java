package vn.edu.usth.connect.Schedule.Course.Course_RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.R;

public class FirstCourseFragment extends BaseCourseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_first_course;
    }

    @Override
    protected List<CourseItem> getCourseData() {
        List<CourseItem> courses = new ArrayList<>();
        courses.add(new CourseItem("Introduction to Informatics", "Dr.", false));
        courses.add(new CourseItem("Fundamental Physics I", "Dr.", false));
        courses.add(new CourseItem("Fundamental Physics II", "Dr.", false));
        courses.add(new CourseItem("General Chemistry I", "Dr. Tran Dinh Phong", false));
        courses.add(new CourseItem("General Chemistry II", "Dr. Tran Dinh Phong", false));
        courses.add(new CourseItem("Cellular Biology", "Dr. Le Thanh Huong", false));
        courses.add(new CourseItem("Biochemistry", "Dr. Le Thi Thu Hang", false));
        courses.add(new CourseItem("Electromagnetism", "Dr. Nguyen Quoc Hung", false));
        courses.add(new CourseItem("General Microbiology", "Dr. Tran Tuan Anh, Dr, Nguyen Thi Tuyet Nhung, MSc. Tran Huyen Linh", false));
        courses.add(new CourseItem("Organic Chemistry", "Prof. Nguyen Van Hung", false));
        courses.add(new CourseItem("Practical Physics", "Dr. Hoang Thi Hoang Cam, MSc. Vu Ngoc Linh", false));
        courses.add(new CourseItem("Linear Algebra", "Dr. Nguyen Viet Dung", false));
        courses.add(new CourseItem("Calculus I", "Prof. Le Hai Khoi, Dr. Luong Thai Hung", false));
        courses.add(new CourseItem("Calculus II", "Prof. Le Hai Khoi, Dr. Luong Thai Hung", false));
        courses.add(new CourseItem("Introduction to Algorithms", "Dr. Le Huu Ton", false));
        courses.add(new CourseItem("Basic Programming", "Dr. Le Huu Ton, Dr. Nguyen Minh Huong", false));
        courses.add(new CourseItem("Discrete Mathematics", "Dr. Nguyen Hoang Thach, Dr. Nguyen Hai Yen", false));
        courses.add(new CourseItem("Mobile Wireless Communication", "Dr. Nguyen Minh Huong", false));
        courses.add(new CourseItem("Machine Learning and Data Mining 1", "Dr. Doan Nhat Quang, Dr. Le Huu Ton", false));
        return courses;
    }

    @Override
    protected String getFavouriteKey() {
        return "favourite_courses_first";
    }
}
