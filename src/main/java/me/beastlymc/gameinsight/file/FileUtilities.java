package me.beastlymc.gameinsight.file;

import me.beastlymc.gameinsight.file.files.CacheFile;
import me.beastlymc.gameinsight.file.files.ProfileImage;

public class FileUtilities {
    private static final String RESOURCE_PATH = "D:\\Programming\\GameInsight\\src\\main\\resources\\";

    private static final GameInsightFile
            CACHE_FILE = new CacheFile(RESOURCE_PATH + "cache", "cache.txt"), // Cache File
            PROFILE_IMAGE_FILE = new ProfileImage(RESOURCE_PATH + "icons","9y2I_Nf9_400x400.jpg"); // Profile Image Picture

    private static final ImageFile PROFILE_IMAGE = (ImageFile) PROFILE_IMAGE_FILE;

    public static GameInsightFile getCache() {
        return CACHE_FILE;
    }
    public static GameInsightFile getProfileImageFile() {
        return PROFILE_IMAGE_FILE;
    }

    public static ImageFile getProfileImage() {
        return PROFILE_IMAGE;
    }
}
