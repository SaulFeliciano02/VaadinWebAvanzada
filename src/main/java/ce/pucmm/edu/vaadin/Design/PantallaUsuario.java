package ce.pucmm.edu.vaadin.Design;

import ce.pucmm.edu.vaadin.Model.User;
import ce.pucmm.edu.vaadin.Services.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@Route("usuario")
@SpringComponent
@UIScope
public class PantallaUsuario extends VerticalLayout {

    public PantallaUsuario(@Autowired UserService userService) {
        User user;

        if (userService.listUsers().isEmpty())
            getUI().get().navigate("");
        else if (!userService.listUsers().get(0).isIsLoggedIn())
            getUI().get().navigate("");
        else {
            user = userService.listUsers().get(0);

            H4 titulo = new H4("Agenda ISC");

            Button calendario = new Button("Return");
            calendario.setIcon(new Icon(VaadinIcon.ARROW_LEFT));

            calendario.addClickListener((evento) -> getUI().get().navigate("calendario"));

            H3 titulo1 = new H3("User Info");
            H6 nombre = new H6("Name: " + user.getName());
            H6 email = new H6("Email: " + user.getEmail());

            H3 titulo2 = new H3("Edit User");

            TextField nuevoEmail = new TextField("Email");
            TextField nuevoNombre = new TextField("Nombre");

            Button guardar = new Button("Save");
            guardar.setIcon(new Icon(VaadinIcon.CHECK_CIRCLE));

            guardar.addClickListener((evento) -> {
                try {
                    if (!nuevoEmail.getValue().equals(""))
                        user.setEmail(nuevoEmail.getValue());

                    if (!nuevoNombre.getValue().equals(""))
                        user.setName(nuevoNombre.getValue());

                    userService.editUser(user);
                    getUI().get().getPage().reload();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            add(titulo, calendario, titulo2, nuevoNombre, nuevoEmail, titulo1, nombre, email);
            setAlignItems(Alignment.CENTER);
        }
    }
}

