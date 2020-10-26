package me.beastlymc.gameinsight;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.beastlymc.gameinsight.controller.ExpandedProfileController;
import me.beastlymc.gameinsight.controller.GameInsightController;
import me.beastlymc.gameinsight.gui.Theme;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <b>Main Application Class</b>
 *
 * <p>Start of the program</p>
 *
 * @author BeastlyMC956
 */
public class GameInsight extends Application {
    private final static Logger LOGGER = Logger.getLogger(GameInsight.class.getName());
    private final Theme fallbackTheme = Theme.DARK;
    private static Stage stage;
    private static Theme currentTheme;

    /**
     * <b>Main start of the application</b>
     *
     * @param stage The stage to be presented
     * @throws Exception In case {@link GameInsightController} is null
     */
    public void start(Stage stage) throws Exception {
        GameInsight.stage = stage;
        ExpandedProfileController expandedProfileController = new ExpandedProfileController();

        FXMLLoader main = new FXMLLoader(GameInsight.class.getResource("/GameInsight.fxml"));

        Pane root = main.load();

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));
        stage.setTitle("Game Insight");
        stage.setResizable(false);
        stage.getIcons().add(new Image(GameInsight.class.getResource("/icons/applicationLogo.png").toExternalForm()));


        //private static GameNetworking gameNetworking;
        GameInsightController insightController = main.getController();

        stage.show();

        getLOGGER().logp(Level.INFO, "GameInsight.java","#start(Stage stage);","Successfully initialized the application!");
        insightController.initMain();

        ExpandedProfileController.init();

        if (!root.isVisible())
            root.setVisible(true);
    }

    public static void main(String[] args) {
        //gameNetworking = new GameNetworking();

        setTheme(Theme.DARK);

        getLOGGER().logp(Level.INFO, "GameInsight.java","#main(String[] args]);","Successfully initialized all utilities!");

        launch(args);
    }

    /**
     * <b>Sets Application Theme</b>
     *
     * @param theme The theme you wish to be active
     */
    public static void setTheme(Theme theme) {
        currentTheme = theme;
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }

    public Stage getStage() {
        return stage;
    }

    public Theme getCurrentTheme() {
        return currentTheme;
    }

    public Theme getFallbackTheme() {
        return fallbackTheme;
    }

    //public static GameNetworking getGameNetworking() {
    //    return gameNetworking;
    //}
    public static GameInsight getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static class InstanceHolder {
        public static final GameInsight INSTANCE = new GameInsight();
    }
}
