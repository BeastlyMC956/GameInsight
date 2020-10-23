package me.beastlymc.gameinsight.file.files;

import me.beastlymc.gameinsight.file.GameInsightFile;
import me.beastlymc.gameinsight.file.ImageFile;
import me.beastlymc.gameinsight.utilities.GraphicsUtilities;

import java.awt.image.BufferedImage;
import java.io.File;

public class ProfileImage extends GameInsightFile implements ImageFile {

    String filePath, imageName;

    public ProfileImage(String path, String name) {
        super(path, name);

        this.filePath = path + File.separator + name;
        this.imageName = "profileIcon.png";
    }

    @Override
    public void createFile() {}

    @Override
    public BufferedImage getImage() {
        return GraphicsUtilities.loadImage(filePath, imageName);
    }
}
