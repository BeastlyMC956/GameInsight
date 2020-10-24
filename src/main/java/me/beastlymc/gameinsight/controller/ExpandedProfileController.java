package me.beastlymc.gameinsight.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.beastlymc.gameinsight.GameInsight;
import me.beastlymc.gameinsight.file.FileUtilities;
import me.beastlymc.gameinsight.gui.AccountType;
import me.beastlymc.gameinsight.gui.Theme;
import me.beastlymc.gameinsight.utilities.FontUtilities;
import me.beastlymc.gameinsight.utilities.Fonts;
import me.beastlymc.gameinsight.utilities.GraphicsUtilities;
import net.coobird.thumbnailator.Thumbnails;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ExpandedProfileController {

    private static Stage stage;
    GameInsight main = GameInsight.getInstance();
    private static Pane profilePane;
    private static final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
            HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private static ExpandedProfileController instance;

    public ExpandedProfileController() {
        instance = this;
    }

    public static void init() throws IOException {
        FXMLLoader profile = new FXMLLoader(GameInsight.class.getResource("/ExpandedProfile.fxml"));
        profilePane = profile.load();

        double width = 250, height = 350;

        stage = new Stage();

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(profilePane));
        stage.setWidth(width);
        stage.setHeight(height);

        stage.setResizable(false);

        stage.getIcons().add(new Image(GameInsight.class.getResource("/icons/applicationLogo.png").toExternalForm()));
    }

    public void update() {
        try {
            updatePosition();
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updatePosition() {
        double stageWidth = WIDTH - GameInsight.getInstance().getStage().getWidth() + GameInsight.getInstance().getStage().getX() + stage.getWidth() + 18,
                stageHeight = HEIGHT / 2 - GameInsight.getInstance().getStage().getHeight() / 2 + GameInsight.getInstance().getStage().getY() - GameInsight.getInstance().getStage().getHeight() * 0.115; // 92
        stage.setX(Math.round(stageWidth)); //Should be dynamic
        stage.setY(Math.round(stageHeight));
    }

    private void start() throws IOException {
        AccountType accountType = AccountType.PRO;
        expandedProfilePane.setBackground(new Background(new BackgroundFill(GameInsight.getInstance().getCurrentTheme().getOverlay(Theme.OverlayValues.OVERLAY_7), CornerRadii.EMPTY, Insets.EMPTY)));

        BufferedImage bufferedImage = FileUtilities.getProfileImage().getImage();
        double scale = 64 / (double) bufferedImage.getHeight();
        BufferedImage newImage = Thumbnails.of(bufferedImage).scale(scale).asBufferedImage();

        Image image = SwingFXUtils.toFXImage(newImage, null);

        expandedProfileImage.setImage(image);
        expandedProfileImage.setLayoutX(Math.round(expandedProfilePane.getWidth() / 3) - image.getWidth());
        expandedProfileImage.setLayoutY(expandedProfileImage.getLayoutX());

        accountTypeText.setFont(FontUtilities.loadFont(Fonts.BOLD, 10));

        accountTypePane.setLayoutX(expandedProfileImage.getLayoutX() * 2 + image.getWidth());
        accountTypePane.setLayoutY(expandedProfileImage.getLayoutY() + image.getHeight() - accountTypePane.getHeight());
        accountTypePane.setPrefHeight(16);
        accountTypePane.setBackground(new Background(new BackgroundFill(accountType.getBackground(), CornerRadii.EMPTY, Insets.EMPTY)));
        accountTypeText.setText(accountType.getName().toUpperCase());
        if (GameInsight.getInstance().getCurrentTheme().isDarkBase())
            accountTypeText.setFill(main.getCurrentTheme().getTextColor());
        else
            accountTypeText.setFill(main.getCurrentTheme().getTextColor().invert());
        accountTypeText.setLayoutY(Math.round(accountTypePane.getHeight() / 2 + accountTypeText.getFont().getSize() / 2.5));
        accountTypeText.setLayoutX(2);
        accountTypePane.setPrefWidth(accountType.getSize());

        expandedProfileText.setFont(ControllerUtilities.TEXT);
        expandedProfileText.setFill(GameInsight.getInstance().getCurrentTheme().getTextColor());
        expandedProfileText.setLayoutX(expandedProfileImage.getLayoutX() * 2 + image.getWidth());
        expandedProfileText.setLayoutY(expandedProfileImage.getLayoutY() + expandedProfileImage.getImage().getHeight() - Math.round(expandedProfileText.getFont().getSize() * 1.5));

        topCanvas.setLayoutX(expandedProfileImage.getLayoutX());
        topCanvas.setLayoutY(expandedProfileImage.getLayoutY() + image.getHeight() + expandedProfileImage.getLayoutX());
        topCanvas.setWidth(expandedProfilePane.getWidth() - expandedProfileImage.getLayoutY() * 2);
        topCanvas.setHeight(2);

        myAccountPane.setLayoutX(expandedProfileImage.getLayoutX());
        myAccountPane.setLayoutY(expandedProfileImage.getLayoutY() * 3 + image.getHeight() + topCanvas.getHeight() - 1);
        myAccountPane.setPrefWidth(topCanvas.getWidth());
        myAccountPane.setCursor(Cursor.HAND);

        myAccountText.setFill(main.getCurrentTheme().getTextColor());
        myAccountText.setFont(FontUtilities.loadFont(Fonts.SEMI_BOLD, 13));
        myAccountText.setLayoutY(myAccountPane.getHeight() / 2 + Math.floor(myAccountText.getFont().getSize() / 2.5));
        myAccountText.setWrappingWidth(myAccountPane.getWidth() - myAccountText.getLayoutX() * 2);

        expandedProfileCanvas.setLayoutX(expandedProfileImage.getLayoutX());
        expandedProfileCanvas.setLayoutY(myAccountPane.getLayoutY());
        expandedProfileCanvas.setWidth(2);

        settingsPane.setLayoutX(myAccountPane.getLayoutX());
        settingsPane.setLayoutY(myAccountPane.getLayoutY() + myAccountPane.getHeight() + expandedProfileImage.getLayoutY() + 2);
        settingsPane.setPrefWidth(topCanvas.getWidth());
        settingsPane.setCursor(Cursor.HAND);

        settingsText.setFill(main.getCurrentTheme().getTextColor());
        settingsText.setFont(FontUtilities.loadFont(Fonts.SEMI_BOLD, 13));
        settingsText.setLayoutY(settingsPane.getHeight() / 2 + Math.floor(settingsText.getFont().getSize() / 2.5));
        settingsText.setWrappingWidth(myAccountPane.getWidth() - settingsText.getLayoutX() * 2);

        bottomCanvas.setLayoutX(expandedProfileImage.getLayoutX());
        bottomCanvas.setLayoutY(settingsPane.getLayoutY() + settingsPane.getHeight() + expandedProfileImage.getLayoutY() + 1);
        bottomCanvas.setWidth(expandedProfilePane.getWidth() - expandedProfileImage.getLayoutY() * 2);
        bottomCanvas.setHeight(2);

        logoutPane.setLayoutX(expandedProfileImage.getLayoutX());
        logoutPane.setLayoutY(bottomCanvas.getLayoutY() + bottomCanvas.getHeight() + expandedProfileImage.getLayoutY());
        logoutPane.setPrefWidth(bottomCanvas.getWidth());
        logoutPane.setCursor(Cursor.HAND);

        logoutText.setFill(main.getCurrentTheme().getTextColor());
        logoutText.setFont(FontUtilities.loadFont(Fonts.SEMI_BOLD, 13));
        logoutText.setLayoutY(logoutPane.getHeight() / 2 + Math.floor(logoutText.getFont().getSize() / 2.5));
        logoutText.setWrappingWidth(logoutPane.getWidth() - logoutText.getLayoutX() * 2);

        initCanvas();

        stage.setHeight(logoutPane.getLayoutY() + logoutPane.getHeight() + expandedProfileImage.getLayoutY());
    }

    private void initCanvas() {
        Stop[] stops = new Stop[]{new Stop(0, main.getCurrentTheme().getMainAccentColor()), new Stop(1, main.getCurrentTheme().getSubAccentColor())};
        LinearGradient fill = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);

        GraphicsContext topContext = topCanvas.getGraphicsContext2D();
        topContext.setLineWidth(2);
        topContext.beginPath();
        topContext.moveTo(0, 0);
        topContext.lineTo(topCanvas.getWidth(), 0);
        GraphicsUtilities.setColors(topContext, fill, fill);
        topContext.closePath();

        GraphicsContext bottomContext = bottomCanvas.getGraphicsContext2D();
        bottomContext.setLineWidth(2);
        bottomContext.beginPath();
        bottomContext.moveTo(0, 0);
        bottomContext.lineTo(topCanvas.getWidth(), 0);
        GraphicsUtilities.setColors(bottomContext, fill, fill);
        bottomContext.closePath();

        expandedProfileCanvas.setLayoutX(expandedProfileImage.getLayoutX());
        expandedProfileCanvas.setLayoutY(myAccountPane.getLayoutY());
        expandedProfileCanvas.setWidth(3);
        GraphicsContext gc = expandedProfileCanvas.getGraphicsContext2D();

        gc.setLineWidth(2);

        gc.beginPath();
        gc.moveTo(0, 0);
        gc.lineTo(0, 30);
        GraphicsUtilities.setColors(gc, main.getCurrentTheme().getMainAccentColor(), main.getCurrentTheme().getMainAccentColor());
        gc.closePath();

        gc.beginPath();
        gc.moveTo(0, settingsPane.getLayoutY() - expandedProfileCanvas.getLayoutY());
        gc.lineTo(0, 30 + settingsPane.getLayoutY() - expandedProfileCanvas.getLayoutY());
        GraphicsUtilities.setColors(gc, main.getCurrentTheme().getMainAccentColor(), main.getCurrentTheme().getMainAccentColor());
        gc.closePath();

        gc.beginPath();
        gc.moveTo(0, logoutPane.getLayoutY() - expandedProfileCanvas.getLayoutY());
        gc.lineTo(0, 30 + logoutPane.getLayoutY() - expandedProfileCanvas.getLayoutY());
        GraphicsUtilities.setColors(gc, main.getCurrentTheme().getMainAccentColor().invert(), main.getCurrentTheme().getMainAccentColor().invert());
        gc.closePath();
    }

    @FXML
    void onLogoutClick() {
        Stage mainStage = main.getStage();
        mainStage.close();
        stage.close();
    }

    @FXML
    private Pane expandedProfilePane,
            accountTypePane,
            myAccountPane,
            settingsPane,
            logoutPane;

    @FXML
    private ImageView expandedProfileImage;

    @FXML
    private Text accountTypeText,
            expandedProfileText,
            myAccountText,
            settingsText,
            logoutText;

    @FXML
    private Canvas expandedProfileCanvas,
            topCanvas,
            bottomCanvas;

    public static Pane getProfilePane() {
        return profilePane;
    }

    public static Stage getStage() {
        return stage;
    }

    public static ExpandedProfileController getInstance() {
        return instance;
    }
}