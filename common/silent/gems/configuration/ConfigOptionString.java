package silent.gems.configuration;

import net.minecraftforge.common.Configuration;
import silent.gems.core.util.LogHelper;

public class ConfigOptionString extends ConfigOption {

    public String value;
    public final String defaultValue;

    public ConfigOptionString(String name, String defaultValue) {

        this.name = name;
        value = "";
        this.defaultValue = defaultValue;
    }

    @Override
    public ConfigOption loadValue(Configuration c, String category) {

        value = c.get(category, name, defaultValue).getString();
        return this;
    }

    @Override
    public ConfigOption loadValue(Configuration c, String category, String comment) {

        value = c.get(category, name, defaultValue, comment).getString();
        return this;
    }

    @Override
    public ConfigOption validate() {

        // Enable sounds
        if (false) {
            // derp
        }
        // Mistake catcher
        else {
            LogHelper.warning("No validation code for config setting " + name + " found!");
        }

        return this;
    }

}