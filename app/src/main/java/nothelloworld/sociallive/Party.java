package nothelloworld.sociallive;

import java.sql.Time;
import java.util.Date;

public class Party {

    private User host;
    private String title;
    private Date date;
    private Time startTime;
    private Time endTime;
    private String description;
    private int numPeopleGoing;

    public Party(String title, Date date, Time startTime, Time endTime, int numPeopleGoing, String description) {
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numPeopleGoing = numPeopleGoing;
        this.description = description;
    }

    public void setNumPeopleGoing(int numPeopleGoing) {
        this.numPeopleGoing = numPeopleGoing;
    }

    public int getNumPeopleGoing()
    {
        return numPeopleGoing;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getAdditionalInfo() { return description; }

    public void setAdditionalInfo(String additionalInfo) {
        this.description = additionalInfo;
    }
}
