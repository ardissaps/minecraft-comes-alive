package mca.network.c2s;

import mca.cobalt.network.Message;
import mca.cobalt.network.NetworkHandler;
import mca.network.s2c.PlayerDataMessage;
import mca.server.world.data.PlayerSaveData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

import java.io.Serial;
import java.util.UUID;

public class PlayerDataRequest implements Message {
    @Serial
    private static final long serialVersionUID = -1869959282406697226L;

    private final UUID uuid;

    public PlayerDataRequest(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void receive(ServerPlayerEntity player) {
        PlayerEntity playerEntity = player.getWorld().getPlayerByUuid(uuid);
        if (playerEntity instanceof ServerPlayerEntity serverPlayerEntity) {
            PlayerSaveData data = PlayerSaveData.get(serverPlayerEntity);
            if (data.isEntityDataSet()) {
                NbtCompound nbt = data.getEntityData();
                NetworkHandler.sendToPlayer(new PlayerDataMessage(uuid, nbt), player);
            }
        }
    }
}
