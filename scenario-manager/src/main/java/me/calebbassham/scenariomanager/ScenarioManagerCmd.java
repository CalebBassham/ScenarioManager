package me.calebbassham.scenariomanager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static me.calebbassham.pluginmessageformat.PluginMessageFormat.*;

public class ScenarioManagerCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                if (ScenarioManager.getScenarios().isEmpty()) {
                    sender.sendMessage(getPrefix() + "There are no scenarios.");
                    return true;
                }

                String scenarios = ScenarioManager.getScenarios().stream()
                        .map(Scenario::getName)
                        .sorted()
                        .collect(Collectors.joining(", "));
                sender.sendMessage(getPrefix() + scenarios);
                return true;
            }
        }

        if (args.length >= 2) {

            if (args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("disable")) {

                LinkedList<String> a = new LinkedList<>(Arrays.asList(args));
                a.poll();

                StringBuilder scenName = new StringBuilder();
                Scenario scenario = null;
                while (scenario == null) {
                    scenName.append(a.poll());

                    scenario = ScenarioManager.getScenario(scenName.toString());
                    if (scenario != null) break;

                    if (a.isEmpty()) {
                        sender.sendMessage(getErrorPrefix() + scenName.toString() + " is not a scenario.");
                        return true;
                    }

                    scenName.append(" ");
                }

                boolean enable = args[0].equalsIgnoreCase("enable");
                scenario.setEnabled(enable);
                sender.sendMessage(getPrefix() + getMainColorPallet().getHighlightTextColor() + scenName.toString() + getMainColorPallet().getPrimaryTextColor() +
                        " has been " + getMainColorPallet().getValueTextColor() + args[0] + "d" + getMainColorPallet().getPrimaryTextColor() + ".");
            }

            return true;
        }

        sender.sendMessage(getErrorPrefix() + "Bad usage."); // TODO

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.stream(new String[]{"list", "enable", "disable"})
                    .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                    .sorted()
                    .collect(Collectors.toList());
        }

        if (args.length > 1) {
            if (args[0].equalsIgnoreCase("enable")) {
                return ScenarioManager.getScenarios().stream()
                        .filter(scenario -> !scenario.isEnabled())
                        .map(Scenario::getName)
                        .filter(name -> name.toLowerCase().startsWith(String.join(" ", Arrays.copyOfRange(args, 1, args.length)).toLowerCase()))
                        .map(name -> name.split(" "))
                        .map(parts -> Arrays.stream(parts, args.length - 2, parts.length).collect(Collectors.joining(" ")))
                        .collect(Collectors.toList());
            }

            if (args[0].equalsIgnoreCase("disable")) {
                return ScenarioManager.getScenarios().stream()
                        .filter(Scenario::isEnabled)
                        .map(Scenario::getName)
                        .filter(name -> name.toLowerCase().startsWith(String.join(" ", Arrays.copyOfRange(args, 1, args.length)).toLowerCase()))
                        .map(name -> name.split(" "))
                        .map(parts -> Arrays.stream(parts, args.length - 2, parts.length).collect(Collectors.joining(" ")))
                        .collect(Collectors.toList());
            }
        }

        return Collections.emptyList();
    }

}
