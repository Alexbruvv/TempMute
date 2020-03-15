package co.alexbruvv.tempmute.data;

import co.alexbruvv.tempmute.TempMute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Configuration
{

    public static Configuration MainConfig;

    private File file;
    private FileConfiguration config;

    public Configuration(String fileName)
    {
        if (!TempMute.getPlugin().getDataFolder().exists())
            TempMute.getPlugin().getDataFolder().mkdir();

        file = new File(TempMute.getPlugin().getDataFolder(), fileName + ".yml");

        if (file.exists())
        {
            try
            {
                file.createNewFile();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public <Type> Type get(String path)
    {
        return (Type) config.get(path);
    }

    public void set(String path, Object value)
    {
        config.set(path, value);
        save();
    }

    public boolean contains(String path)
    {
        return config.contains(path);
    }

    public ConfigurationSection createSection(String path)
    {
        ConfigurationSection section = config.createSection(path);
        save();
        return section;
    }

    public void save()
    {
        try
        {
            config.save(file);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
