package dyeableknitknacks.init;

import dyeableknitknacks.DyeableKnitKnacks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class itemInit {
    public static final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, DyeableKnitKnacks.modid);

    public static final RegistryObject<Item> newItem = items.register("example_item", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
}
