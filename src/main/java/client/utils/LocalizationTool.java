package client.utils;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ResourceBundle;

public class LocalizationTool {
    private ObjectProperty<ResourceBundle> resources = new SimpleObjectProperty<>();

    public ObjectProperty<ResourceBundle> getBundle() {
        return resources;
    }

    public final ResourceBundle getResources() {
        return getBundle().get();
    }

    public final void setResources(ResourceBundle resources) {
        getBundle().set(resources);
    }

    public StringBinding getStringBinding(String key) {
        return new StringBinding() {
            {
                bind(getBundle());
            }
            @Override
            public String computeValue() {
                return getResources().getString(key);
            }
        };
    }
}
