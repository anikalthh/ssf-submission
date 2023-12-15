package vttp.ssf.assessment.eventmanagement;

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
	}
}
