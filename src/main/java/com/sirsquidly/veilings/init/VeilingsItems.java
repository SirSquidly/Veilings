package com.sirsquidly.veilings.init;

import com.sirsquidly.veilings.common.item.*;
import com.sirsquidly.veilings.veilings;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = veilings.MOD_ID)
public class VeilingsItems
{
    public static final List<Item> itemList = new ArrayList<Item>();

    public static final ItemArmor.ArmorMaterial MASK = EnumHelper.addArmorMaterial("veiling_mask_mat", veilings.MOD_ID + ":veiling_mask_mat", 10, new int[]{0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);

    public static Item VEILING_EGG = new ItemVeilingEgg();
    public static Item VEILING_ESSENCE = new Item().setCreativeTab(CreativeTabs.MISC);
    public static Item VEILING_WEAVE = new Item().setCreativeTab(CreativeTabs.MISC);

    public static Item VEILING_TIRAMISU = new ItemVeilingTreat(5, 0.6F, false).setPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 30 * 20, 0), 1.0F);
    public static Item COMMAND_SCEPTRE = new ItemCommandSceptre();
    public static Item CALLING_BELL = new ItemCallingBell();

    public static Item VEILING_MASK_DEFT = new ItemVeilingMask(MASK,0);
    public static Item VEILING_MASK_CUSTODIAN = new ItemVeilingMask(MASK,1);
    public static Item VEILING_MASK_DRAMATIST = new ItemVeilingMask(MASK,2);
    public static Item SPIRIT_DAGGER = new ItemSpiritDagger();

    public static Item VEILING_ARMOR_OUTFIT = new ItemVeilingOutfit(0, "armor", true);
    public static Item VEILING_JESTER_OUTFIT = new ItemVeilingOutfit(0, "jester", true);
    public static Item VEILING_MAID_OUTFIT = new ItemVeilingOutfit(0, "maid", true);
    public static Item VEILING_PONCHO_OUTFIT = new ItemVeilingOutfit(0, "poncho", true);
    public static Item VEILING_VEST_OUTFIT = new ItemVeilingOutfit(0, "vest", false);
    public static Item VEILING_WRAPS_OUTFIT = new ItemVeilingOutfit(0, "wraps", true);

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        itemReadyForRegister(VEILING_EGG, "false_fetus");
        itemReadyForRegister(VEILING_ESSENCE, "veiling_essence");
        itemReadyForRegister(VEILING_WEAVE, "veiling_weave");
        itemReadyForRegister(CALLING_BELL, "calling_bell");
        itemReadyForRegister(VEILING_TIRAMISU, "veiling_tiramisu");

        itemReadyForRegister(COMMAND_SCEPTRE, "command_sceptre");

        itemReadyForRegister(SPIRIT_DAGGER, "spirit_dagger");

        itemReadyForRegister(VEILING_MASK_DEFT, "mask_deft");
        itemReadyForRegister(VEILING_MASK_CUSTODIAN, "mask_custodian");
        itemReadyForRegister(VEILING_MASK_DRAMATIST, "mask_dramatist");

        itemReadyForRegister(VEILING_ARMOR_OUTFIT, "veiling_outfit_armor");
        itemReadyForRegister(VEILING_JESTER_OUTFIT, "veiling_outfit_jester");
        itemReadyForRegister(VEILING_MAID_OUTFIT, "veiling_outfit_maid");
        itemReadyForRegister(VEILING_PONCHO_OUTFIT, "veiling_outfit_poncho");
        itemReadyForRegister(VEILING_VEST_OUTFIT, "veiling_outfit_vest");
        itemReadyForRegister(VEILING_WRAPS_OUTFIT, "veiling_outfit_wraps");

        for (Item items : itemList) event.getRegistry().register(items);
    }

    public static Item itemReadyForRegister(Item item, String name)
    {
        if (name != null)
        {
            item.setTranslationKey(veilings.MOD_ID + "." + name);
            item.setRegistryName(name);
        }

        itemList.add(item);

        return item;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event)
    { for (Item items : itemList) veilings.proxy.registerItemRenderer(items, 0, "inventory"); }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerItemColourHandlers(final ColorHandlerEvent.Item event)
    {
        final ItemColors itemColors = event.getItemColors();

        final IItemColor veilingOutfitColor = (stack, tintIndex) ->
        {
            if (tintIndex == 0 && stack.getItem() instanceof ItemVeilingOutfit)
            { return ((ItemVeilingOutfit) stack.getItem()).getColor(stack); }

            return 0xFFFFFF;
        };

        itemColors.registerItemColorHandler(veilingOutfitColor, VeilingsItems.VEILING_ARMOR_OUTFIT);
        itemColors.registerItemColorHandler(veilingOutfitColor, VeilingsItems.VEILING_JESTER_OUTFIT);
        itemColors.registerItemColorHandler(veilingOutfitColor, VeilingsItems.VEILING_MAID_OUTFIT);
        itemColors.registerItemColorHandler(veilingOutfitColor, VeilingsItems.VEILING_PONCHO_OUTFIT);
        itemColors.registerItemColorHandler(veilingOutfitColor, VeilingsItems.VEILING_VEST_OUTFIT);
        itemColors.registerItemColorHandler(veilingOutfitColor, VeilingsItems.VEILING_WRAPS_OUTFIT);
    }
}