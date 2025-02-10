package vn.edu.usth.connect.Schedule.Course.Course_RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.R;

public class ThirdCourseFragment extends BaseCourseFragment {

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
        return R.layout.activity_third_course;
    }

    @Override
    protected List<CourseItem> getCourseData() {
        List<CourseItem> courses = new ArrayList<>();
        if (major == null || major.isEmpty()) return courses;
        switch (major) {
            case "ICT":
                courses.add(new CourseItem("Web Application Development", "Msc. Kieu Quoc Viet & Msc. Huynh Vinh Nam", false));
                courses.add(new CourseItem("Object-oriented Systems Analysis and Design", "Dr. Do Trung Dung", false));
                courses.add(new CourseItem("Mobile Application Development", "Dr. Tran Giang Son & Msc. Kieu Quoc Viet", false));
                courses.add(new CourseItem("Advanced Database", "Huynh Vinh Nam", false));
                courses.add(new CourseItem("FR3.1", "", false));
                courses.add(new CourseItem("FR3.2", "", false));
                courses.add(new CourseItem("Computer Vision", "Prof. Nguyen Duc Dung", false));
                courses.add(new CourseItem("Graph Theory", "Le Nhu Chu Hiep", false));
                courses.add(new CourseItem("Machine Learning and Data Mining 1", "Dr. Doan Nhat Quang, Dr. Le Huu Ton", false));
                courses.add(new CourseItem("Machine Learning and Data Mining 2", "Dr. Doan Nhat Quang", false));
                courses.add(new CourseItem("Network Simulation", "Le Nhu Chu Hiep", false));
                courses.add(new CourseItem("Computer Graphic", "", false));
                courses.add(new CourseItem("Introduction to Cryptography", "Dr. Nguyen Minh Huong", false));
                courses.add(new CourseItem("Network Simulation", "Dr. Nguyen Minh Huong", false));
                courses.add(new CourseItem("Distributed Systems", "Le Nhu Chu Hiep", false));
                courses.add(new CourseItem("Natural Language Processing", "Dr. Pham Quang Nhat Minh", false));
                courses.add(new CourseItem("Scientific Writing and Communication", "Dr. Doan Nhat Quang", false));
                break;
            case "CS":
                courses.add(new CourseItem("Scientific writing", "Dr. Doan Nhat Quang", false));
                courses.add(new CourseItem("FR3.1", "", false));
                courses.add(new CourseItem("FR3.2", "", false));
                courses.add(new CourseItem("Law of cyberspace and ethics", "Dr. Do Trung Dung", false));
                courses.add(new CourseItem("Information Security", "Dr. Thai Minh Quan", false));
                courses.add(new CourseItem("Digital forensics", "Dr. Pham Truong Son", false));
                courses.add(new CourseItem("Malware analysis", "Dr. Pham Duy Trung", false));
                courses.add(new CourseItem("Intrusion Detection and Prevention System", "Prof. Pham Thanh Giang", false));
                courses.add(new CourseItem("Administration of Computer Systems", "Le Nhu Chu Hiep", false));
                courses.add(new CourseItem("Web security", "Prof. Hoang Xuan Dau", false));
                courses.add(new CourseItem("Cloud Security and Privacy", "Le Nhu Chu Hiep", false));
                break;
            case "DS":
                courses.add(new CourseItem("FR3.1", "", false));
                courses.add(new CourseItem("FR3.2", "", false));
                courses.add(new CourseItem("Fundamental of data science", "Prof. Nguyen Duc Dung", false));
                courses.add(new CourseItem("Scientific writing", "Dr. Doan Nhat Quang", false));
                courses.add(new CourseItem("Advanced Database", "Huynh Vinh Nam", false));
                courses.add(new CourseItem("Distributed Systems", "Le Nhu Chu Hiep", false));
                courses.add(new CourseItem("Computer Vision", "Prof. Nguyen Duc Dung", false));
                courses.add(new CourseItem("Data Visualization", "Dr. Nguyen Le Dung", false));
                courses.add(new CourseItem("Introduction to Bioinformatics", "", false));
                courses.add(new CourseItem("Analysis of spatial and temporal data", "Dr. Nguyen Xuan Thanh", false));
                courses.add(new CourseItem("Natural Language Processing", "Dr. Pham Quang Nhat Minh", false));
                courses.add(new CourseItem("Graph Analytics for big data", "Dr. Can Van Hao, Dr Do Van Hoan", false));
                courses.add(new CourseItem("Machine Learning in Medicine", "Dr. Tran Giang Son", false));
                break;
        }
        // Add more courses...
        return courses;
    }

    @Override
    protected String getFavouriteKey() {
        return "favourite_courses_second";
    }
}
