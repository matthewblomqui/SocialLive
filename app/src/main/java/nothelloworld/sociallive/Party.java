package nothelloworld.sociallive;

import android.location.Location;

import java.sql.Time;
import java.util.Date;

public class Party {

    private User host;
    private Location location;
    private String title;
    private Date date;
    private Time startTime;
    private Time endTime;
    private String additionalInfo;
    private int numPeopleGoing;

    public Party(Location location, String title, Date date, Time startTime, Time endTime, int numPeopleGoing) {
        this.location = location;
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numPeopleGoing = numPeopleGoing;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
