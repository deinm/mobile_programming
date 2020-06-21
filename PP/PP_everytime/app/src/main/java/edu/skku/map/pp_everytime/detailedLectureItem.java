package edu.skku.map.pp_everytime;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class detailedLectureItem {
    int totalIdx;

    String detailedEnglish;
    String detailedHW;
    String detailedTeam;
    String detailedAttendance;
    String detailedExam;

    public detailedLectureItem() { }

    public detailedLectureItem(int englishidx, int hwIdx, int teamIdx, int attendanceIdx, int examIdx, int totalIdx) {
        if(englishidx == 0){
            this.detailedEnglish = "All of the course are delivered in English";
        }
        else if(englishidx == 1){
            this.detailedEnglish = "Most of the course are delivered in English, but Korean is seldomly used.";
        }
        else if(englishidx == 2){
            this.detailedEnglish = "English and Korean are used evenly";
        }
        else if(englishidx == 3){
            this.detailedEnglish = "Korean is mostly used";
        }

        if(hwIdx == 0){
            this.detailedHW = "Assignment : None";
        }
        else if(hwIdx == 1){
            this.detailedHW = "Assignment : Average";
        }
        else if(hwIdx == 2){
            this.detailedHW = "Assignment : Many";
        }

        if(teamIdx == 0){
            this.detailedTeam = "Team Assignment : None";
        }
        else if(teamIdx == 1){
            this.detailedTeam = "Team Assignment : Average";
        }
        else if(teamIdx == 2){
            this.detailedTeam = "Team Assignment : Many";
        }

        if(attendanceIdx == 0){
            this.detailedAttendance = "Attendance : Checked every time";
        }
        else if(attendanceIdx == 1){
            this.detailedAttendance = "Attendance : Checked randomly";
        }
        else if(attendanceIdx == 2){
            this.detailedAttendance = "Attendance : Not checked";
        }

        if(examIdx == 0){
            this.detailedExam = "Exam : None";
        }
        else if(examIdx == 1){
            this.detailedExam = "Exam : 1 time";
        }
        else if(examIdx == 2){
            this.detailedExam = "Exam : 2 times";
        }
        else if(examIdx == 3){
            this.detailedExam = "Exam : More than 2 times";
        }

        this.totalIdx = totalIdx;
    }

    public String getDetailedEnglish() { return detailedEnglish; }

    public String getDetailedHW() { return detailedHW; }

    public String getDetailedTeam() { return detailedTeam; }

    public String getDetailedAttendance() { return detailedAttendance; }

    public String getDetailedExam() { return detailedExam; }

    public int getTotalIdx() {
        return totalIdx;
    }
}
