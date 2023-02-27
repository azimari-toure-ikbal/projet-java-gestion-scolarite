package com.gesschoolapp.utils;

import com.gesschoolapp.models.classroom.Classe;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class Route {

    private String routeMainName;

    public String getRouteMainName() {
        return routeMainName;
    }

    public void setRouteMainName(String routeMainName) {
        this.routeMainName = routeMainName;
    }

    private String routeLink;
    private BorderPane routeView;
    private Button navSelection;

    private FontAwesomeIcon routeIcon;

    public Route(Classe classeSelectionee){
        setRouteLink("/"+classeSelectionee.getIntitule());
    }

    public Route(String routeMainName, BorderPane routeView, Button navSelection, FontAwesomeIcon routeIcon) {
        setRouteMainName(routeMainName);
        setRouteView(routeView);
        setNavSelection(navSelection);
        setRouteIcon(routeIcon);
    }

    public Route(String routeMainName, BorderPane routeView, Button navSelection) {
        setRouteMainName(routeMainName);
        setRouteView(routeView);
        setNavSelection(navSelection);
    }

    public Button getNavSelection() {
        return this.navSelection;
    }

    public void setNavSelection(Button navSelection) {
        this.navSelection = navSelection;
    }

    public BorderPane getRouteView() {
        return routeView;
    }

    public void setRouteView(BorderPane routeView) {
        this.routeView = routeView;
    }


    public String getRouteLink() {
        return routeLink;
    }

    public void setRouteLink(String routeLink) {
        this.routeLink = routeLink;
    }

    public FontAwesomeIcon getRouteIcon() {
        return routeIcon;
    }

    public void setRouteIcon(FontAwesomeIcon routeIcon) {
        this.routeIcon = routeIcon;
    }

}
