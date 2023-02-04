package main;

import gui.Option;


public class Settings {
    public static String on = " [·█]";
    public static String off = " [█·]";

    public static Setting DEVMODE;
    public static Setting SHOWACTION;

    public Settings() {
        DEVMODE = new Setting(false);
        SHOWACTION = new Setting(true);
    }

    public class Setting {
        private boolean enabled;
        private String visual;
        public Setting(boolean enabled) {
            this.enabled = enabled;
            if (enabled) visual = on;
            else visual = off;
        }
        public String toggle() {
            enabled = !enabled;
            if (enabled) visual = on;
            else visual = off;
            return visual;
        }
        public String getVisual() {
            if (enabled) visual = on;
            else visual = off;
            return visual;
        }
        public boolean isEnabled() {
            return enabled;
        }
    }


    public static class SettingToggle extends Option {
        private Setting setting;
        private String title;

        public SettingToggle(String name, Setting setting) {
            super(-1, name+setting.getVisual());
            this.title = name;
            this.setting = setting;
        }

        public void toggle(int index) {
            this.name = this.title+setting.toggle();
            Main.currMenu.rows[4+index] = Main.currMenu.makeOptionRow(true, index);
            Main.screen.printRows();
        }
    }

}


