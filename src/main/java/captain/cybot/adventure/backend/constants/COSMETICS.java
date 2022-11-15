package captain.cybot.adventure.backend.constants;

public enum COSMETICS {

    DEFAULT_SHIELD("default_shield.png"),
    WORLD_1_SHIELD("world_1_shield.png"),
    WORLD_2_SHIELD("world_2_shield.png"),
    WORLD_3_SHIELD("world_3_shield.png"),
    WORLD_4_SHIELD("world_4_shield.png");

    private String cosmeticFileName;

    COSMETICS(String cosmeticFileName) {
        this.cosmeticFileName = cosmeticFileName;
    }

    @Override
    public String toString() {
        return cosmeticFileName;
    }
}
