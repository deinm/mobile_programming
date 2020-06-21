package edu.skku.map.pp_everytime;

public class LectureItem {
    String courseName;
    String profName;
    int totalScore;

    public LectureItem() { }

    public LectureItem(String courseName, String profName, Integer totalScore) {
        this.courseName = courseName;
        this.profName = profName;
        this.totalScore = totalScore;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getProfName() {
        return profName;
    }

    public int getTotalScore() {
        return totalScore;
    }
}
