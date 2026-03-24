package com.daqem.grieflogger.command;

import com.daqem.grieflogger.GriefLogger;
import com.daqem.grieflogger.config.GriefLoggerConfig;
import com.daqem.grieflogger.player.GriefLoggerServerPlayer;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class InspectCommand implements ICommand {


    @Override
    public LiteralArgumentBuilder<CommandSourceStack> getCommand() {
        return Commands.literal("inspect")
                .requires(source -> source.hasPermission(GriefLoggerConfig.inspectPermission.get()))
                .executes(context -> inspect(context.getSource()));
    }//我在此处已经修改，但是进入游戏里面默认权限的玩家无法使用这条指令，配置文件中已经改为1了

    private static int inspect(CommandSourceStack source) {
        if (source.getPlayer() instanceof GriefLoggerServerPlayer player) {
            player.grieflogger$setInspecting(!player.grieflogger$isInspecting());
            source.sendSuccess(() -> GriefLogger.translate("commands.inspect." + (player.grieflogger$isInspecting() ? "enabled" : "disabled"), GriefLogger.getName()), false);
        }
        return 1;
    }
}
