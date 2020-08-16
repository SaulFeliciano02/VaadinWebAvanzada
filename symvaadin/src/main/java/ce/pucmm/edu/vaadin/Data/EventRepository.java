package ce.pucmm.edu.vaadin.Data;

import ce.pucmm.edu.vaadin.Model.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<CalendarEvent, Long> {
    List<CalendarEvent> findAllByDate(Date date);

    @Query("select event from CalendarEvent event where event.date between ?1 and ?2")
    List<CalendarEvent> findByDatesBetween(Date start, Date end);
}
