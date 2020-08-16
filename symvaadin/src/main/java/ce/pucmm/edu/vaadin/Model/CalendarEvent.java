package ce.pucmm.edu.vaadin.Model;

import org.vaadin.calendar.CalendarItemTheme;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class CalendarEvent implements Serializable {

    @Id
    private long id;
    private Date date;
    private String title;
    private CalendarItemTheme color;

    public CalendarEvent() {
    }

    public CalendarEvent(Long id, Date date, String title, CalendarItemTheme color) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date fecha) {
        this.date = fecha;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titulo) {
        this.title = titulo;
    }

    public CalendarItemTheme getColor() {
        return color;
    }

    public void setColor(CalendarItemTheme color) {
        this.color = color;
    }
}