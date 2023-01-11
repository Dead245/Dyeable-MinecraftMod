package dyeableknitknacks.items;

import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class dyeGadget extends Item {
    public dyeGadget(Properties properties) {
        super(properties);
    }
    private int dyeID = 0;
    private DyeColor dyeColor = DyeColor.byId(dyeID);

    //Filter for finding the names of blocks that are dyed.
    String[] blockFilter = {"_concrete","_terracotta","_wool",
                            "_candle","stained_glass","_carpet"};
    @Override
    public InteractionResult useOn(UseOnContext p_41427_) {
        return super.useOn(p_41427_);
    }

    int entityID = -1;

    //Open item GUI/change dye | Change dye of block |  Change dye of entity (sheep)
    @Override
    public InteractionResultHolder<ItemStack> use(Level lvl, Player plyr, InteractionHand intrHand) {
        if(!lvl.isClientSide && entityID < 0){
            BlockHitResult ray = getPlayerPOVHitResult(lvl,plyr, ClipContext.Fluid.NONE);
            BlockPos lookPos = ray.getBlockPos();
            Block blkType = lvl.getBlockState(lookPos).getBlock();
            if(blkType == Blocks.AIR){
                //IS NOTHING - SET DYE COLOR HERE
                dyeID++;
                dyeColor = DyeColor.byId(dyeID);
                if(dyeID != dyeColor.getId()){ //byID will automatically set ID > max length to 0
                    dyeID = 0;
                }
            } else{
                //IS BLOCK
                String blockName = blkType.getDescriptionId().substring(blkType.getDescriptionId().lastIndexOf('.') + 1);
                checkAndSetBlock(lvl,lookPos,blockName);
            }
        } else if (!lvl.isClientSide && entityID >= 0) {
            //IS ENTITY
            LivingEntity liveEnt = (LivingEntity) lvl.getEntity(entityID); //easier to get entity name through LivingEntity???
            plyr.sendSystemMessage(Component.literal("Right clicked on Entity: " + liveEnt.getName().getString()));
            //Mostly copied from DyeItem.java
            if (liveEnt instanceof Sheep sheep) {
                if (sheep.isAlive() && !sheep.isSheared() && sheep.getColor() != this.dyeColor) {
                    sheep.level.playSound(plyr, sheep, SoundEvents.DYE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
                    sheep.setColor(this.dyeColor);
                }
            }

        }
        entityID = -1; //basically null?
        return super.use(lvl, plyr, intrHand);
    }

    //Get ID of entity of right-clicked on one.
    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player plyr, LivingEntity liveEntity, InteractionHand intrHand) {
        entityID = liveEntity.getId();
        return super.interactLivingEntity(itemStack, plyr, liveEntity, intrHand);
    }


    //Tooltip
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level lvl, List<Component> compList, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, lvl, compList, tooltipFlag);
        compList.add(Component.literal("Gadget for dyes..."));
    }

    void checkAndSetBlock(Level lvl, BlockPos blockPos, String blockName){
        int filterIndex;

        //----- Gets Block Type
        for(filterIndex = 0; filterIndex < blockFilter.length; filterIndex++) {
            if(blockName.contains(blockFilter[filterIndex])){
                break;
            }
        }
        switch(filterIndex) {
            case 0: //Concrete / Concrete Powder
                if(blockName.contains("powder")){
                    lvl.setBlockAndUpdate(blockPos, Blocks.ORANGE_CONCRETE_POWDER.defaultBlockState());
                    break;
                }
                lvl.setBlockAndUpdate(blockPos, Blocks.ORANGE_CONCRETE.defaultBlockState());
                break;
            case 1: //Terracotta / Glazed Terracotta
                if(blockName.contains("glazed")){
                    lvl.setBlockAndUpdate(blockPos, Blocks.ORANGE_GLAZED_TERRACOTTA.defaultBlockState());
                    break;
                }
                lvl.setBlockAndUpdate(blockPos, Blocks.ORANGE_TERRACOTTA.defaultBlockState());
                break;
            case 2: //Wool
                lvl.setBlockAndUpdate(blockPos, Blocks.ORANGE_WOOL.defaultBlockState());
                break;
            case 3: //Candle
                //Amount and lit tags aren't updated
                lvl.setBlockAndUpdate(blockPos, Blocks.ORANGE_CANDLE.defaultBlockState());
                break;
            case 4: //Stained Glass / Stained Glass Pane
                if(blockName.contains("pane")){
                    //sides aren't updated
                    lvl.setBlockAndUpdate(blockPos, Blocks.ORANGE_STAINED_GLASS_PANE.defaultBlockState());
                    break;
                }
                lvl.setBlockAndUpdate(blockPos, Blocks.ORANGE_STAINED_GLASS.defaultBlockState());
                break;
            case 5: //Carpet
                if(blockName.equals("moss_carpet")) break; //moss carpet exception
                lvl.setBlockAndUpdate(blockPos, Blocks.ORANGE_CARPET.defaultBlockState());
                break;
            default: //Not Dyeable
                break;
        }
    }

}
