package vttp.ssf.assessment.eventmanagement.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;

public class Event {
    private Integer eventId;
    private String eventName;
    private Integer eventSize;
    private long eventDate;
    private String eventDateFormatted;
    private Integer participants;

    // GETTERS AND SETTERS

    public Integer getEventId() {
        return eventId;
    }
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public Integer getEventSize() {
        return eventSize;
    }
    public void setEventSize(Integer eventSize) {
        this.eventSize = eventSize;
    }
    public long getEventDate() {
        return eventDate;
    }
    public void setEventDate(long eventDate) {
        this.eventDate = eventDate;
    }
    public Integer getParticipants() {
        return participants;
    }
    public void setParticipants(Integer participants) {
        this.participants = participants;
    }

    public String getEventDateFormatted() {
        return eventDateFormatted;
    }
    public void setEventDateFormatted(long eventDate) {

        // DateTime date = new DateTime(eventDate);
        Date date2 = new Date(eventDate);
        // System.out.printf("PRINT OG EVENTDATE BEFORE PARSING: %s\n", eventDate);
        // System.out.printf("PRINT OG EVENTDATE AFTER DATETIME INSTANTIATION: %s\n", date);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        this.eventDateFormatted = sdf.format(date2).toString();
    }

    // NO ARGS CONSTRUCTOR
    public Event() {
    }

    // ALL ARGS CONSTRUCTOR
    public Event(Integer eventId, String eventName, Integer eventSize, long eventDate, Integer participants) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventSize = eventSize;
        this.eventDate = eventDate;
        this.participants = participants;
        setEventDateFormatted(eventDate);
    }
    
}
