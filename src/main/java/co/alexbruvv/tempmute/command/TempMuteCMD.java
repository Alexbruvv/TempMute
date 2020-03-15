package co.alexbruvv.tempmute.command;

import co.alexbruvv.tempmute.common.TimeUnit;
import co.alexbruvv.tempmute.data.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Permission: tempmute.mute

public class TempMuteCMD implements CommandExecutor
{

    public boolean onCommand(CommandSender sender, Command command, String name, String[] args)
    {
        // Handle permission check
        if (sender instanceof Player)
        {
            Player player = (Player) sender;

            if (!player.hasPermission("tempmute.mute") && !player.isOp())
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to execute that command.");
                return true;
            }
        }

        if (args.length != 2)
        {
            sender.sendMessage(ChatColor.RED + "Usage: /tempmute <Player> <Time>");
            sender.sendMessage(ChatColor.GREEN + "Time units: s,m,h,d (seconds, minutes, hours, days)");
            sender.sendMessage(ChatColor.GREEN + "Example: /tempmute Notch 1d");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        String time = args[1];
        int length = time.length();
        int unitCodePosition = length - 1;
        char unitCode = time.charAt(unitCodePosition);

        TimeUnit unit = TimeUnit.fromCode(unitCode);

        if (unit == null)
        {
            sender.sendMessage(ChatColor.GREEN + "Time units: s,m,h,d (seconds, minutes, hours, days)");
            return true;
        }

        long unitQuantity = Long.parseLong(time.substring(0, length - 1));

        long duration = unitQuantity * unit.getMillis();

        Configuration config = Configuration.MainConfig;

        if (!config.contains(target.getUniqueId().toString()))
            config.createSection(target.getUniqueId().toString());

        config.set(target.getUniqueId() + ".muted", true);
        config.set(target.getUniqueId() + ".endTime", System.currentTimeMillis() + duration);

        String formattedTime = unitQuantity + " " + (unitQuantity == 1 ? unit.getName().substring(0, unit.getName().length() - 1) : unit.getName());

        sender.sendMessage(ChatColor.GREEN + target.getName() + " has been muted for " + formattedTime + ".");

        if (target.isOnline())
        {
            target.getPlayer().sendMessage(ChatColor.RED + "You have been muted for " + formattedTime + ".");
        }

        return true;
    }

}
