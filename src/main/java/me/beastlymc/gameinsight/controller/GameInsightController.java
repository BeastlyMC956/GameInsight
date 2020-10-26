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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import me.beastlymc.gameinsight.GameInsight;
import me.beastlymc.gameinsight.file.FileUtilities;
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
    private static boolean isToolbar = true;
    private Color baseColor;
    private final Map<Pane, Boolean> active = new HashMap<>();
    private final Map<Pane, Canvas> canvasMap = new HashMap<>();
    private final GameInsight main = GameInsight.getInstance();

    public void initMain() throws IOException {
        setBaseColor(main.getCurrentTheme().addOverlay(50));
        contentPane.setBackground(new Background(new BackgroundFill(main.getCurrentTheme().addOverlay(22), CornerRadii.EMPTY, Insets.EMPTY)));
        homeTitle.setFill(main.getCurrentTheme().getThemeBaseColorMap().get(Theme.ThemeBase.TEXT_COLOR));
        homeTitle.setWrappingWidth(homeTitle.getText().length() * homeTitle.getFont().getSize() * 1.5);
        homeTitle.setFont(ControllerUtilities.MAIN_CONTENT);

        homeText.setLayoutY(homeTitle.getLayoutY() + homeTitle.getFont().getSize() * 1.5);
        homeText.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur id urna augue. " +
                "Vivamus sit amet mi nec velit lacinia viverra. Etiam nisi nunc, rutrum et nulla vel, cursus tristique justo. " +
                "Vestibulum commodo felis sapien, ac finibus magna imperdiet eu. In hac habitasse platea dictumst. " +
                "Nulla facilisi. Nulla feugiat ultricies massa, ac bibendum nisi eleifend id.");
        homeText.setWrappingWidth(contentPane.getWidth() - homeText.getLayoutX() * 2);
        homeText.setFill(main.getCurrentTheme().getEmphasis(60));
        homeText.setFont(ControllerUtilities.BODY_TEXT);

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
        toolbarPane.setBackground(new Background(new BackgroundFill(main.getCurrentTheme().getEmphasis(4), CornerRadii.EMPTY, Insets.EMPTY)));
        titleText.setFont(ControllerUtilities.TITLE);
        titleText.setFill(main.getCurrentTheme().getThemeBaseColorMap().get(Theme.ThemeBase.TEXT_COLOR));

        // Flexible Image & Text Placement

        double wrapping = profileName.getText().length() * 7.5 + ControllerUtilities.TEXT.getSize() * (ControllerUtilities.TEXT.getSize() / 8);
        double spacing = wrapping + dropDownMenuPane.getWidth() + ControllerUtilities.TEXT.getSize() * .2;

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
        profileName.setFont(ControllerUtilities.TEXT);
        profileName.setFill(main.getCurrentTheme().getThemeBaseColorMap().get(Theme.ThemeBase.TEXT_COLOR));
        profileName.setLayoutX(main.getStage().getWidth() - spacing);
        profileName.setLayoutY(profileImagePane.getLayoutY() + (profileImagePane.getHeight() / 2) + ControllerUtilities.TEXT.getSize() / 4);

        profileImagePane.setLayoutX(main.getStage().getWidth() - (spacing + profileImagePane.getWidth() + (ControllerUtilities.TEXT.getSize() * .6)));

        profileSettings.setFill(main.getCurrentTheme().getThemeBaseColorMap().get(Theme.ThemeBase.PRIMARY_ACCENT_COLOR));
        profileSettings.setScaleX(2.3);
        profileSettings.setScaleY(2.3);

        profileSettings.setLayoutY(profileName.getLayoutY() - ControllerUtilities.TEXT.getSize() / 5);
        dropDownMenuPane.setLayoutX(main.getStage().getWidth() - (8 + dropDownMenuPane.getWidth()));

        dropdownMenuButton.setCursor(Cursor.HAND);
        dropdownMenuButton.setLayoutX(dropDownMenuPane.getLayoutX());
        dropdownMenuButton.setLayoutY(dropDownMenuPane.getLayoutY());
        dropdownMenuButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            Stage stage = ExpandedProfileController.getStage();
            stage.close();
        });

        titleText.setLayoutY(profileName.getLayoutY());
    }

    /**
     * <b>Initialization of the Sidebar</b>
     * <p>Color Corrections & Theme Integration within the Sidebar</p>
     * <p>Layout of certain elements</p>
     */
    private void initSidebar() {
        initActive();
        sidebarPane.setBackground(new Background(new BackgroundFill(main.getCurrentTheme().getEmphasis(6), CornerRadii.EMPTY, Insets.EMPTY)));
        homePane.setCursor(Cursor.HAND);
        leaguePane.setCursor(Cursor.HAND);

        GraphicsContext gcHome = homeCanvas.getGraphicsContext2D();
        GraphicsContext gcLeague = leagueCanvas.getGraphicsContext2D();

        GraphicsUtilities.drawHouse(gcHome, main.getCurrentTheme().getThemeBaseColorMap().get(Theme.ThemeBase.TEXT_COLOR), main.getCurrentTheme().getThemeBaseColorMap().get(Theme.ThemeBase.TEXT_COLOR));
        GraphicsUtilities.drawLeague(gcLeague, main.getCurrentTheme().getThemeBaseColorMap().get(Theme.ThemeBase.TEXT_COLOR), main.getCurrentTheme().getThemeBaseColorMap().get(Theme.ThemeBase.TEXT_COLOR));
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
                    entry.getKey().setBackground(new Background(new BackgroundFill(main.getCurrentTheme().getEmphasis(6), CornerRadii.EMPTY, Insets.EMPTY)));
                drawLine = false;
            } else {
                if (!customOverlay)
                    entry.getKey().setBackground(new Background(new BackgroundFill(main.getCurrentTheme().getEmphasis(10), CornerRadii.EMPTY, Insets.EMPTY)));
                drawLine = true;
            }
            if (drawLine) {
                Stop[] stops = new Stop[]{new Stop(0, main.getCurrentTheme().getThemeBaseColorMap().get(Theme.ThemeBase.PRIMARY_ACCENT_COLOR)), new Stop(1, main.getCurrentTheme().getThemeBaseColorMap().get(Theme.ThemeBase.SECONDARY_ACCENT_COLOR))};
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
    void onMainMouseClicked() {
        Stage stage = ExpandedProfileController.getStage();
        if (!stage.isFocused())
            stage.close();
    }

    @FXML
    private void onToolbarPress(MouseEvent event) {
        if (isToolbar) {
            Stage stage = main.getStage();
            Stage profile = ExpandedProfileController.getStage();
            if (profile.isShowing())
                profile.close();

            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        }
    }

    @FXML
    private void onToolbarDrag(MouseEvent event) {
        if (isToolbar) {
            Stage stage = main.getStage();
            Stage profile = ExpandedProfileController.getStage();
            if (profile.isShowing())
                profile.close();

            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        }
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
        Stage profileStage = ExpandedProfileController.getStage();
        profileStage.close();
        stage.close();
    }

    @FXML
    private void onProfileImageClicked() {
        boolean toggle = ExpandedProfileController.getStage().isShowing();
        if (!toggle) {
            ExpandedProfileController.getStage().show();
            ExpandedProfileController.getProfilePane().setVisible(true);
            ExpandedProfileController.getInstance().update();
        } else
            ExpandedProfileController.getStage().close();
    }

    @FXML
    private void onMouseHomeHover() {
        if (!active.get(homePane)) {
            setBaseColor(main.getCurrentTheme().addOverlay(24));
            homePane.setBackground(new Background(new BackgroundFill(baseColor, CornerRadii.EMPTY, Insets.EMPTY)));
            updateBackground(true);
        }
    }

    @FXML
    private void onLeagueHover() {
        if (active.get(leaguePane).equals(false)) {
            setBaseColor(main.getCurrentTheme().addOverlay(24));
            leaguePane.setBackground(new Background(new BackgroundFill(baseColor, CornerRadii.EMPTY, Insets.EMPTY)));
            updateBackground(true);
        }
    }

    @FXML
    private void onMouseHomeExited() {
        if (!active.get(homePane)) {
            setBaseColor(main.getCurrentTheme().addOverlay(60));
            homePane.setBackground(new Background(new BackgroundFill(baseColor, CornerRadii.EMPTY, Insets.EMPTY)));
            updateBackground(false);
        }
    }

    @FXML
    private void onLeagueExited() {
        if (!active.get(leaguePane)) {
            setBaseColor(main.getCurrentTheme().addOverlay(50));
            leaguePane.setBackground(new Background(new BackgroundFill(baseColor, CornerRadii.EMPTY, Insets.EMPTY)));
            updateBackground(false);
        }
    }

    @FXML
    private void onHomeClicked() {
        setActive(homePane, true);
        setBaseColor(main.getCurrentTheme().addOverlay(18));
        updateBackground(false);
        mainMenuPane.setVisible(true);
    }

    @FXML
    private void onLeagueClicked() {
        setActive(leaguePane, true);
        setBaseColor(main.getCurrentTheme().addOverlay(18));
        updateBackground(false);
        mainMenuPane.setVisible(false);
    }

    //TODO: FIX THIS
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
            homePane,
            leaguePane;

    @FXML
    private Text profileName,
            profileSettings,
            titleText,
            homeTitle,
            homeText;

    @FXML
    private MenuButton dropdownMenuButton;

    @FXML
    private ImageView profileImage;

    @FXML
    private Canvas homeCanvas,
            leagueCanvas;
}