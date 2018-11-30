package nothelloworld.sociallive;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Party {

    //private User host;
    //private String title;
    private String location;
    private String description;
    private String startTime;
    private String endTime;
    private String date;
    private String dateCreated;
    private String partyId;

    public Party(String location, String description, String startTime,
                 String endTime, String date, String dateCreated,
                 String partyId)
    {
        this.location = location;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.dateCreated = dateCreated;
        this.partyId = partyId;
    }

    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public String getDate() { return date; }
    public String getDateCreated() { return dateCreated; }
    public String getPartyId() { return partyId; }
}
