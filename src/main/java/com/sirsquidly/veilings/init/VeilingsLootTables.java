package com.sirsquidly.veilings.init;

import com.sirsquidly.veilings.veilings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class VeilingsLootTables
{
    public static final ResourceLocation VEILING_CUSTODIAN_DROPS = new ResourceLocation(veilings.MOD_ID, "entities/veiling_custodian.json");
    public static final ResourceLocation VEILING_DEFT_DROPS = new ResourceLocation(veilings.MOD_ID, "entities/veiling_deft.json");
    public static final ResourceLocation VEILING_DRAMATIST_DROPS = new ResourceLocation(veilings.MOD_ID, "entities/veiling_dramatist.json");
    public static final ResourceLocation WICKED_VEILING_CUSTODIAN_DROPS = new ResourceLocation(veilings.MOD_ID, "entities/wicked_veiling_custodian.json");
    public static final ResourceLocation WICKED_VEILING_DEFT_DROPS = new ResourceLocation(veilings.MOD_ID, "entities/wicked_veiling_deft.json");
    public static final ResourceLocation WICKED_VEILING_DRAMATIST_DROPS = new ResourceLocation(veilings.MOD_ID, "entities/wicked_veiling_dramatist.json");

    public static final ResourceLocation NETHER_FORTRESS_CHEST_INJECT = new ResourceLocation(veilings.MOD_ID, "nether_fortress_inject");

    public static void registerLootTables()
    {
        LootTableList.register(VEILING_CUSTODIAN_DROPS);
        LootTableList.register(VEILING_DEFT_DROPS);
        LootTableList.register(VEILING_DRAMATIST_DROPS);
        LootTableList.register(WICKED_VEILING_CUSTODIAN_DROPS);
        LootTableList.register(WICKED_VEILING_DEFT_DROPS);
        LootTableList.register(WICKED_VEILING_DRAMATIST_DROPS);

        LootTableList.register(NETHER_FORTRESS_CHEST_INJECT);
    }
}