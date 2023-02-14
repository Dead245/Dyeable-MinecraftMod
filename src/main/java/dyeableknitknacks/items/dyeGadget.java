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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class dyeGadget extends Item {
    public dyeGadget(Properties properties) {
        super(properties);
    }

    //There's gotta be an easier way, right?
    Block[] woolList = {Blocks.WHITE_WOOL,Blocks.ORANGE_WOOL,Blocks.MAGENTA_WOOL,Blocks.LIGHT_BLUE_WOOL,
            Blocks.YELLOW_WOOL,Blocks.LIME_WOOL,Blocks.PINK_WOOL,Blocks.GRAY_WOOL,
            Blocks.LIGHT_GRAY_WOOL,Blocks.CYAN_WOOL,Blocks.PURPLE_WOOL,Blocks.BLUE_WOOL,
            Blocks.BROWN_WOOL,Blocks.GREEN_WOOL,Blocks.RED_WOOL,Blocks.BLACK_WOOL};
    Block[] carpetList = {Blocks.WHITE_CARPET,Blocks.ORANGE_CARPET,Blocks.MAGENTA_CARPET,Blocks.LIGHT_BLUE_CARPET,
            Blocks.YELLOW_CARPET,Blocks.LIME_CARPET,Blocks.PINK_CARPET,Blocks.GRAY_CARPET,
            Blocks.LIGHT_GRAY_CARPET,Blocks.CYAN_CARPET,Blocks.PURPLE_CARPET,Blocks.BLUE_CARPET,
            Blocks.BROWN_CARPET,Blocks.GREEN_CARPET,Blocks.RED_CARPET,Blocks.BLACK_CARPET};
    Block[] terracottaList = {Blocks.WHITE_TERRACOTTA,Blocks.ORANGE_TERRACOTTA,Blocks.MAGENTA_TERRACOTTA,Blocks.LIGHT_BLUE_TERRACOTTA,
            Blocks.YELLOW_TERRACOTTA,Blocks.LIME_TERRACOTTA,Blocks.PINK_TERRACOTTA,Blocks.GRAY_TERRACOTTA,
            Blocks.LIGHT_GRAY_TERRACOTTA,Blocks.CYAN_TERRACOTTA,Blocks.PURPLE_TERRACOTTA,Blocks.BLUE_TERRACOTTA,
            Blocks.BROWN_TERRACOTTA,Blocks.GREEN_TERRACOTTA,Blocks.RED_TERRACOTTA,Blocks.BLACK_TERRACOTTA};
    Block[] glazedTerracottaList = {Blocks.WHITE_GLAZED_TERRACOTTA,Blocks.ORANGE_GLAZED_TERRACOTTA,Blocks.MAGENTA_GLAZED_TERRACOTTA,Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA,
            Blocks.YELLOW_GLAZED_TERRACOTTA,Blocks.LIME_GLAZED_TERRACOTTA,Blocks.PINK_GLAZED_TERRACOTTA,Blocks.GRAY_GLAZED_TERRACOTTA,
            Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA,Blocks.CYAN_GLAZED_TERRACOTTA,Blocks.PURPLE_GLAZED_TERRACOTTA,Blocks.BLUE_GLAZED_TERRACOTTA,
            Blocks.BROWN_GLAZED_TERRACOTTA,Blocks.GREEN_GLAZED_TERRACOTTA,Blocks.RED_GLAZED_TERRACOTTA,Blocks.BLACK_GLAZED_TERRACOTTA};
    Block[] concreteList = {Blocks.WHITE_CONCRETE,Blocks.ORANGE_CONCRETE,Blocks.MAGENTA_CONCRETE,Blocks.LIGHT_BLUE_CONCRETE,
            Blocks.YELLOW_CONCRETE,Blocks.LIME_CONCRETE,Blocks.PINK_CONCRETE,Blocks.GRAY_CONCRETE,
            Blocks.LIGHT_GRAY_CONCRETE,Blocks.CYAN_CONCRETE,Blocks.PURPLE_CONCRETE,Blocks.BLUE_CONCRETE,
            Blocks.BROWN_CONCRETE,Blocks.GREEN_CONCRETE,Blocks.RED_CONCRETE,Blocks.BLACK_CONCRETE};
    Block[] concretePowderList = {Blocks.WHITE_CONCRETE_POWDER,Blocks.ORANGE_CONCRETE_POWDER,Blocks.MAGENTA_CONCRETE_POWDER,Blocks.LIGHT_BLUE_CONCRETE_POWDER,
            Blocks.YELLOW_CONCRETE_POWDER,Blocks.LIME_CONCRETE_POWDER,Blocks.PINK_CONCRETE_POWDER,Blocks.GRAY_CONCRETE_POWDER,
            Blocks.LIGHT_GRAY_CONCRETE_POWDER,Blocks.CYAN_CONCRETE_POWDER,Blocks.PURPLE_CONCRETE_POWDER,Blocks.BLUE_CONCRETE_POWDER,
            Blocks.BROWN_CONCRETE_POWDER,Blocks.GREEN_CONCRETE_POWDER,Blocks.RED_CONCRETE_POWDER,Blocks.BLACK_CONCRETE_POWDER};
    Block[] glassList = {Blocks.WHITE_STAINED_GLASS,Blocks.ORANGE_STAINED_GLASS,Blocks.MAGENTA_STAINED_GLASS,Blocks.LIGHT_BLUE_STAINED_GLASS,
            Blocks.YELLOW_STAINED_GLASS,Blocks.LIME_STAINED_GLASS,Blocks.PINK_STAINED_GLASS,Blocks.GRAY_STAINED_GLASS,
            Blocks.LIGHT_GRAY_STAINED_GLASS,Blocks.CYAN_STAINED_GLASS,Blocks.PURPLE_STAINED_GLASS,Blocks.BLUE_STAINED_GLASS,
            Blocks.BROWN_STAINED_GLASS,Blocks.GREEN_STAINED_GLASS,Blocks.RED_STAINED_GLASS,Blocks.BLACK_STAINED_GLASS};
    Block[] glassPaneList = {Blocks.WHITE_STAINED_GLASS_PANE,Blocks.ORANGE_STAINED_GLASS_PANE,Blocks.MAGENTA_STAINED_GLASS_PANE,Blocks.LIGHT_BLUE_STAINED_GLASS_PANE,
            Blocks.YELLOW_STAINED_GLASS_PANE,Blocks.LIME_STAINED_GLASS_PANE,Blocks.PINK_STAINED_GLASS_PANE,Blocks.GRAY_STAINED_GLASS_PANE,
            Blocks.LIGHT_GRAY_STAINED_GLASS_PANE,Blocks.CYAN_STAINED_GLASS_PANE,Blocks.PURPLE_STAINED_GLASS_PANE,Blocks.BLUE_STAINED_GLASS_PANE,
            Blocks.BROWN_STAINED_GLASS_PANE,Blocks.GREEN_STAINED_GLASS_PANE,Blocks.RED_STAINED_GLASS_PANE,Blocks.BLACK_STAINED_GLASS_PANE};
    Block[] candleList = {Blocks.WHITE_CANDLE,Blocks.ORANGE_CANDLE,Blocks.MAGENTA_CANDLE,Blocks.LIGHT_BLUE_CANDLE,
            Blocks.YELLOW_CANDLE,Blocks.LIME_CANDLE,Blocks.PINK_CANDLE,Blocks.GRAY_CANDLE,
            Blocks.LIGHT_GRAY_CANDLE,Blocks.CYAN_CANDLE,Blocks.PURPLE_CANDLE,Blocks.BLUE_CANDLE,
            Blocks.BROWN_CANDLE,Blocks.GREEN_CANDLE,Blocks.RED_CANDLE,Blocks.BLACK_CANDLE};
    Block[] bedList; //Not yet implemented

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

    //Change color | Change dye of block |  Change dye of entity (sheep)
    @Override
    public InteractionResultHolder<ItemStack> use(Level lvl, Player plyr, InteractionHand intrHand) {
        if(entityID < 0){
            BlockHitResult ray = getPlayerPOVHitResult(lvl,plyr, ClipContext.Fluid.NONE);
            BlockPos lookPos = ray.getBlockPos();
            Block blkType = lvl.getBlockState(lookPos).getBlock();
            if(blkType == Blocks.AIR){
                //----- IS NOTHING - SET DYE COLOR HERE
                dyeID++;
                dyeColor = DyeColor.byId(dyeID);
                if(dyeID != dyeColor.getId()){ //byID will automatically set ID > max length to 0
                    dyeID = 0;
                }

                if(!lvl.isClientSide){
                    plyr.sendSystemMessage(Component.literal("Color: " + dyeColor.getName()));
                }
            } else{
                //----- IS BLOCK
                String blockName = blkType.getDescriptionId().substring(blkType.getDescriptionId().lastIndexOf('.') + 1);
                checkAndSetBlock(lvl,lookPos,blockName);
                lvl.playSound(plyr, lookPos, SoundEvents.DYE_USE, SoundSource.PLAYERS,1.0f,1.0f);
            }
        } else if (entityID >= 0) {
            //----- IS ENTITY
            LivingEntity liveEnt = (LivingEntity) lvl.getEntity(entityID);
            //Mostly copied from DyeItem.java
            if (liveEnt instanceof Sheep sheep) {
                if (sheep.isAlive() && !sheep.isSheared() && sheep.getColor() != this.dyeColor) {
                    sheep.level.playSound(plyr, sheep, SoundEvents.DYE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
                    if(!lvl.isClientSide){
                        sheep.setColor(this.dyeColor);
                    }
                }
            }
        }
        entityID = -1; //basically null?
        return super.use(lvl, plyr, intrHand);
    }

    //----- Get ID of entity when right-clicked on
    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player plyr, LivingEntity liveEntity, InteractionHand intrHand) {
        entityID = liveEntity.getId();
        return super.interactLivingEntity(itemStack, plyr, liveEntity, intrHand);
    }


    //Tooltip
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level lvl, List<Component> compList, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, lvl, compList, tooltipFlag);
        compList.add(Component.literal("Current Color: " + DyeColor.byId(dyeID)));
    }

    void checkAndSetBlock(Level lvl, BlockPos blockPos, String blockName){
        int filterIndex;

        BlockState blkState; //For use for blocks with tags that need to be transferred over after dyeing.

        //----- Gets Block Type
        for(filterIndex = 0; filterIndex < blockFilter.length; filterIndex++) {
            if(blockName.contains(blockFilter[filterIndex])){
                break;
            }
        }
        switch(filterIndex) {
            case 0: //Concrete / Concrete Powder
                if(blockName.contains("powder")){
                    lvl.setBlockAndUpdate(blockPos, concretePowderList[dyeID].defaultBlockState());
                    break;
                }
                lvl.setBlockAndUpdate(blockPos, concreteList[dyeID].defaultBlockState());
                break;
            case 1: //Terracotta / Glazed Terracotta
                if(blockName.contains("glazed")){
                    lvl.setBlockAndUpdate(blockPos, glazedTerracottaList[dyeID].defaultBlockState());
                    break;
                }
                lvl.setBlockAndUpdate(blockPos, terracottaList[dyeID].defaultBlockState());
                break;
            case 2: //Wool
                lvl.setBlockAndUpdate(blockPos, woolList[dyeID].defaultBlockState());
                break;
            case 3: //Candle -- Note: Still extinguishes the candle if it was lit
                blkState = lvl.getBlockState(blockPos);
                lvl.setBlockAndUpdate(blockPos, candleList[dyeID].withPropertiesOf(blkState));
                break;
            case 4: //Stained Glass / Stained Glass Pane
                if(blockName.contains("pane")){
                    blkState = lvl.getBlockState(blockPos);
                    lvl.setBlockAndUpdate(blockPos, glassPaneList[dyeID].withPropertiesOf(blkState));
                    break;
                }
                lvl.setBlockAndUpdate(blockPos, glassList[dyeID].defaultBlockState());
                break;
            case 5: //Carpet
                if(blockName.equals("moss_carpet")) break; //moss carpet exception
                lvl.setBlockAndUpdate(blockPos, carpetList[dyeID].defaultBlockState());
                break;
            default: //Not Dyeable
                break;
        }
    }

}
