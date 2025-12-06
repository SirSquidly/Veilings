package com.sirsquidly.veilings.common.item;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.common.entity.EntityVeilingCustodian;
import com.sirsquidly.veilings.common.entity.EntityVeilingDeft;
import com.sirsquidly.veilings.common.entity.EntityVeilingDramatist;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemVeilingEgg extends Item
{
    public ItemVeilingEgg()
    {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.MATERIALS);
    }

    public boolean onEntityItemUpdate(EntityItem entityItem)
    {
        World world = entityItem.world;

        // Check if item is in water
        if (entityItem.isWet())
        {
            if (!world.isRemote)
            {
                ((WorldServer)world).spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, entityItem.posX, entityItem.posY, entityItem.posZ,
                        200, 0.25D, 0.25D, 0.25D, 0.05D);

                AbstractVeiling randomVeiling = getRandomVeiling(world);
                AbstractVeiling veiling = randomVeiling.multiplyLogic.generateNewVeiling(world, randomVeiling);

                veiling.setPosition(entityItem.posX, entityItem.posY, entityItem.posZ);
                world.spawnEntity(veiling);
                veiling.multiplyLogic.imprintOnNearestPlayer(veiling);
                entityItem.setDead();

                world.playSound(null, entityItem.posX, entityItem.posY, entityItem.posZ, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
            return true;
        }
        return false;
    }

    /** Literally just gets a random Veiling type. */
    public AbstractVeiling getRandomVeiling(World world)
    {
        switch (world.rand.nextInt(3))
        {
            case 0: return new EntityVeilingDeft(world);
            case 1: return new EntityVeilingCustodian(world);
            case 2: return new EntityVeilingDramatist(world);
        }

        return null;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings.false_egg.desc1"));
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("description.veilings.false_egg.desc2"));
    }
}