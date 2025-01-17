package mca.client.render.layer;

import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import static mca.client.model.CommonVillagerModel.getVillager;

public class ClothingLayer<T extends LivingEntity, M extends BipedEntityModel<T>> extends VillagerLayer<T, M> {
    private final String variant;

    public ClothingLayer(FeatureRendererContext<T, M> renderer, M model, String variant) {
        super(renderer, model);
        this.variant = variant;
    }

    @Override
    protected Identifier getSkin(T villager) {
        String v = getVillager(villager).isBurned() ? "burnt" : variant;
        return cached(getVillager(villager).getClothes() + v, clothes -> {
            Identifier id = new Identifier(getVillager(villager).getClothes());

            Identifier idNew = new Identifier(id.getNamespace(), id.getPath().replace("normal", v));
            if (canUse(idNew)) {
                return idNew;
            }

            return id;
        });
    }
}
