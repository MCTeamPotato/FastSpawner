package me.kall.fastspawner.mixin.embeddium;

import me.jellysquid.mods.sodium.client.gui.SodiumGameOptionPages;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpact;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.options.control.ControlValueFormatter;
import me.jellysquid.mods.sodium.client.gui.options.control.SliderControl;
import me.jellysquid.mods.sodium.client.gui.options.storage.SodiumOptionsStorage;
import me.kall.fastspawner.FastSpawner;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(value = SodiumGameOptionPages.class, remap = false)
public abstract class SodiumGameOptionPagesMixin {
    @Shadow @Final private static SodiumOptionsStorage sodiumOpts;

    @Inject(method = "performance", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", remap = false, ordinal = 0, shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void insert(CallbackInfoReturnable<OptionPage> cir, @NotNull List<OptionGroup> groups) {
        OptionImpl<SodiumGameOptions, Integer> particle = OptionImpl.createBuilder(Integer.TYPE, sodiumOpts)
                .setName(Component.translatable("fastspawner.particle.name"))
                .setTooltip(Component.translatable("fastspawner.particle.tooltip"))
                .setControl(option -> new SliderControl(option, 1, 100, 3, ControlValueFormatter.number()))
                .setBinding((sodiumGameOptions, integer) -> FastSpawner.PARTICLE_RENDERING_PERCENT.set(integer), sodiumGameOptions -> FastSpawner.PARTICLE_RENDERING_PERCENT.get())
                .setImpact(OptionImpact.VARIES)
                .build();
        groups.add(OptionGroup.createBuilder().add(particle).build());
    }
}
