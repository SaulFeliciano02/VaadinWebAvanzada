package ce.pucmm.edu.vaadin.Services;

import ce.pucmm.edu.vaadin.Data.EventRepository;
import ce.pucmm.edu.vaadin.Model.CalendarEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.calendar.CalendarItemTheme;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class CalendarEventService {

    @Autowired
    private EventRepository er;

    public List<CalendarEvent> listEvents() {
        return er.findAll();
    }

    public List<CalendarEvent> findEventByDate(Date date) {
        return er.findAllByDate(date);
    }

    public List<CalendarEvent> findEventInDateRange(Date start, Date end) {
        return er.findByDatesBetween(start, end);
    }

    public CalendarEvent findEventById(long id){
        return er.getOne(id);
    }

    @Transactional
    public CalendarEvent createEvent(long id, Date date, String title, CalendarItemTheme color) {
        return er.save(new CalendarEvent(id, date, title, color));
    }

    public void editEvent(long EventoID) throws Exception {
        try {
            CalendarEvent calendarEvent = findEventById(EventoID);
            er.save(calendarEvent);
        } catch (PersistenceException e) {
            throw new PersistenceException("Hubo un error al editar el evento.");
        } catch (NullPointerException e) {
            throw new NullPointerException("Al editar el evento hubo un error de datos nulos.");
        } catch (Exception e) {
            throw new Exception("Hubo un error general al editar un evento.");
        }
    }

    @Transactional
    public boolean deleteEvent(CalendarEvent calendarEvent) {
        er.delete(calendarEvent);
        return true;
    }
}
