package net.lomeli.turtlegun.lib;

public class ModLibs {
    public static final String MOD_ID = "turtlegun";
    public static final String MOD_NAME = "Turtle Gun";
    public static final int MAJOR = 2, MINOR = 0, REVISION = 0;
    public static final String VERSION = MAJOR + "." + MINOR + "." + REVISION;

    public static final String PACKAGE_PATH = "net.lomeli.turtlegun.";
    public static final String CLIENT_PROXY = PACKAGE_PATH + "client.ClientProxy";
    public static final String PROXY = PACKAGE_PATH + "core.Proxy";

    public static final String UPDATE_JSON = "https://raw.githubusercontent.com/Lomeli12/TurtleGun/master/update.json";

    public static int GUN_COOLDOWN = 20;
    public static int TURTLE_COUNTDOWN = 7;
    public static boolean ALLOW_NATURAL_SPAWN = true;
    public static int SPAWN_RATE = 15;
    public static int PACK_SIZE_MIN = 1;
    public static int PACK_SIZE_MAX = 7;
    public static int GUN_DROP_RATE = 35;
    public static int TURTLE_BOMB_SPAWN = 4;
}
