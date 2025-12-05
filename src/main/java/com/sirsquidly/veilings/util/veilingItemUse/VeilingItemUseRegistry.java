package com.sirsquidly.veilings.util.veilingItemUse;

import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class VeilingItemUseRegistry
{
    private static final Map<Item, IVeilingItemUse> MAP = new HashMap<>();

    public static void register(Item item, IVeilingItemUse behavior)
    {
        MAP.put(item, behavior);
    }

    public static IVeilingItemUse get(Item item)
    { return MAP.get(item); }
}