package me.beastlymc.gameinsight.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import me.beastlymc.gameinsight.GameInsight;
import me.beastlymc.gameinsight.file.FileUtilities;
import me.beastlymc.gameinsight.gui.AccountType;
import me.beastlymc.gameinsight.gui.Theme;
import me.beastlymc.gameinsight.utilities.*;

import net.coobird.thumbnailator.Thumbnails;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>Main Pane Controller Class</b>
 *
 * <p>Setup & Logic of the main pane</p>
 *
 * @author BeastlyMC956
 */
public class GameInsightController {

    //TODO: Create custom dropdown menu with panes?
    private static double xOffset = 0, yOffset = 0;
    private static boolean isToolbar = true, isEnabled = false;
    private final Font title = FontUtilities.loadFont(Fonts.BOLD, 20),
            text = FontUtilities.loadFont(Fonts.SEMI_BOLD, 15),
            bodyText = FontUtilities.loadFont(Fonts.MEDIUM, 12),
            mainContent = FontUtilities.loadFont(Fonts.SEMI_BOLD, 25);
    private Color baseColor;
    private final Map<Pane, Boolean> active = new HashMap<>();
    private final Map<Pane, Canvas> canvasMap = new HashMap<>();
    private final GameInsight main = GameInsight.getInstance();

    public void initMain() throws IOException {
        setBaseColor(main.getCurrentTheme().getOverlay(Theme.OverlayValues.OVERLAY_4));
        contentPane.setBackground(new Background(new BackgroundFill(main.getCurrentTheme().getOverlay(Theme.OverlayValues.OVERLAY_5), CornerRadii.EMPTY, Insets.EMPTY)));
        homeTitle.setFill(main.getCurrentTheme().getTextColor());
        homeTitle.setWrappingWidth(homeTitle.getText().length() * homeTitle.getFont().getSize() * 1.5);
        homeTitle.setFont(mainContent);
        homeTitle.setLayoutY(toolbarPane.getHeight() * 1.5);
        homeTitle.setLayoutX(sidebarPane.getWidth() * 1.5);

        homeText.setLayoutY(homeTitle.getLayoutY() + homeTitle.getFont().getSize() * 1.5);
        homeText.setLayoutX(homeTitle.getLayoutX());
        homeText.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur id urna augue. " +
                "Vivamus sit amet mi nec velit lacinia viverra. Etiam nisi nunc, rutrum et nulla vel, cursus tristique justo. " +
                "Vestibulum commodo felis sapien, ac finibus magna imperdiet eu. In hac habitasse platea dictumst. " +
                "Nulla facilisi. Nulla feugiat ultricies massa, ac bibendum nisi eleifend id.");
        homeText.setWrappingWidth(contentPane.getWidth() - homeText.getLayoutX() * 2);
        homeText.setFill(main.getCurrentTheme().getEmphasis(Theme.Emphasis.MEDIUM));
        homeText.setFont(bodyText);

