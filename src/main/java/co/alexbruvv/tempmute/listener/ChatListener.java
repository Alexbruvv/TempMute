package co.alexbruvv.tempmute.listener;

import co.alexbruvv.tempmute.data.Configuration;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener
{

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();

        Configuration config = Configuration.MainConfig;

        if (!config.contains(uuid))
            return;

        boolean muted = config.<Boolean>get(uuid + ".muted");
        long endTime = config.<Long>get(uuid + ".endTime");

        if (muted && endTime > System.currentTimeMillis())
        {
            event.getPlayer().sendMessage(ChatColor.RED + "You are currently muted.");
            event.setCancelled(true);
            return;
        }

        if (muted)
        {
            config.set(uuid + ".muted", false);
        }
    }

}
