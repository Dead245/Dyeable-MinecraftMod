package dyeableknitknacks.init;

import dyeableknitknacks.DyeableKnitKnacks;
import dyeableknitknacks.items.dyeGadget;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class itemInit {
    public static final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, DyeableKnitKnacks.modid);

    public static final RegistryObject<dyeGadget> dyeGadget = items.register("dye_gadget", () -> new dyeGadget(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
}
