package co.alexbruvv.tempmute;

import co.alexbruvv.tempmute.command.TempMuteCMD;
import co.alexbruvv.tempmute.command.UnmuteCMD;
import co.alexbruvv.tempmute.data.Configuration;
import co.alexbruvv.tempmute.listener.ChatListener;
import org.bukkit.plugin.java.JavaPlugin;

public class TempMute extends JavaPlugin
{

    private static TempMute plugin;

    public void onEnable()
    {
        plugin = this;

        Configuration.MainConfig = new Configuration("config");

        getCommand("tempmute").setExecutor(new TempMuteCMD());
        getCommand("unmute").setExecutor(new UnmuteCMD());

        getServer().getPluginManager().registerEvents(new ChatListener(), this);
    }

    public static TempMute getPlugin()
    {
        return plugin;
    }

}
