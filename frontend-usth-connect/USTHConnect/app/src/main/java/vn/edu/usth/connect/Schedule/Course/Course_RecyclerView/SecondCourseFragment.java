package vn.edu.usth.connect.Schedule.Course.Course_RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.R;

public class SecondCourseFragment extends BaseCourseFragment {

    private String major;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            major = getArguments().getString("major", "default_major"); // Retrieve the major value
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_second_course;
    }

    @Override
    protected List<CourseItem> getCourseData() {
        List<CourseItem> courses = new ArrayList<>();
        if (major == null || major.isEmpty()) return courses;
        switch (major) {
            case "ICT":
                courses.add(new CourseItem("Advanced Programming with Python", "Dr. Tran Giang Son", false));
                courses.add(new CourseItem("Algebraic Structures", "Dr. Doan Nhat Quang", false));
                courses.add(new CourseItem("Algorithms and Data structures", "Dr. Doan Nhat Quang", false));
                courses.add(new CourseItem("Fundamentals of Database", "Dr. Nguyen Hoang Ha", false));
                courses.add(new CourseItem("FR2.1", "", false));
                courses.add(new CourseItem("FR2.2", "", false));
                courses.add(new CourseItem("Numerical Methods", "Dr. Thai Minh Quan", false));
                courses.add(new CourseItem("Object-oriented Programming", "Dr. Tran Giang Son", false));
                courses.add(new CourseItem("Signal and Systems", "Le Nhu Chu Hiep", false));
                courses.add(new CourseItem("Computational Theory", "Dr. Giang Anh Tuan", false));
                courses.add(new CourseItem("Computer Networks", "Dr. Giang Anh Tuan", false));
                courses.add(new CourseItem("Digital Image Processing", "Dr. Tran Giang Son", false));
                courses.add(new CourseItem("Digital Signal Processing", "Prof. Tran Duc Tan", false));
                courses.add(new CourseItem("Operating System", "Prof. Hagimon", false));
                courses.add(new CourseItem("Software Engineering", "Dr. Le Minh Duc", false));
                courses.add(new CourseItem("Software testing and quality assurance", "Le Nhu Chu Hiep, Kieu Quoc Viet", false));
                courses.add(new CourseItem("Mobile Wireless Communication", "Dr. Nguyen Minh Huong", false));
                courses.add(new CourseItem("Machine Learning and Data Mining 1", "Dr. Doan Nhat Quang, Dr. Le Huu Ton", false));
                break;
            case "CS":
                courses.add(new CourseItem("Project Management", "Prof. Nguyen Van Hung", false));
                courses.add(new CourseItem("FR2.1", "", false));
                courses.add(new CourseItem("FR2.2", "", false));
                courses.add(new CourseItem("Probability and Statistics", "Dr. Tran Hoang Tung", false));
                courses.add(new CourseItem("Computational Theory", "Dr. Giang Anh Tuan", false));
                courses.add(new CourseItem("Computer Networks", "Dr. Giang Anh Tuan", false));
                courses.add(new CourseItem("Algorithms and Data structures", "Dr. Doan Nhat Quang", false));
                courses.add(new CourseItem("Numerical Methods", "Dr. Thai Minh Quan", false));
                courses.add(new CourseItem("Object-oriented Programming", "Dr. Tran Giang Son", false));
                courses.add(new CourseItem("Basic Databases", "Dr. Nguyen Hoang Ha", false));
                courses.add(new CourseItem("Advanced Programming with Python", "Dr. Tran Giang Son", false));
                courses.add(new CourseItem("Operating System", "Prof. Hagimon", false));
                courses.add(new CourseItem("Advanced Computer Architecture and x86 ISA", "Le Nhu Chu Hiep", false));
                courses.add(new CourseItem("Network Programming", "Dr. Giang Anh Tuan", false));
                courses.add(new CourseItem("Mobile Wireless Communication", "Dr. Nguyen Minh Huong", false));
                break;
            case "DS":
                courses.add(new CourseItem("Intellectual Property Management", "Prof. Le Thi Thu Hien", false));
                courses.add(new CourseItem("FR2.1", "", false));
                courses.add(new CourseItem("FR2.2", "", false));
                courses.add(new CourseItem("Advanced Programming with Python", "Dr. Tran Giang Son", false));
                courses.add(new CourseItem("Algebraic Structures", "Dr. Doan Nhat Quang", false));
                courses.add(new CourseItem("Algorithms and Data structures", "Dr. Doan Nhat Quang", false));
                courses.add(new CourseItem("Fundamentals of Database", "Dr. Nguyen Hoang Ha", false));
                courses.add(new CourseItem("Numerical Methods", "Dr. Thai Minh Quan", false));
                courses.add(new CourseItem("Object-oriented Programming", "Dr. Tran Giang Son", false));
                courses.add(new CourseItem("Signal and Systems", "Dr. Tran Hoang Tung", false));
                courses.add(new CourseItem("Operating System", "Prof. Hagimon", false));
                courses.add(new CourseItem("Software Engineering", "Dr. Le Minh Duc", false));
                courses.add(new CourseItem("Fundamental of Optimization", "Dr. Le Xuan Thanh", false));
                courses.add(new CourseItem("Applied Statistics and experimental design", "Dr. Can Van Hao, Dr. Do Van Hoan", false));
                courses.add(new CourseItem("Statistics", "Dr. Nghiem Thi Phuong", false));
                courses.add(new CourseItem("Introduction to Artificial Intelligence", "Dr. Nghiem Thi Phuong", false));
                courses.add(new CourseItem("Image Processing", "Dr. Nghiem Thi Phuong", false));
                break;
        }
        // Add more courses for more major
        return courses;
    }

    @Override
    protected String getFavouriteKey() {
        return "favourite_courses_second";
    }

}
