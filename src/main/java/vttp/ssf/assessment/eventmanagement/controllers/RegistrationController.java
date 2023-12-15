package vttp.ssf.assessment.eventmanagement.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.models.Participant;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;
import vttp.ssf.assessment.eventmanagement.utils.Utility;

@Controller
@RequestMapping
public class RegistrationController {

    @Autowired
    private DatabaseService dSvc;

    // TODO: Task 6
    @PostMapping("/events/register/{eventId}")
    public String getRegistrationPage(@PathVariable("eventId") Integer eventId, Model m, HttpSession sess) {

        Event event = dSvc.getEventViaId(eventId);

        m.addAttribute("participant", new Participant());
        m.addAttribute("event", event);

        sess.setAttribute("eventId", event.getEventId());
        sess.setAttribute("eventSize", event.getEventSize());
        sess.setAttribute("eventName", event.getEventName());
        sess.setAttribute("eventDateFormatted", event.getEventDateFormatted());
        return "eventregister";
    }

    // TODO: Task 7
    @PostMapping("/registration/register")
    public String processRegistration(@Valid @ModelAttribute Participant participant, BindingResult bindings, Model m, HttpSession sess) {
        if (bindings.hasErrors()) {
            m.addAttribute("eventName", sess.getAttribute("eventName"));
            m.addAttribute("eventDate", sess.getAttribute("eventDateFormatted"));
            return "eventregister";
        }

        // VALIDATION CHECKS (AGE AND PARTICIPANTS)
        Integer eventSize = (Integer) sess.getAttribute("eventSize");
        Integer eventId = (Integer) sess.getAttribute("eventId");
        Integer currSize = dSvc.getEventViaId(eventId).getParticipants();
        Date birthDate = participant.getBirthDate();

        Boolean participantIsOfAge = Utility.validateAge(birthDate);
        Boolean validNoOfTickets = Utility.validateNoOfTickets((currSize + participant.getNoOfTickets()), eventSize);

        if (Boolean.FALSE.equals(participantIsOfAge) && Boolean.FALSE.equals(validNoOfTickets)) {
            m.addAttribute("error", "Your request for tickets exceeded event size and you are below 21 years old.");
            return "ErrorRegistration";
        } else if (Boolean.FALSE.equals(participantIsOfAge)) {
            m.addAttribute("error", "You are below 21 years old.");
            return "ErrorRegistration";
        } else if (Boolean.FALSE.equals(validNoOfTickets)) {
            m.addAttribute("error", "Your request for tickets exceeded event size.");
            
            return "ErrorRegistration";
        }

        // UPDATE 
        dSvc.updateParticipants(eventId, participant.getNoOfTickets());

        String eventName = sess.getAttribute("eventName").toString();
        String eventDateFormattedStr = sess.getAttribute("eventDateFormatted").toString();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        LocalDate eventDateFormatted = LocalDate.parse(eventDateFormattedStr, formatter);

        m.addAttribute("eventName", eventName);
        m.addAttribute("eventDate", eventDateFormatted);
        // System.out.printf("CURR PARTICIPANTS: %s\n", dSvc.getEventViaId(eventId).getParticipants());
        return "SuccessRegistration";
    }
}
