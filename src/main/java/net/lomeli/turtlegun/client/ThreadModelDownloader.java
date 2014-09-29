package net.lomeli.turtlegun.client;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import net.lomeli.turtlegun.TurtleGun;
import net.lomeli.turtlegun.client.render.RenderGun;
import net.lomeli.turtlegun.lib.ModLibs;

public class ThreadModelDownloader extends Thread {
    public final File modelFolder;
    public boolean needToDownload;

    public ThreadModelDownloader(File modelFolder) {
        this.needToDownload = false;
        this.modelFolder = modelFolder;

        this.setName("Turtle Gun Asset Downloader");
        this.setDaemon(true);
    }

    @Override
    public void run() {
        if (needToDownload) {
            try {
                downloadResource(new URL(ModLibs.MODEL_URL), RenderGun.modelFile, 2217);
                TurtleGun.logger.logInfo("Successfully downloaded models needed.");
            } catch (Exception e) {
                TurtleGun.logger.logError("Failed to download techne model!");
                e.printStackTrace();
            }
        }
    }

    public void preCheck() {
        if (!modelFolder.exists()) {
            modelFolder.mkdirs();
            needToDownload = true;
        } else {
            if (!RenderGun.modelFile.exists())
                needToDownload = true;
        }
    }

    public boolean downloadResource(URL par1URL, File par2File, long size) throws IOException {
        if (par2File.exists()) {
            if (par2File.length() == size)
                return false;
        } else if (!par2File.getParentFile().exists())
            par2File.getParentFile().mkdirs();
        byte[] var5 = new byte[4096];
        URLConnection con = par1URL.openConnection();
        con.setConnectTimeout(15000);
        con.setReadTimeout(15000);
        DataInputStream var6 = new DataInputStream(con.getInputStream());
        DataOutputStream var7 = new DataOutputStream(new FileOutputStream(par2File));
        while (true) {
            int var9;
            if ((var9 = var6.read(var5)) < 0) {
                var6.close();
                var7.close();
                return true;
            }
            var7.write(var5, 0, var9);
        }
    }
}
