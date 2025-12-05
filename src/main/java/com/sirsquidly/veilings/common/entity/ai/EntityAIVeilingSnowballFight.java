package com.sirsquidly.veilings.common.entity.ai;

import com.sirsquidly.veilings.common.entity.AbstractVeiling;
import com.sirsquidly.veilings.util.veilingItemUse.IVeilingItemUse;
import com.sirsquidly.veilings.util.veilingItemUse.VeilingItemUseRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;

public class EntityAIVeilingSnowballFight extends EntityAIVeilingUseItem
{
    private final AbstractVeiling veiling;
    private final World world;
    private int snowballThrowTime;
    private int snowballQuantity;
    private Entity victim;

    public EntityAIVeilingSnowballFight(AbstractVeiling veilingIn)
    {
        super(veilingIn);
        this.veiling = veilingIn;
        this.world = veilingIn.world;
    }

    public boolean shouldExecute()
    { return this.veiling.snowballFighting; }

    public void startExecuting()
    {
        this.snowballThrowTime = 5;
        this.snowballQuantity = this.world.rand.nextInt(60) + 6;
        this.victim = this.getNearbyRandomEntity(this.world, this.veiling,5);
    }

    public void updateTask()
    {
        --this.snowballThrowTime;

        ItemStack heldItem = this.veiling.getHeldItemMainhand();

        if (victim != null) this.veiling.getLookHelper().setLookPositionWithEntity(victim, 30F, 30F);

        if (heldItem.getItem() == Items.SNOWBALL)
        {
            if (this.snowballThrowTime > 0) return;

            if (victim != null)
            {
                IVeilingItemUse behavior = VeilingItemUseRegistry.get(heldItem.getItem());

                if (behavior != null)
                {
                    behavior.onItemUseFinish(this.veiling, heldItem, 999999);
                }

                this.snowballThrowTime = this.world.rand.nextInt(10) + 5;
                --this.snowballQuantity;

                victim = this.getNearbyRandomEntity(this.world, this.veiling,5);
            }
            else
            { victim = this.getNearbyRandomEntity(this.world, this.veiling,5); }
        }
        else
        {
            BlockPos blockpos = new BlockPos(this.veiling.posX, this.veiling.posY, this.veiling.posZ);
            IBlockState iblockstate = this.world.getBlockState(blockpos);

            if (iblockstate.getBlock() == Blocks.SNOW_LAYER)
            {
                if (!world.isRemote)
                {
                    this.veiling.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.SNOWBALL));
                }

                this.veiling.playSound(SoundEvents.BLOCK_SNOW_BREAK, 1.0F, 1.0F);
                ((WorldServer)this.world).spawnParticle(
                        EnumParticleTypes.BLOCK_CRACK,
                        this.veiling.posX, this.veiling.posY, this.veiling.posZ,
                        10,
                        0.2D, 0.4D, 0.2D,
                        0.05D,  Block.getStateId(iblockstate)
                );
            }
        }


        if (snowballQuantity <= 0)
        {
            this.veiling.shiftHappiness(40);
            this.veiling.snowballFighting = false;
            this.veiling.setPlayCooldown(6000);
        }
    }

    public Entity getNearbyRandomEntity(World world, AbstractVeiling veiling, double expandByRange)
    {
        List<Entity> list = world.getEntitiesWithinAABB(Entity.class, veiling.getEntityBoundingBox().grow(expandByRange));
        list.remove(veiling);
        if (list.isEmpty()) return null;

        return list.get(world.rand.nextInt(list.size()));
    }
}