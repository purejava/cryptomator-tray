package org.purejava.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.purejava.appindicator.GCallback;
import org.purejava.appindicator.AppIndicator;

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
            var gtkSeparator = gtk_menu_item_new();
            var gtkMenu = gtk_menu_new();
            var gtkSubmenu = gtk_menu_new();
            var gtkMenuItem = gtk_menu_item_new();
            gtk_menu_item_set_label(gtkMenuItem, arena.allocateFrom("More"));
            var gtkSMenuItem = gtk_menu_item_new();
            gtk_menu_item_set_label(gtkSMenuItem, arena.allocateFrom("Change icon"));
            var gtkSMenuItem1 = gtk_menu_item_new();
            gtk_menu_item_set_label(gtkSMenuItem1, arena.allocateFrom("Quit"));
            gtk_menu_shell_append(gtkSubmenu, gtkSMenuItem);
            gtk_menu_shell_append(gtkSubmenu, gtkSeparator);
            gtk_menu_shell_append(gtkSubmenu, gtkSMenuItem1);
            gtk_menu_item_set_submenu(gtkMenuItem, gtkSubmenu);
            g_signal_connect_object(gtkSMenuItem1, arena.allocateFrom("activate"), GCallback.allocate(new QuitCallback(), arenaAuto), gtkMenu, 0);
            g_signal_connect_object(gtkSMenuItem, arena.allocateFrom("activate"), GCallback.allocate(new ChangeIconCallback(indicator), arenaAuto), gtkMenu, 0);
            gtk_menu_shell_append(gtkMenu, gtkMenuItem);
            gtk_widget_show_all(gtkMenu);
            AppIndicator.setMenu(indicator, gtkMenu);
            AppIndicator.setAttentionIcon(indicator, "indicator-messages-new");
            AppIndicator.setStatus(indicator, APP_INDICATOR_STATUS_ACTIVE());
            launch();
        }
    }
}