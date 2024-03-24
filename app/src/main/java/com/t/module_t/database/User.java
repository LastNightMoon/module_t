package com.t.module_t.database;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    public String username;
    public String email;
    public boolean status;
    public String like_course;
    public ArrayList<User> students = new ArrayList<>();
    public ArrayList<Notification> notifications = new ArrayList<>();
    public ArrayList<String> id_courses = new ArrayList<>();

    public User(String username, String email, boolean status) {
        this.username = username;
        this.email = email;
        this.status = status;
    }

    public User(HashMap<String, Object> map) {
        this.username = map.get("username").toString();
        this.email = map.get("email").toString();
        this.status = Boolean.parseBoolean(map.get("status").toString());
        if (map.containsKey("like_course"))
            this.like_course = map.get("like_course").toString();
        HashMap<String, String> coursesMap = (HashMap<String, String>) map.get("courses");
        if (coursesMap != null) {
            for (String courseId : coursesMap.values()) {
                id_courses.add(courseId);
            }
        }
        // Обработка списка студентов
        if (map.containsKey("students")) {
            ArrayList<HashMap<String, Object>> studentsList = (ArrayList<HashMap<String, Object>>) map.get("students");
            for (HashMap<String, Object> studentMap : studentsList) {
                String email = (String) studentMap.get("email");
                boolean status = (boolean) studentMap.get("status");
                String username = (String) studentMap.get("username");
                students.add(new User(username, email, status));
            }
        }
        if (map.containsKey("notifications")) {
            HashMap<String, Object> data = (HashMap<String, Object>) map.get("notifications");
            // Проход по каждой записи в данных уведомлений
            for (HashMap.Entry<String, Object> entry : data.entrySet()) {
                HashMap<String, Object> notificationData = (HashMap<String, Object>) entry.getValue();
                HashMap<String, Object> notificationMap = (HashMap<String, Object>) notificationData.get("date");
                String text = (String) notificationData.get("text");
                String id_notifications = notificationData.get("id_notifications").toString();
                notifications.add(new Notification(text, id_notifications,notificationMap));
            }
        }
    }

    public ArrayList<User> getStudents() {
        return students;
    }

    public String getEmail() {
        return email.replace("~", ".");
    }

    @NonNull
    @Override
    public String toString() {
        return "Username: " + username + "Email: " + getEmail() + "Students: " + students + "Notifications: " + notifications;
    }
}
