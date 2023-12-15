package vttp.ssf.assessment.eventmanagement.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.utils.Utility;

@Repository
public class RedisRepository {

	@Autowired @Qualifier("redis")
	private RedisTemplate<String, String> template;

	private String hashRef = "events";
	private String listRef = "eventsList";

	// TODO: Task 2
	public void saveRecord(Event event) {
		JsonObject jsonObj = Utility.eventToJson(event);
		template.opsForHash().put(hashRef, event.getEventId().toString(), jsonObj.toString());
		template.opsForList().rightPush(listRef, jsonObj.toString());
	}

	// TODO: Task 3
	public long getNumberOfEvents() {
		return template.opsForList().size(listRef);
		// return template.opsForHash().size(hashRef);
	}


	// TODO: Task 4
	public Event getEvent(Integer index) {
		String jsonStr = template.opsForList().index(listRef, index);
		JsonObject jsonObj = Utility.strToJson(jsonStr);
		Event event = Utility.jsonToEvent(jsonObj);
		return event;
	}

	public Event getEventViaId(Integer eventId) {
		String jsonStr = template.opsForHash().get(hashRef, eventId.toString()).toString();
		JsonObject jsonObj = Utility.strToJson(jsonStr);
		Event event = Utility.jsonToEvent(jsonObj);
		return event;
	}

	public List<Event> getAllEvents() {
		List<Event> listOfEvents = new LinkedList<>();

		Map<Object, Object> entries = template.opsForHash().entries(hashRef);
		for (Object e: entries.values()) {
			String jsonStr = e.toString();
				JsonObject jsonObj = Utility.strToJson(jsonStr);
				Event event = Utility.jsonToEvent(jsonObj);
				listOfEvents.add(event);
		}

		return listOfEvents;
	}

}

