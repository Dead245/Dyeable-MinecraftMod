package dyeableknitknacks.items;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class dyeGadget extends Item {
    public dyeGadget(Properties properties) {
        super(properties);
    }

    //Open item GUI/change dye | Change dye of block?
    @Override
    public InteractionResultHolder<ItemStack> use(Level lvl, Player plyr, InteractionHand intrHand) {
        BlockHitResult ray = getPlayerPOVHitResult(lvl,plyr, ClipContext.Fluid.NONE);
        BlockPos lookPos = ray.getBlockPos();
        Block blkType = lvl.getBlockState(lookPos).getBlock();
        if(!lvl.isClientSide){
            if(blkType == Blocks.AIR){
                plyr.sendSystemMessage(Component.literal("Right clicked in the air."));
            } else{
                plyr.sendSystemMessage(Component.literal("Right clicked on "+ blkType.getName().getString()));
            }
        }
        return super.use(lvl, plyr, intrHand);
    }

    @Override
    public InteractionResult useOn(UseOnContext useContext) {
        //BlockPos lookPos = useContext.getClickedPos();
        //BlockState blkState = useContext.getLevel().getBlockState(lookPos);
        //Block blkType = blkState.getBlock();
        //useContext.getPlayer().sendSystemMessage(Component.literal("Clicked on " + blkType.toString()));

        return super.useOn(useContext);
    }

    //Change dye of entity(sheep)
    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player plyr, LivingEntity liveEntity, InteractionHand intrHand) {
        plyr.sendSystemMessage(Component.literal("Right clicked an entity."));
        return super.interactLivingEntity(itemStack, plyr, liveEntity, intrHand);

    }

    //Tooltip
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level lvl, List<Component> compList, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, lvl, compList, tooltipFlag);
        compList.add(Component.literal("Gadget for dyes..."));
    }
}
