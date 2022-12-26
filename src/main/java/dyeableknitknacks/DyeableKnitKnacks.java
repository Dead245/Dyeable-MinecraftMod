package dyeableknitknacks;

import dyeableknitknacks.init.itemInit;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DyeableKnitKnacks.modid)
public class DyeableKnitKnacks {
    public static final String modid = "dyeableknitknacks";

    public DyeableKnitKnacks(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        itemInit.items.register(bus);
    }
}
