package ce.pucmm.edu.vaadin.Design;

import ce.pucmm.edu.vaadin.Model.CalendarEvent;
import ce.pucmm.edu.vaadin.Services.CalendarEventService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.calendar.CalendarItemTheme;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@SpringComponent
@UIScope
public class PantallaEvento extends VerticalLayout {
    DatePicker fecha = new DatePicker();
    TextField titulo = new TextField("Event Title");

    public PantallaEvento(@Autowired CalendarEventService calendarEventService) {
        FormLayout formLayout = new FormLayout();

        H3 header = new H3("Create Event");

        fecha.setLabel("Date");
        fecha.setPlaceholder("Date");
        fecha.setValue(LocalDate.now());

        Button agregar = new Button("Add");
        //agregar.getElement().setAttribute("theme", "primary");

        Button editar = new Button("Edit");
        //editar.getElement().setAttribute("theme", "success");

        Button cancelar = new Button("Cancelar");
        cancelar.getStyle().set("color", "red");

        HorizontalLayout botones = new HorizontalLayout(agregar, cancelar);

        botones.setSpacing(true);

        formLayout.add(titulo, fecha);
        setAlignItems(Alignment.CENTER);

        add(header, formLayout, botones);

        agregar.addClickListener((evento) -> {
            CalendarEvent e = new CalendarEvent(
                    (long) calendarEventService.listEvents().size() + 1,
                    Date.from(fecha.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    titulo.getValue(),
                    CalendarItemTheme.Green
            );

            try {
                calendarEventService.createEvent(
                        e.getId(),
                        e.getDate(),
                        e.getTitle(),
                        e.getColor()
                );

                titulo.setValue("");
                fecha.setValue(LocalDate.now());
            } catch (Exception exp) {
                exp.printStackTrace();
            }
            Principal.calendario.refresh();
        });

        cancelar.addClickListener((evento) -> {
            titulo.setValue("");
            fecha.setValue(LocalDate.now());
        });
    }
}
