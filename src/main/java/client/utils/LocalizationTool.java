package client.utils;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class LocalizationTool {
    private ResourceBundle resources = null;

    public ResourceBundle getBundle() {
        return resources;
    }

    public final ResourceBundle getResources() {
        return getBundle();
    }

    public final void setResources(ResourceBundle resources) {
        this.resources = resources;
    }

    public String getStringBinding(String key) {
        return resources.getString(key);
    }
}
