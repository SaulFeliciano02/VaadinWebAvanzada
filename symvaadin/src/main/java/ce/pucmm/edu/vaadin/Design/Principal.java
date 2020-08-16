package ce.pucmm.edu.vaadin.Design;

import ce.pucmm.edu.vaadin.Model.CalendarEvent;
import ce.pucmm.edu.vaadin.Model.User;
import ce.pucmm.edu.vaadin.Services.CalendarEventService;
import ce.pucmm.edu.vaadin.Services.UserService;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.button.Button;
import org.vaadin.calendar.CalendarComponent;
import org.vaadin.calendar.CalendarItemTheme;
import org.vaadin.calendar.data.AbstractCalendarDataProvider;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Route("calendario")
@SpringComponent
@UIScope
public class Principal extends VerticalLayout {
    public static CalendarComponent<CalendarEvent> calendario = new CalendarComponent<CalendarEvent>()
            .withItemDateGenerator(CalendarEvent::getDate)
            .withItemLabelGenerator(CalendarEvent::getTitle)
            .withItemThemeGenerator(CalendarEvent::getColor);

    @Autowired
    public static CalendarEventService calendarEventService;

    @Autowired
    public Principal(@Autowired final PantallaEvento pantallaEvento,
                     @Autowired UserService userService,
                     @Autowired CalendarEventService calendarEventService,
                     @Autowired final PantallaEmail pantallaEmail,
                     @Autowired PantallaEventoModificar pantallaEventoModificar) {
        Principal.calendarEventService = calendarEventService;

        if (userService.listUsers().isEmpty()) {
            getUI().get().navigate("");
        } else if (!userService.listUsers().get(0).isIsLoggedIn()) {
            getUI().get().navigate("");
        } else {
            setAlignItems(Alignment.CENTER);

            HorizontalLayout layoutBotones = new HorizontalLayout();
            layoutBotones.setSpacing(true);

            Button agregar = new Button("Agregar evento");
            Button enviarEmail = new Button("Enviar email");
            Button verUsuario = new Button("Información de usuario");
            Button CRUD = new Button("CRUD de Gerentes");
            Button salir = new Button("Salir");

            agregar.setIcon(new Icon(VaadinIcon.CALENDAR_CLOCK));
            agregar.getElement().setAttribute("theme", "primary");

            enviarEmail.setIcon(new Icon(VaadinIcon.CALENDAR_ENVELOPE));
            enviarEmail.getElement().setAttribute("theme", "primary");

            verUsuario.setIcon(new Icon(VaadinIcon.CLIPBOARD_USER));

            CRUD.setIcon(new Icon(VaadinIcon.FORM));
            CRUD.getElement().setAttribute("theme", "success");

            salir.setIcon(new Icon(VaadinIcon.SIGN_OUT));
            salir.getElement().setAttribute("theme", "error");

            configurarBotonPantalla(agregar, pantallaEvento);
            configurarBotonPantalla(enviarEmail, pantallaEmail);

            layoutBotones = new HorizontalLayout(agregar, enviarEmail, verUsuario, CRUD, salir);

            salir.addClickListener((evento) -> {
                try {
                    User user = userService.listUsers().get(0);
                    user.setIsLoggedIn(false);
                    userService.editUser(user);

                    getUI().get().navigate("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            verUsuario.addClickListener((evento) -> getUI().get().navigate("usuario"));
            CRUD.addClickListener((evento) -> getUI().get().navigate("gerentes"));

            calendarEventService.createEvent(
                    1,
                    Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    "Practica Vaadin",
                    CalendarItemTheme.Green
            );

            calendario.setDataProvider(new CustomDataProvider());
            calendario.addEventClickListener(evt -> {
                try {
                    pantallaEventoModificar.fecha.setValue(
                            evt.getDetail().getDate().toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDate()
                    );

                    pantallaEventoModificar.titulo.setValue(evt.getDetail().getTitle());

                    abrirPantalla(pantallaEventoModificar);

                    calendarEventService.createEvent(
                            evt.getDetail().getId(), evt.getDetail().getDate(),
                            evt.getDetail().getTitle(), evt.getDetail().getColor()
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            H4 titulo = new H4("C A L E N D A R I O");
            H6 subtitulo = new H6("Eventos");

            setAlignItems(Alignment.CENTER);

            add(titulo, subtitulo, layoutBotones, calendario);
        }

        Button agregar = new Button("Agregar");
        agregar.setIcon(new Icon(VaadinIcon.PLUS));
        agregar.getElement().setAttribute("theme", "primary");
    }

    private void abrirPantalla(VerticalLayout form) {
        Dialog vistaPantalla = new Dialog();
        vistaPantalla.add(form);
        vistaPantalla.open();
    }

    private void configurarBotonPantalla(Button boton, VerticalLayout formulario) {
        boton.addClickListener((e) -> abrirPantalla(formulario));
    }
}

@SpringComponent
@UIScope
class CustomDataProvider extends AbstractCalendarDataProvider<CalendarEvent> {
    @Override
    public Collection<CalendarEvent> getItems(Date fromDate, Date toDate) {
        return Principal.calendarEventService.listEvents();
    }
}
