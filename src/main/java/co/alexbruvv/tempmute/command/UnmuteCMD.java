package co.alexbruvv.tempmute.command;

import co.alexbruvv.tempmute.data.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Permission: tempmute.unmute

public class UnmuteCMD implements CommandExecutor
{

    public boolean onCommand(CommandSender sender, Command command, String name, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;

            if (!player.hasPermission("tempmute.unmute") && !player.isOp())
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to execute that command.");
                return true;
            }
        }

        if (args.length != 1)
        {
            sender.sendMessage(ChatColor.RED + "Usage: /unmute <Player>");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        Configuration config = Configuration.MainConfig;

        if (!config.contains(target.getUniqueId().toString()))
        {
            sender.sendMessage(ChatColor.RED + "That player has never been muted.");
            return true;
        }

        if (!config.<Boolean>get(target.getUniqueId().toString() + ".muted"))
        {
            sender.sendMessage(ChatColor.RED + "That player is not currently muted.");
            return true;
        }

        config.set(target.getUniqueId().toString() + ".muted", false);

        sender.sendMessage(ChatColor.GREEN + target.getName() + " is no longer muted.");

        if (target.isOnline())
        {
            target.getPlayer().sendMessage(ChatColor.GREEN + "You are no longer muted.");
        }

        return true;
    }

}
