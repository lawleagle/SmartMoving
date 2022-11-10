package net.smart.moving.test;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;

public class SmartMovingTestCommand extends CommandBase {
	
	@Override
	public String getCommandName() {
		return "smartmovingtest";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "";
	}
	
	public int getRequiredPermissionLevel()
	{
		return 0;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length == 1) {
			if(args[0].equals("run")) {
				sender.addChatMessage(new ChatComponentText(">> Starting test. Keep the game focused, and don't touch anything! I hope you're on a superflat world with a spawn-proof block."));
				SmartMovingTestMod.instance.startTest();
				return;
			}
		}
		throw new WrongUsageException(getCommandName() + " run");
	}
	
}
