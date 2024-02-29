package org.purejava.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.purejava.appindicator.AppIndicator;
import org.purejava.appindicator.GObject;
import org.purejava.appindicator.Gtk;
import org.purejava.appindicator.GCallback;

import java.io.IOException;
import java.lang.foreign.Arena;

import static org.purejava.appindicator.app_indicator_h.*;

public class HelloApplication extends Application {

    static Arena arenaAuto;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try (var arena = Arena.ofConfined()) {
            arenaAuto = Arena.ofAuto();
            var indicator = AppIndicator.newIndicator("org.cryptomator.Cryptomator",
                    "/home/ralph/IdeaProjects/cryptomator-tray/org.cryptomator.Cryptomator.tray-unlocked.svg",
                    APP_INDICATOR_CATEGORY_APPLICATION_STATUS());
            var gtkSeparator = Gtk.newMenuItem();
            var gtkMenu = Gtk.newMenu();
            var gtkSubmenu = Gtk.newMenu();
            var gtkMenuItem = Gtk.newMenuItem();
            Gtk.menuItemSetLabel(gtkMenuItem, "More");
            var gtkSMenuItem = Gtk.newMenuItem();
            Gtk.menuItemSetLabel(gtkSMenuItem, "Change icon");
            var gtkSMenuItem1 = Gtk.newMenuItem();
            Gtk.menuItemSetLabel(gtkSMenuItem1, "Quit");
            Gtk.menuShellAppend(gtkSubmenu, gtkSMenuItem);
            Gtk.menuShellAppend(gtkSubmenu, gtkSeparator);
            Gtk.menuShellAppend(gtkSubmenu, gtkSMenuItem1);
            Gtk.menuItemSetSubmenu(gtkMenuItem, gtkSubmenu);
            GObject.signalConnectObject(gtkSMenuItem1, "activate", GCallback.allocate(new QuitCallback(), arenaAuto), gtkMenu, 0);
            GObject.signalConnectObject(gtkSMenuItem, "activate", GCallback.allocate(new ChangeIconCallback(indicator), arenaAuto), gtkMenu, 0);
            Gtk.menuShellAppend(gtkMenu, gtkMenuItem);
            Gtk.widgetShowAll(gtkMenu);
            AppIndicator.setMenu(indicator, gtkMenu);
            AppIndicator.setAttentionIcon(indicator, "indicator-messages-new");
            AppIndicator.setStatus(indicator, APP_INDICATOR_STATUS_ACTIVE());
            launch();
        }
    }
}