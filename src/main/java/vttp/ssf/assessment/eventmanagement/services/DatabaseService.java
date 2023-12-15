package vttp.ssf.assessment.eventmanagement.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;

@Service
public class DatabaseService {

    @Autowired
    private RedisRepository repo;
    
    // TODO: Task 1
    public List<Event> readFile(String fileName) throws FileNotFoundException, IOException {
        File file = new File(fileName);
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while (null!=(line=br.readLine())) {
                sb.append(line);
            }
        }

        String fileStr = sb.toString();
        JsonReader jsonReader = Json.createReader(new StringReader(fileStr));
        JsonArray jsonArray = jsonReader.readArray();

        List<Event> listOfEvents = jsonArray.stream() 
            .map(j -> j.asJsonObject())
            .map(o -> {
                Integer eventId = o.getInt("eventId");
                String eventName = o.getString("eventName");
                Integer eventSize = o.getInt("eventSize");
                long eventDate = o.getJsonNumber("eventDate").bigDecimalValue().longValue();
                Integer participants = o.getInt("participants");
                return new Event(eventId, eventName, eventSize, eventDate, participants);
            })
            .toList();

        System.out.printf("\n ------------ LIST OF EVENTS LOADED ------------ \n%s\n", listOfEvents);

        System.out.println("\n---- DETAILS ----\n");

        for (Event e : listOfEvents) {
            System.out.printf("EVENT ID: %s\n", e.getEventId());
            System.out.printf("EVENT NAME: %s\n", e.getEventName());
            System.out.printf("EVENT SIZE: %s\n", e.getEventSize());
            System.out.printf("EVENT DATE: %s\n", e.getEventDate());
            System.out.printf("EVENT PARTICPANTS: %s\n\n", e.getParticipants());
        }

        return listOfEvents;
    }

    public void saveRecord(Event event) {
        repo.saveRecord(event);
    }

    public long getNumberOfEvents() {
        return repo.getNumberOfEvents();
    }

    public Event getEvent(Integer index) {
        return repo.getEvent(index);
    }

    public Event getEventViaId(Integer eventId) {
        return repo.getEventViaId(eventId);
    }

    public List<Event> getAllEvents() {
        return repo.getAllEvents();
    }

    public void updateParticipants(Integer eventId, Integer noOfParticipants) {
		Event event = repo.getEventViaId(eventId);
        Integer currSize = event.getParticipants();
        Integer newSize = currSize + noOfParticipants;
        event.setParticipants(newSize);
        repo.saveRecord(event);
	}
}
