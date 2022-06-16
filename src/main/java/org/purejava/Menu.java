package org.purejava;

import com.canonical.DBusMenu;
import org.freedesktop.StatusNotifierItem;
import org.freedesktop.StatusNotifierItemMenuItemListener;
import org.freedesktop.dbus.exceptions.DBusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private Logger log = LoggerFactory.getLogger(Menu.class);

    private static final String SERVICE_NAME = "org.purejava.testmenu";

    private DBusMenu menu;
    private List<StatusNotifierItem.MenuItem> entries;

    public Menu() {
        entries = new ArrayList<>();
        entries.add(0, new StatusNotifierItem.MenuItem("Entry 1", new StatusNotifierItemMenuItemListener() {
            @Override
            public void onActivated() {

            }
        }));
        entries.add(1, new StatusNotifierItem.MenuItem("Entry 2", new StatusNotifierItemMenuItemListener() {
            @Override
            public void onActivated() {

            }
        }));
        try {
            menu = new DBusMenu(SERVICE_NAME, entries);
        } catch (DBusException e) {
            log.error(e.toString(), e.getCause());
        }
    }

    public DBusMenu getMenu() {
        return menu;
    }
}
