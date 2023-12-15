package vttp.ssf.assessment.eventmanagement.utils;


import java.io.StringReader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.ssf.assessment.eventmanagement.models.Event;

public class Utility {

    private static final DateTimeFormatter dateFormatter 
        = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");
    
    public static JsonObject eventToJson(Event event) {
        JsonObject jsonObj = Json.createObjectBuilder() 
			.add("eventId", event.getEventId())
            .add("eventName", event.getEventName())
            .add("eventSize", event.getEventSize())
            .add("eventDate", event.getEventDate())
            .add("participants", event.getParticipants())
            .build();

        return jsonObj;
    }

    public static JsonObject strToJson(String str) {
        JsonReader jsonReader = Json.createReader(new StringReader(str));
        JsonObject jsonObject = jsonReader.readObject();
        return jsonObject;
    }

    public static Event jsonToEvent(JsonObject jsonObj) {
        Integer eventId = jsonObj.getInt("eventId");
        String eventName = jsonObj.getString("eventName");
        Integer eventSize = jsonObj.getInt("eventSize");
        long eventDate = jsonObj.getJsonNumber("eventDate").longValue();
        Integer participants = jsonObj.getInt("participants");
        return new Event(eventId, eventName, eventSize, eventDate, participants);
    } 

    public static Boolean validateAge(Date birthDate) {

        LocalDate birthDateParsed = birthDate.toInstant()
            .atZone(ZoneId.of("Asia/Dushanbe"))
            .toLocalDate();
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Dushanbe"));
        int age = (int) ChronoUnit.YEARS.between(birthDateParsed, today);
        if (age >= 21) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean validateNoOfTickets(Integer currSize, Integer eventSize) {
        if (currSize <= eventSize) {
            return true;
        } else {
            return false;
        }
    }
}