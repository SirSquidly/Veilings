package com.sirsquidly.veilings.network;

import com.sirsquidly.veilings.veilings;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CFDPacketHandler
{
	public static final SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel(veilings.MOD_ID);
	
	public static void registerMessages()
	{
		int messageId = 0;

		//CHANNEL.registerMessage(OEPacketRiptide.Handler.class, OEPacketRiptide.class, messageId++, Side.CLIENT);
	}
}
