package nothelloworld.sociallive;

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
    private String mImageUrl;

    private int numPeopleAttending;
    private int numUpVotes;

    /**
     *
     * @param location
     * @param description
     * @param startTime
     * @param endTime
     * @param date
     * @param dateCreated
     * @param partyId
     */

    public Party()
    {
        //empty constructor needed
    }

    public Party(String location, String description, String startTime,
                 String endTime, String date, String dateCreated,
                 String partyId, String mImageUrl)
    {
        this.location = location;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.dateCreated = dateCreated;
        this.partyId = partyId;
        this.mImageUrl = mImageUrl;

        numPeopleAttending = 0;
        numUpVotes = 0;
    }

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
        this.mImageUrl = "";

        numPeopleAttending = 0;
        numUpVotes = 0;
    }

    public String getImageUrl() { return mImageUrl; }
    public void setImageUrl(String imageUrl) { mImageUrl = imageUrl; }

    public void setNumPeopleAttending(int peopleAttending) { this.numPeopleAttending = peopleAttending; }
    public int getNumPeopleAttending() { return numPeopleAttending; }
    public void setNumUpVotes(int upVote) { this.numUpVotes = upVote; }
    public int getNumUpVotes() { return numUpVotes; }

    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public String getDate() { return date; }
    public String getDateCreated() { return dateCreated; }
    public String getPartyId() { return partyId; }
}
