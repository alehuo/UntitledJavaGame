package ahuotala.game;

/**
 *
 * @author Aleksi Huotala
 */
public class Item implements java.io.Serializable {

    private String name;
    private Effect effect;
    private String effectDesc;
    private int effectValue;
    private final ItemId itemId;
    private boolean isInteractable = false;

    public Item(ItemId itemId) {
        this.itemId = itemId;
        this.name = itemId + "";
        effect = Effect.NONE;
        effectValue = 0;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public Item setInteractable(boolean value) {
        isInteractable = value;
        return this;
    }

    public Item setEffect(Effect effect, String desc) {
        this.effect = effect;
        this.effectDesc = desc;
        return this;
    }

    public Item setEffectValue(int effectValue) {
        this.effectValue = effectValue;
        return this;
    }

    public int getEffectValue() {
        return effectValue;
    }

    public Effect getEffect() {
        return effect;
    }

    public ItemId getItemId() {
        return itemId;
    }

    public String getEffectDescription() {
        return effectDesc;
    }

    public String getName() {
        return name;
    }

    public boolean isInteractable() {
        return isInteractable;
    }

    /**
     * Interact
     */
    public void interact() {

    }
}
