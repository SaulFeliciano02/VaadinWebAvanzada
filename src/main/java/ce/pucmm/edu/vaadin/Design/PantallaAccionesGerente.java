package ce.pucmm.edu.vaadin.Design;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class PantallaAccionesGerente extends VerticalLayout {

    Button modificar = new Button("Modificar");
    Button eliminar = new Button("Eliminar");

    public PantallaAccionesGerente() {
        modificar.getElement().setAttribute("theme", "success");
        eliminar.getElement().setAttribute("theme", "error");

        HorizontalLayout buttons = new HorizontalLayout(modificar, eliminar);
        buttons.setSpacing(true);
        add(buttons);
    }
}
