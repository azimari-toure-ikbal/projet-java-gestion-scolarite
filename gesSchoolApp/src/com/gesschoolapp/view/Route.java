package application.view;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class Route {
    private String routeLink;
    private BorderPane routeView;
    private Button navSelection;

    private FontAwesomeIcon routeIcon;



    public Route(String routeLink, BorderPane routeView, Button navSelection, FontAwesomeIcon routeIcon) {
        setRouteLink(routeLink);
        setRouteView(routeView);
        setNavSelection(navSelection);
        setRouteIcon(routeIcon);
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
