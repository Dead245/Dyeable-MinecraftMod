package dyeableknitknacks.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class advancedItem extends Item {
    public advancedItem(Properties properties) {
        super(properties);
    }

    //Open item GUI/change dye
    @Override
    public InteractionResultHolder<ItemStack> use(Level lvl, Player plyr, InteractionHand intrHand) {
        plyr.sendSystemMessage(Component.literal("Testing Right Click Functionality..."));

        return super.use(lvl, plyr, intrHand);
    }

    //Change dye of block?
    @Override
    public InteractionResult useOn(UseOnContext useContext) {
        return super.useOn(useContext);
    }

    //Change dye of entity(sheep)
    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player plyr, LivingEntity liveEntity, InteractionHand intrHand) {
        return super.interactLivingEntity(itemStack, plyr, liveEntity, intrHand);

    }
}
