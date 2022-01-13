package net.zestyblaze.lycanthropy.common.registry;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.TranslatableText;
import net.zestyblaze.lycanthropy.Lycanthropy;
import net.zestyblaze.lycanthropy.common.component.LycanthropyPlayerComponent;

public class LycanthropyCommandInit {
    public static void registerCommands(){
        CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> dispatcher.register(CommandManager.literal(Lycanthropy.MODID).requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(3))
        .then(CommandManager.literal("get")
            .then(CommandManager.argument("player", EntityArgumentType.player()).executes(context -> {
                PlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                LycanthropyPlayerComponent lycanthropyPlayerComponent = LycanthropyComponentInit.WEREWOLF.get(player);
                context.getSource().sendFeedback(new TranslatableText("commands.lycanthropy.get"+lycanthropyPlayerComponent.getCanBecomeWerewolf(), player.getEntityName()), false);
                return Command.SINGLE_SUCCESS;
            })))
        .then(CommandManager.literal("set")
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .then(CommandManager.literal("transform").executes(context -> {
                    PlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                    LycanthropyPlayerComponent lycanthropyPlayerComponent = LycanthropyComponentInit.WEREWOLF.get(player);
                    lycanthropyPlayerComponent.setIsWerewolf(!lycanthropyPlayerComponent.getIsWerewolf());
                    lycanthropyPlayerComponent.setCanBecomeWerewolf(!lycanthropyPlayerComponent.getCanBecomeWerewolf());
                    context.getSource().sendFeedback(new TranslatableText("commands.lycanthropy.set"+lycanthropyPlayerComponent.getCanBecomeWerewolf(), player.getEntityName()), false);
                    return Command.SINGLE_SUCCESS;
                }))))
        .then(CommandManager.literal("level")
            .then(CommandManager.literal("set").then(CommandManager.argument("player", EntityArgumentType.player())
                .then(CommandManager.argument("level", IntegerArgumentType.integer(0, 10)).executes(context -> {
                    PlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                    int level = IntegerArgumentType.getInteger(context, "level");
                    LycanthropyComponentInit.WEREWOLF.maybeGet(player).ifPresent(lycanthropyPlayerComponent -> lycanthropyPlayerComponent.setWerewolfLevel(level));
                    context.getSource().sendFeedback(new TranslatableText("commands.lycanthropy.level", player.getEntityName(), level), true);
                    return Command.SINGLE_SUCCESS;
                }))))
        .then(CommandManager.literal("get")
            .then(CommandManager.argument("player", EntityArgumentType.player()).executes(context -> {
                PlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                LycanthropyPlayerComponent lycanthropyPlayerComponent = LycanthropyComponentInit.WEREWOLF.get(player);
                context.getSource().sendFeedback(new TranslatableText("commands.lycanthropy.level.get"+lycanthropyPlayerComponent.getWerewolfLevel(), player.getEntityName()), false);
                return Command.SINGLE_SUCCESS;
            })))))));
    }
}
