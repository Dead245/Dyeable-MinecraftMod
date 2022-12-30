package dyeableknitknacks.items;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.client.event.RenderHighlightEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class dyeGadget extends Item {
    public dyeGadget(Properties properties) {
        super(properties);
    }

    int entityID = 1;

    void setEntityId(int id){ entityID = id;}

    @Override
    public InteractionResult useOn(UseOnContext p_41427_) {
        return super.useOn(p_41427_);
    }

    //Open item GUI/change dye | Change dye of block |  Change dye of entity(sheep)
    @Override
    public InteractionResultHolder<ItemStack> use(Level lvl, Player plyr, InteractionHand intrHand) {
        if(!lvl.isClientSide && entityID < 0){
            BlockHitResult ray = getPlayerPOVHitResult(lvl,plyr, ClipContext.Fluid.NONE);
            BlockPos lookPos = ray.getBlockPos();
            Block blkType = lvl.getBlockState(lookPos).getBlock();
            if(blkType == Blocks.AIR){
                plyr.sendSystemMessage(Component.literal("Right clicked in the air."));
            } else{
                plyr.sendSystemMessage(Component.literal("Right clicked on Block: "+ blkType.getName().getString()));
            }
        } else if (!lvl.isClientSide && entityID >= 0) {
            LivingEntity liveEnt = (LivingEntity) lvl.getEntity(entityID); //easier to get entity name through LivingEntity???
            plyr.sendSystemMessage(Component.literal("Right clicked on Entity: " + liveEnt.getName().getString()));
        }
        entityID = -1; //basically null?
        return super.use(lvl, plyr, intrHand);
    }

    //Get ID of entity of right-clicked on one.
    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player plyr, LivingEntity liveEntity, InteractionHand intrHand) {
        setEntityId(liveEntity.getId());
        return super.interactLivingEntity(itemStack, plyr, liveEntity, intrHand);
    }


    //Tooltip
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level lvl, List<Component> compList, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, lvl, compList, tooltipFlag);
        compList.add(Component.literal("Gadget for dyes..."));
    }

}
