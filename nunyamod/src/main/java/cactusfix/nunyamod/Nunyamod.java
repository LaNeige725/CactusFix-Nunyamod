package cactusfix.nunyamod;

import net.fabricmc.api.ModInitializer;
// NEW IMPORT for Client Commands
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Nunyamod implements ModInitializer {
    public static final String MOD_ID = "nunyamod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // We switch from CommandRegistrationCallback to ClientCommandRegistrationCallback
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            
            // We switch from Commands.literal to ClientCommandManager.literal
            dispatcher.register(ClientCommandManager.literal("lbcalc")
                .then(ClientCommandManager.argument("percent", StringArgumentType.word())
                .then(ClientCommandManager.argument("price", StringArgumentType.word())
                    .executes(context -> {
                        String pStr = StringArgumentType.getString(context, "percent");
                        String vStr = StringArgumentType.getString(context, "price");
                        
                        try {
                            double pct = Double.parseDouble(pStr.replace("%", ""));
                            long val = parseAbbreviatedNumber(vStr);

                            if (val == -1) {
                                context.getSource().sendError(Component.literal("Invalid price format!"));
                                return 0;
                            }

                            long res = (long) (val * (1 - (pct / 100.0)));
                            String resultText = "Offer: " + String.format("%,d", res);
                            
                            // ClientCommandSource uses sendFeedback directly (no lambda needed)
                            context.getSource().sendFeedback(Component.literal(resultText));
                            
                        } catch (Exception e) {
                            context.getSource().sendError(Component.literal("Error: " + e.getMessage()));
                            return 0;
                        }
                        return 1;
                    }))));
        });
    }

    private static long parseAbbreviatedNumber(String input) {
        input = input.toLowerCase().trim();
        long mult = 1;
        if (input.endsWith("k")) mult = 1000L;
        else if (input.endsWith("m")) mult = 1000000L;
        else if (input.endsWith("b")) mult = 1000000000L;
        
        if (mult > 1) input = input.substring(0, input.length() - 1);
        
        try {
            return (long) (Double.parseDouble(input) * mult);
        } catch (Exception e) {
            return -1;
        }
    }
}