package vttp.ssf.assessment.eventmanagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@SpringBootApplication
public class EventmanagementApplication implements CommandLineRunner {

	@Autowired
	private DatabaseService dSvc;
	public static void main(String[] args) {
		SpringApplication.run(EventmanagementApplication.class, args);
	}
	
	// TODO: Task 1
	@Override
	public void run(String... args) throws IOException {
		// List<Event> listOfEvents = dSvc.readFile("src/main/resources/static/events.json");
		List<Event> listOfEvents = dSvc.readFile("events.json");
		
		for (Event e : listOfEvents) {
			dSvc.saveRecord(e);
		}
		System.out.printf("\nNumber of events: %s\n", dSvc.getNumberOfEvents());

		System.out.printf("\nget event of index 3: %s\n id: %s\n name: %s\n", dSvc.getEvent(3), dSvc.getEvent(3).getEventId(), dSvc.getEvent(3).getEventName());
	}
}
