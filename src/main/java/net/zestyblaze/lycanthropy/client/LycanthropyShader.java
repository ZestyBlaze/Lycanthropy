package net.zestyblaze.lycanthropy.client;

import net.minecraft.client.render.*;
import net.minecraft.resource.ResourceManager;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;


public class LycanthropyShader  {
        private static Shader werewolfVision;

        public static void init(ResourceManager resourceManager, List<Pair<Shader, Consumer<Shader>>> registrations) throws IOException {
            registrations.add(Pair.of(
            new Shader(resourceManager, "lycanthropy__werewolfvision", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL),
            inst -> werewolfVision = inst));
        }
        public static Shader werewolfVision() {
            return werewolfVision;
        }
}

