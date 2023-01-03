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
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class dyeGadget extends Item {
    public dyeGadget(Properties properties) {
        super(properties);
    }

    //The 16 colors used for dyeable blocks (swapped light_grey and grey for easier searchability)
    String[] colors = { "white","orange","magenta","light_blue",
                        "yellow","lime","pink","light_gray",
                        "gray","cyan","purple","blue",
                        "brown","green","red","black" };

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
                //IS NOTHING
                plyr.sendSystemMessage(Component.literal("Right clicked in the air."));
            } else{
                //IS BLOCK
                String blockName = blkType.getDescriptionId().substring(blkType.getDescriptionId().lastIndexOf('.') + 1);
                plyr.sendSystemMessage(Component.literal("Right clicked on Block: " + blockName ));
                int filterIndex, colorIndex;
                String blockColor = null;

                //----- Gets Block Type
                for(filterIndex = 0; filterIndex < blockFilter.length; filterIndex++) {
                    if(blockName.contains(blockFilter[filterIndex])){
                        //----- Gets blockColor if blockName contains something in blockFilter
                        for(colorIndex = 0; colorIndex < colors.length; colorIndex++){
                            if(blockName.contains(colors[colorIndex])){
                                blockColor = colors[colorIndex];
                                break;
                            }
                        }
                        plyr.sendSystemMessage(Component.literal("Color: " + blockColor ));
                        break;
                    }
                }
                switch(filterIndex) {
                    case 0: //Concrete / Concrete Powder
                        if(blockName.contains("powder")){
                            plyr.sendSystemMessage(Component.literal("IS DYEABLE CONCRETE POWDER"));
                            break;
                        }
                        plyr.sendSystemMessage(Component.literal("IS DYEABLE CONCRETE"));
                        break;
                    case 1: //Terracotta / Glazed Terracotta
                        if(blockName.contains("glazed")){
                            plyr.sendSystemMessage(Component.literal("IS DYEABLE GLAZED TERRACOTTA"));
                            break;
                        }
                        plyr.sendSystemMessage(Component.literal("IS DYEABLE TERRACOTTA"));
                        break;
                    case 2: //Wool
                        plyr.sendSystemMessage(Component.literal("IS DYEABLE WOOL"));
                        break;
                    case 3: //Candle
                        plyr.sendSystemMessage(Component.literal("IS DYEABLE CANDLE"));
                        break;
                    case 4: //Stained Glass / Stained Glass Pane
                        if(blockName.contains("pane")){
                            plyr.sendSystemMessage(Component.literal("IS DYEABLE GLASS PANE"));
                            break;
                        }
                        plyr.sendSystemMessage(Component.literal("IS DYEABLE GLASS"));
                        break;
                    case 5: //Carpet
                        if(blockName.equals("moss_carpet")) break; //moss carpet exception
                        plyr.sendSystemMessage(Component.literal("IS DYEABLE CARPET"));
                        break;
                    default: //Not Dyeable
                        break;
                }
            }
        } else if (!lvl.isClientSide && entityID >= 0) {
            //IS ENTITY
            LivingEntity liveEnt = (LivingEntity) lvl.getEntity(entityID); //easier to get entity name through LivingEntity???
            plyr.sendSystemMessage(Component.literal("Right clicked on Entity: " + liveEnt.getName().getString()));
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

}