        initToolbar();
        initSidebar();
    }

    /**
     * <b>Initialization of the Toolbar</b>
     * <p>Layout of a lot of elements</p>
     * <p>Dynamic text & image placement</p>
     * <p>Resizing of the profile image</p>
     *
     * @throws IOException if certain files does not exist
     */
    private void initToolbar() throws IOException {
        toolbarPane.setBackground(new Background(new BackgroundFill(main.getCurrentTheme().getMainBackgroundColor(), CornerRadii.EMPTY, Insets.EMPTY)));
        titleText.setFont(title);
        titleText.setFill(main.getCurrentTheme().getTextColor());

        // Flexible Image & Text Placement

        double wrapping = profileName.getText().length() * 7.5 + text.getSize() * (text.getSize() / 8);
        double spacing = wrapping + dropDownMenuPane.getWidth() + text.getSize() * .2;

        dropDownMenuPane.setLayoutX(main.getStage().getWidth() - (20 + dropDownMenuPane.getWidth()));

        BufferedImage icon = FileUtilities.getProfileImage().getImage();
        double scale = 32 / (double) icon.getHeight();
        BufferedImage newImage = Thumbnails.of(icon).scale(scale).asBufferedImage();

        Image image = SwingFXUtils.toFXImage(newImage, null);
        profileImage.setImage(image);
        profileImagePane.setMaxHeight(icon.getHeight() * scale);
        profileImagePane.setMaxWidth(icon.getWidth() * scale);
        profileImagePane.setCursor(Cursor.HAND);

        profileName.setWrappingWidth(wrapping);
        profileName.setFont(text);
        profileName.setFill(main.getCurrentTheme().getTextColor());
        profileName.setLayoutX(main.getStage().getWidth() - spacing);
        profileName.setLayoutY(profileImagePane.getLayoutY() + (profileImagePane.getHeight() / 2) + text.getSize() / 4);

        profileImagePane.setLayoutX(main.getStage().getWidth() - (spacing + profileImagePane.getWidth() + (text.getSize() * .6)));

        profileSettings.setFill(main.getCurrentTheme().getMainAccentColor());
        profileSettings.setScaleX(2.3);
        profileSettings.setScaleY(2.3);

        profileSettings.setLayoutY(profileName.getLayoutY() - text.getSize() / 5);
        dropDownMenuPane.setLayoutX(main.getStage().getWidth() - (8 + dropDownMenuPane.getWidth()));

        dropdownMenuButton.setCursor(Cursor.HAND);
        dropdownMenuButton.setLayoutX(dropDownMenuPane.getLayoutX());
        dropdownMenuButton.setLayoutY(dropDownMenuPane.getLayoutY());

        titleText.setLayoutX(10);
        titleText.setLayoutY(profileName.getLayoutY());
        initExpandedProfile();
    }

    private void initExpandedProfile() throws IOException {
        AccountType accountType = AccountType.PRO;
        expandedProfilePane.setBackground(new Background(new BackgroundFill(main.getCurrentTheme().getOverlay(Theme.OverlayValues.OVERLAY_7), CornerRadii.EMPTY, Insets.EMPTY)));
        expandedProfilePane.setLayoutX(profileImagePane.getLayoutX() - expandedProfilePane.getWidth() + profileImagePane.getWidth());
        expandedProfilePane.setLayoutY(profileImagePane.getLayoutY() + profileImagePane.getHeight());

        BufferedImage icon = FileUtilities.getProfileImage().getImage();
        double imgHeight = icon.getHeight();
        double scale = 64 / imgHeight;
        BufferedImage newImage = Thumbnails.of(icon).scale(scale).asBufferedImage();
        Image image = SwingFXUtils.toFXImage(newImage, null);

        expandedProfileImage.setImage(image);
        expandedProfileImage.setLayoutX(Math.round(expandedProfilePane.getWidth() / 3) - image.getWidth());
        expandedProfileImage.setLayoutY(expandedProfileImage.getLayoutX());

        accountTypeText.setFont(FontUtilities.loadFont(Fonts.BOLD, 10));

        accountTypePane.setLayoutX(expandedProfileImage.getLayoutX() * 2 + image.getWidth());
        accountTypePane.setLayoutY(expandedProfileImage.getLayoutY() + image.getHeight() - 2 - accountTypePane.getHeight());
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

        expandedProfileText.setFont(text);
        expandedProfileText.setFill(main.getCurrentTheme().getTextColor());
        expandedProfileText.setLayoutX(expandedProfileImage.getLayoutX() * 2 + image.getWidth());
        expandedProfileText.setLayoutY(expandedProfileImage.getLayoutY() + image.getHeight() - accountTypePane.getHeight() - expandedProfileText.getFont().getSize());

        Stop[] stops = new Stop[]{new Stop(0, main.getCurrentTheme().getMainAccentColor()), new Stop(1, main.getCurrentTheme().getSubAccentColor())};
        LinearGradient fill = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        topCanvas.setLayoutX(expandedProfileImage.getLayoutX());
        topCanvas.setLayoutY(expandedProfileImage.getLayoutY() + image.getHeight() + expandedProfileImage.getLayoutX());
        topCanvas.setWidth(expandedProfilePane.getWidth() - expandedProfileImage.getLayoutY() * 2);
        topCanvas.setHeight(2);

        GraphicsContext topContext = topCanvas.getGraphicsContext2D();
        topContext.setLineWidth(2);
        topContext.beginPath();
        topContext.moveTo(0, 0);
        topContext.lineTo(topCanvas.getWidth(), 0);
        GraphicsUtilities.setColors(topContext, fill, fill);
        topContext.closePath();

        myAccountPane.setLayoutX(expandedProfileImage.getLayoutX());
        myAccountPane.setLayoutY(expandedProfileImage.getLayoutY() * 3 + image.getHeight() + topCanvas.getHeight());
        myAccountPane.setPrefWidth(topCanvas.getWidth());
        myAccountPane.setCursor(Cursor.HAND);

        myAccountText.setFill(main.getCurrentTheme().getTextColor());
        myAccountText.setFont(FontUtilities.loadFont(Fonts.SEMI_BOLD, 13));
        myAccountText.setLayoutX(8);
        myAccountText.setLayoutY(myAccountPane.getHeight() / 2 + Math.floor(myAccountText.getFont().getSize() / 2.5));
        myAccountText.setWrappingWidth(myAccountPane.getWidth() - myAccountText.getLayoutX() * 2);

        settingsPane.setLayoutX(myAccountPane.getLayoutX());
        settingsPane.setLayoutY(myAccountPane.getLayoutY() + expandedProfilePane.getLayoutY());
        settingsPane.setPrefWidth(topCanvas.getWidth());
        settingsPane.setCursor(Cursor.HAND);

        settingsText.setFill(main.getCurrentTheme().getTextColor());
        settingsText.setFont(FontUtilities.loadFont(Fonts.SEMI_BOLD, 13));
        settingsText.setLayoutX(8);
        settingsText.setLayoutY(settingsPane.getHeight() / 2 + Math.floor(settingsText.getFont().getSize() / 2.5));
        settingsText.setWrappingWidth(myAccountPane.getWidth() - settingsText.getLayoutX() * 2);

        bottomCanvas.setLayoutX(expandedProfileImage.getLayoutX());
        bottomCanvas.setLayoutY(settingsPane.getLayoutY() + settingsPane.getHeight() + expandedProfileImage.getLayoutY());
        bottomCanvas.setWidth(expandedProfilePane.getWidth() - expandedProfileImage.getLayoutY() * 2);
        bottomCanvas.setHeight(2);

        GraphicsContext bottomContext = bottomCanvas.getGraphicsContext2D();
        bottomContext.setLineWidth(2);
        bottomContext.beginPath();
        bottomContext.moveTo(0, 0);
        bottomContext.lineTo(bottomCanvas.getWidth(), 0);
        GraphicsUtilities.setColors(bottomContext, fill, fill);
        bottomContext.closePath();

        logoutPane.setLayoutX(expandedProfileImage.getLayoutX());
        logoutPane.setLayoutY(bottomCanvas.getLayoutY() + bottomCanvas.getHeight() + expandedProfileImage.getLayoutY());
        logoutPane.setPrefWidth(bottomCanvas.getWidth());
        logoutPane.setCursor(Cursor.HAND);

        logoutText.setFill(main.getCurrentTheme().getTextColor());
        logoutText.setFont(FontUtilities.loadFont(Fonts.SEMI_BOLD, 13));
        logoutText.setLayoutX(8);
        logoutText.setLayoutY(logoutPane.getHeight() / 2 + Math.floor(logoutText.getFont().getSize() / 2.5));
        logoutText.setWrappingWidth(logoutPane.getWidth() - logoutText.getLayoutX() * 2);

        expandedProfileCanvas.setLayoutX(expandedProfileImage.getLayoutX());
        expandedProfileCanvas.setLayoutY(myAccountPane.getLayoutY());
        expandedProfileCanvas.setWidth(2);
        GraphicsContext gc = expandedProfileCanvas.getGraphicsContext2D();

        gc.setLineWidth(2);

        gc.beginPath();
        gc.moveTo(0, 0);
        gc.lineTo(0, 30);
        GraphicsUtilities.setColors(gc, main.getCurrentTheme().getMainAccentColor(), main.getCurrentTheme().getMainAccentColor());
        gc.closePath();

        gc.beginPath();
        gc.moveTo(0, expandedProfileImage.getLayoutY() + 30);
        gc.lineTo(0, expandedProfileImage.getLayoutY() + 60);
        GraphicsUtilities.setColors(gc, main.getCurrentTheme().getMainAccentColor(), main.getCurrentTheme().getMainAccentColor());
        gc.closePath();

        gc.beginPath();
        gc.moveTo(0, (expandedProfileImage.getLayoutY() * 3 + 60) + bottomCanvas.getHeight());
        gc.lineTo(0, (expandedProfileImage.getLayoutY() * 3 + 60) + bottomCanvas.getHeight() + 30);
        GraphicsUtilities.setColors(gc, main.getCurrentTheme().getMainAccentColor().invert(), main.getCurrentTheme().getMainAccentColor().invert());
        gc.closePath();

        expandedProfilePane.setPrefHeight(logoutPane.getLayoutY() + logoutPane.getHeight() + expandedProfileImage.getLayoutY());

    }

    /**
     * <b>Initialization of the Sidebar</b>
     * <p>Color Corrections & Theme Integration within the Sidebar</p>
     * <p>Layout of certain elements</p>
     */
    private void initSidebar() {
        initActive();
        sidebarPane.setBackground(new Background(new BackgroundFill(main.getCurrentTheme().getOverlay(Theme.OverlayValues.OVERLAY_2), CornerRadii.EMPTY, Insets.EMPTY)));
        homePane.setCursor(Cursor.HAND);
        leaguePane.setCursor(Cursor.HAND);

        GraphicsContext gcHome = homeCanvas.getGraphicsContext2D();
        GraphicsContext gcLeague = leagueCanvas.getGraphicsContext2D();

        GraphicsUtilities.drawHouse(gcHome, main.getCurrentTheme().getTextColor(), main.getCurrentTheme().getTextColor());
        GraphicsUtilities.drawLeague(gcLeague, main.getCurrentTheme().getTextColor(), main.getCurrentTheme().getTextColor());
    }

    private void initActive() {
        canvasMap.put(homePane, homeCanvas);
        canvasMap.put(leaguePane, leagueCanvas);
        setActive(homePane, true);
        setActive(leaguePane, false);
    }

    private void setActive(Pane pane, boolean isActive) {
        if (!active.isEmpty() && isActive)
            for (Map.Entry<Pane, Boolean> entry : active.entrySet())
                entry.setValue(false);

        active.put(pane, isActive);
        updateBackground(false);
    }

    private void updateBackground(boolean customOverlay) {
        boolean drawLine;
        for (Map.Entry<Pane, Boolean> entry : active.entrySet()) {
            GraphicsContext gc = canvasMap.get(entry.getKey()).getGraphicsContext2D();
            if (entry.getValue().equals(false)) {
                if (!customOverlay)
                    entry.getKey().setBackground(new Background(new BackgroundFill(main.getCurrentTheme().getOverlay(Theme.OverlayValues.OVERLAY_4), CornerRadii.EMPTY, Insets.EMPTY)));
                drawLine = false;
            } else {
                if (!customOverlay)
                    entry.getKey().setBackground(new Background(new BackgroundFill(main.getCurrentTheme().getOverlay(Theme.OverlayValues.OVERLAY_8), CornerRadii.EMPTY, Insets.EMPTY)));
                drawLine = true;
            }
            if (drawLine) {
                Stop[] stops = new Stop[]{new Stop(0, main.getCurrentTheme().getMainAccentColor()), new Stop(1, main.getCurrentTheme().getSubAccentColor())};
                LinearGradient fill = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);

                gc.setLineWidth(2);

                gc.beginPath();
                gc.moveTo(67, 0);
                gc.lineTo(67, 64);

                gc.closePath();

                gc.setFill(fill);
                gc.setStroke(fill);
            } else {
                gc.setLineWidth(2);

                gc.beginPath();
                gc.moveTo(67, 0);
                gc.lineTo(67, 64);

                gc.closePath();

                gc.setFill(baseColor);
                gc.setStroke(baseColor);
            }
            gc.fill();
            gc.stroke();
        }
    }

    @FXML
    private void onToolbarPress(MouseEvent event) {
        if (isToolbar) {
            Stage stage = main.getStage();

            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        }
    }

    @FXML
    private void onToolbarDrag(MouseEvent event) {
        if (isToolbar) {
            Stage stage = main.getStage();

            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        }
    }

    @FXML
    private void onLogoutClick() {
        Stage stage = main.getStage();
        stage.close();
    }

    @FXML
    private void onImagePanePress() {
        isToolbar = false;
    }

    @FXML
    private void onImagePaneRelease() {
        isToolbar = true;
    }

    @FXML
    private void onExitAction() {
        Stage stage = main.getStage();
        stage.close();
    }

    @FXML
    private void onProfileImageClicked() {
        expandedProfilePane.setVisible(!isEnabled);
        isEnabled = !isEnabled;
    }

    @FXML
    private void onMouseHomeHover() {
        if (!active.get(homePane)) {
            setBaseColor(main.getCurrentTheme().getOverlay(Theme.OverlayValues.OVERLAY_6));
            homePane.setBackground(new Background(new BackgroundFill(baseColor, CornerRadii.EMPTY, Insets.EMPTY)));
            updateBackground(true);
        }
    }

    @FXML
    private void onLeagueHover() {
        if (active.get(leaguePane).equals(false)) {
            setBaseColor(main.getCurrentTheme().getOverlay(Theme.OverlayValues.OVERLAY_6));
            leaguePane.setBackground(new Background(new BackgroundFill(baseColor, CornerRadii.EMPTY, Insets.EMPTY)));
            updateBackground(true);
        }
    }

    @FXML
    private void onMouseHomeExited() {
        if (!active.get(homePane)) {
            setBaseColor(main.getCurrentTheme().getOverlay(Theme.OverlayValues.OVERLAY_4));
            homePane.setBackground(new Background(new BackgroundFill(baseColor, CornerRadii.EMPTY, Insets.EMPTY)));
            updateBackground(false);
        }
    }

    @FXML
    private void onLeagueExited() {
        if (!active.get(leaguePane)) {
            setBaseColor(main.getCurrentTheme().getOverlay(Theme.OverlayValues.OVERLAY_4));
            leaguePane.setBackground(new Background(new BackgroundFill(baseColor, CornerRadii.EMPTY, Insets.EMPTY)));
            updateBackground(false);
        }
    }

    @FXML
    private void onHomeClicked() {
        setActive(homePane, true);
        setBaseColor(main.getCurrentTheme().getOverlay(Theme.OverlayValues.OVERLAY_4));
        updateBackground(false);
        mainMenuPane.setVisible(true);
    }

    @FXML
    private void onLeagueClicked() {
        setActive(leaguePane, true);
        setBaseColor(main.getCurrentTheme().getOverlay(Theme.OverlayValues.OVERLAY_4));
        updateBackground(false);
        mainMenuPane.setVisible(false);
    }

    private void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
    }

    @FXML
    private Pane contentPane,
            mainMenuPane,
            toolbarPane,
            sidebarPane,
            profileImagePane,
            dropDownMenuPane,
            expandedProfilePane,
            accountTypePane,
            myAccountPane,
            settingsPane,
            logoutPane,

    homePane,
            leaguePane;

    @FXML
    private Text profileName,
            profileSettings,
            titleText,
            homeTitle,
            homeText,
            expandedProfileText,
            accountTypeText,
            myAccountText,
            settingsText,
            logoutText;

    @FXML
    private MenuButton dropdownMenuButton;

    @FXML
    private ImageView profileImage,
            expandedProfileImage;

    @FXML
    private Canvas homeCanvas,
            leagueCanvas,
            expandedProfileCanvas,
            topCanvas,
            bottomCanvas;
}