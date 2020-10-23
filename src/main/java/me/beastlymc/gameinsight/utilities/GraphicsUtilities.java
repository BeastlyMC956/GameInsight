package me.beastlymc.gameinsight.utilities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import me.beastlymc.gameinsight.file.FileUtilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <b>A Utility Class for everything concerning [Graphics ({@link java.awt.Image} & {@link GraphicsContext})]</b>
 *
 * @author BeastlyMC956
 */
public class GraphicsUtilities {

    /**
     *
     * <p>Loads an image from any format and converts it to a .png</p>
     * <p>You can specify the name that the new image will have</p>
     * @param imagePath the origin image
     * @param newName the new file name in png format
     * @return a new {@link BufferedImage} instance with the new image loaded
     */
    public static BufferedImage loadImage(String imagePath, String newName) {
        Path path = Paths.get(imagePath);

        try {
            if (!new File(imagePath).exists()) {
                if (!new File(path.toFile().getParentFile().getPath(), newName).exists())
                    throw new IOException("Neither the origin file nor the new file exists");
                return ImageIO.read(new File(path.toFile().getParentFile().getPath(), newName));
            }
            InputStream inputStream = new FileInputStream(new File(path.toUri()));
            BufferedImage bufferedImage = ImageIO.read(inputStream);

            File newImage = FileUtilities.getProfileImageFile().toFile();

            System.out.println(newImage.getAbsolutePath());

            ImageIO.write(bufferedImage, "png", newImage);

            inputStream.close();

            Files.delete(path);

            return ImageIO.read(newImage);
        } catch(IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * <p>Draws a house with custom colors</p>
     * @param graphicsContext The {@link GraphicsContext} instance
     * @param fill The fill color
     * @param stroke The stroke color
     */
    public static void drawHouse(GraphicsContext graphicsContext, Paint fill, Paint stroke) {

        graphicsContext.setLineWidth(2);
        //gc.strokeLine(40, 0, 0, 40);

        graphicsContext.beginPath();

        // Beginning
        graphicsContext.moveTo(24, 42);
        drawLine(graphicsContext, 29, 42);

        // Door
        drawLine(graphicsContext, 29, 35);
        drawLine(graphicsContext, 37, 35);
        drawLine(graphicsContext, 37, 42);

        // Elevation of 2nd post
        drawLine(graphicsContext, 42, 42);
        drawLine(graphicsContext, 42, 30);

        //Extension 2nd
        drawLine(graphicsContext, 47, 30);

        // Convergence
        drawLine(graphicsContext, 33, 21);

        // Extension 1st
        drawLine(graphicsContext, 19, 30);

        // Elevation of 1st post
        drawLine(graphicsContext, 24, 30);
        drawLine(graphicsContext, 24, 42);

        //gc.moveTo(19,32);

        graphicsContext.closePath();

        graphicsContext.setFill(fill);
        graphicsContext.setStroke(stroke);
        graphicsContext.fill();
        graphicsContext.stroke();

        //Huge Boxes either side
        drawRect(graphicsContext, 24, 27, 5, 14);
        drawRect(graphicsContext, 37, 27, 5, 14);

        //Top-Box
        drawRect(graphicsContext, 29, 23, 8, 11);

        drawRect(graphicsContext, 25, 25, 16, 2);

        drawRect(graphicsContext, 22, 28, 22, 1);

        drawRect(graphicsContext, 31, 22, 4, 2);
    }


    public static void drawLeague(GraphicsContext graphicsContext, Paint fill, Paint stroke) {
        graphicsContext.setLineWidth(2);

        graphicsContext.beginPath();

        graphicsContext.moveTo(27,24);
        drawLine(graphicsContext,27,39);
        //Bottom Left Diagonal Line
        drawLine(graphicsContext,24,43);
        drawLine(graphicsContext,42,43);
        //Bottom Right Diagonal Line
        drawLine(graphicsContext,45,39);
        //From bottom to top
        drawLine(graphicsContext,33,39);
        drawLine(graphicsContext,33,21);
        //Top Diagonal Line
        drawLine(graphicsContext,24,21);
        drawLine(graphicsContext,27,24);

        graphicsContext.closePath();
        graphicsContext.setFill(fill);
        graphicsContext.setStroke(stroke);
        graphicsContext.fill();
        graphicsContext.stroke();

        drawRect(graphicsContext,27,21,30-24,43-21);
        drawRect(graphicsContext,26,40,18,2);
        drawRect(graphicsContext,26,22,2,2);
    }

    public static void setColors(GraphicsContext graphicsContext, Paint fill, Paint stroke){
        graphicsContext.setFill(fill);
        graphicsContext.setStroke(stroke);

        graphicsContext.fill();
        graphicsContext.stroke();
    }

    /**
     * <p>Combing {@link GraphicsContext#lineTo(double, double)} & {@link GraphicsContext#moveTo(double, double)}</p>
     * <p>To make it more understandable from a user perspective</p>
     * 
     * @param gc The {@link GraphicsContext} instance
     * @param x Start X value
     * @param y Start Y value
     */
    private static void drawLine(GraphicsContext gc, double x, double y) {
        gc.lineTo(x, y);
        gc.moveTo(x, y);
    }

    /**
     *
     * <p>Simplified version of the already existing {@link GraphicsContext#fillRect(double, double, double, double)}</p>
     * <p>To make it more understandable from a user perspective</p>
     *
     * @param gc The {@link GraphicsContext} instance
     * @param x Start X value
     * @param y Start Y value
     * @param width How wide the rectangle will be, from left to right
     * @param height How high the rectangle will be, from top to bottom
     */
    private static void drawRect(GraphicsContext gc, double x, double y, double width, double height) {
        gc.fillRect(x, y, width, height);
    }
}
