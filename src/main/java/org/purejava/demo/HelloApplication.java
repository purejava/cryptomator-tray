package org.purejava.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
            var indicator = app_indicator_new(arena.allocateUtf8String("org.cryptomator.Cryptomator"),
                    arena.allocateUtf8String("/home/ralph/IdeaProjects/cryptomator-tray/org.cryptomator.Cryptomator.tray-unlocked.svg"),
                    APP_INDICATOR_CATEGORY_APPLICATION_STATUS());
            var gtkSeparator = gtk_menu_item_new();
            var gtkMenu = gtk_menu_new();
            var gtkSubmenu = gtk_menu_new();
            var gtkMenuItem = gtk_menu_item_new();
            gtk_menu_item_set_label(gtkMenuItem, arena.allocateUtf8String("More"));
            var gtkSMenuItem = gtk_menu_item_new();
            gtk_menu_item_set_label(gtkSMenuItem, arena.allocateUtf8String("Change icon"));
            var gtkSMenuItem1 = gtk_menu_item_new();
            gtk_menu_item_set_label(gtkSMenuItem1, arena.allocateUtf8String("Quit"));
            gtk_menu_shell_append(gtkSubmenu, gtkSMenuItem);
            gtk_menu_shell_append(gtkSubmenu, gtkSeparator);
            gtk_menu_shell_append(gtkSubmenu, gtkSMenuItem1);
            gtk_menu_item_set_submenu(gtkMenuItem, gtkSubmenu);
            g_signal_connect_object(gtkSMenuItem1, arena.allocateUtf8String("activate"), GCallback.allocate(new QuitCallback(), arenaAuto), gtkMenu, 0);
            g_signal_connect_object(gtkSMenuItem, arena.allocateUtf8String("activate"), GCallback.allocate(new ChangeIconCallback(indicator), arenaAuto), gtkMenu, 0);
            gtk_menu_shell_append(gtkMenu, gtkMenuItem);
            gtk_widget_show_all(gtkMenu);
            app_indicator_set_menu(indicator, gtkMenu);
            app_indicator_set_attention_icon(indicator, arena.allocateUtf8String("indicator-messages-new"));
            app_indicator_set_status(indicator, APP_INDICATOR_STATUS_ACTIVE());
            launch();
        }
    }
}