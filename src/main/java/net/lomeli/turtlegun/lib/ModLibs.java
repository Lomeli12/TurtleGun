package net.lomeli.turtlegun.lib;

import net.lomeli.lomlib.core.config.annotations.ConfigBoolean;
import net.lomeli.lomlib.core.config.annotations.ConfigInt;

public class ModLibs {
    public static final String MOD_ID = "turtlegun";
    public static final String MOD_NAME = "Turtle Gun";
    public static final int MAJOR = 2, MINOR = 0, REVISION = 0;
    public static final String VERSION = MAJOR + "." + MINOR + "." + REVISION;

    public static final String PACKAGE_PATH = "net.lomeli.turtlegun.";
    public static final String CLIENT_PROXY = PACKAGE_PATH + "client.ClientProxy";
    public static final String PROXY = PACKAGE_PATH + "core.Proxy";

    public static final String UPDATE_JSON = "https://raw.githubusercontent.com/Lomeli12/TurtleGun/master/update.json";

    @ConfigInt(defaultValue = 20, nameOverride = "gunCoolDown")
    public static int GUN_COOLDOWN;
    @ConfigInt(defaultValue = 7, nameOverride = "turtleCountDown")
    public static int TURTLE_COUNTDOWN;
    @ConfigBoolean(defaultValue = true, nameOverride = "allowSpawn")
    public static boolean ALLOW_NATURAL_SPAWN;
    @ConfigInt(defaultValue = 15, nameOverride = "spawnRate")
    public static int SPAWN_RATE;
    @ConfigInt(defaultValue = 1, nameOverride = "packSizeMin")
    public static int PACK_SIZE_MIN;
    @ConfigInt(defaultValue = 7, nameOverride = "packSizeMax")
    public static int PACK_SIZE_MAX;
    @ConfigInt(defaultValue = 10, nameOverride = "gunDropRate")
    public static int GUN_DROP_RATE;
    @ConfigBoolean(defaultValue = true, nameOverride = "checkForUpdates")
    public static boolean CHECK_FOR_UPDATES;
}
