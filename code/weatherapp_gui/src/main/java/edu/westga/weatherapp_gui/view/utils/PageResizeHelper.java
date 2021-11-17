package edu.westga.weatherapp_gui.view.utils;

import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

/**
 * The PageResizeHelper class helps to resize the current page
 * 
 * @author Eboni Walker
 * @version Nov 14, 2021
 */
public class PageResizeHelper {

    private static final double CENTER_OFFSET = 2;

    private static final int INITIAL_VALUE = 0;

    private static final double RATIO_BOUNDARY = 1;

    private static final double PAGE_SIZE_1 = 756.0;

    private static final double PAGE_SIZE_2 = 600.0;

    private static final double PAGE_SIZE_3 = 577.0;

    private static final double PAGE_SIZE_1_MENU_BAR_TRANSLATE_X = 130;

    private static final double PAGE_SIZE_2_MENU_BAR_TRANSLATE_X = 208;

    private static final double PAGE_SIZE_3_MENU_BAR_TRANSLATE_X = 220;

    private static final double PAGE_SIZE_1_MENU_BAR_TRANSLATE_Y = 200;

    private static final double PAGE_SIZE_2_MENU_BAR_TRANSLATE_Y = 330;

    private static final double PAGE_SIZE_3_MENU_BAR_TRANSLATE_Y = 340;

    private static final String MAXIMIZED_STYLE = "-fx-padding:  2 2 2 60";

    private static final String MINIMIZED_STYLE = "-fx-padding:  2 2 2 2";

    private double menuBarTranslateY;

    private double menuBarTranslateX;

    private Pane pagePane;

    private boolean canScaleNow;

    /**
     * Sets the scaling rules for the page
     * 
     * @param pagePane the page's root pane
     */
    public void setScalingRules(Pane pagePane) {
        this.pagePane = pagePane;
        this.canScaleNow = true;
        Stage currStage = (Stage) this.pagePane.getScene().getWindow();
        double initWidth = pagePane.getPrefWidth();
        double initHeight = pagePane.getPrefHeight();
        MenuBar settingMenuBar = this.findSettingMenuBar(this.pagePane);
        this.bindMenuBarWidthIfNotNull(settingMenuBar, currStage);
        this.determineMenuBarCoordinateTranslations();
        this.setChangeListeners(initWidth, initHeight, settingMenuBar, currStage);

        this.preserveMaximizedState(currStage, settingMenuBar);
    }

    private void preserveMaximizedState(Stage currStage, MenuBar settingMenuBar) {
        boolean wasMaximized = false;
        if (currStage.getUserData() != null) {
            wasMaximized = ((Boolean) currStage.getUserData());
        }
        if (wasMaximized) {
            currStage.hide();
            currStage.setMaximized(wasMaximized);
            this.canScaleNow = false;
            currStage.show();
            this.setMenuBarLayoutXIfNotNull(settingMenuBar, this.menuBarTranslateX);
        }

    }

    private void determineMenuBarCoordinateTranslations() {
        if (this.pagePane.getPrefHeight() == PageResizeHelper.PAGE_SIZE_1) {
            this.menuBarTranslateY = PAGE_SIZE_1_MENU_BAR_TRANSLATE_X;
            this.menuBarTranslateX = PAGE_SIZE_1_MENU_BAR_TRANSLATE_Y;
        } else if (this.pagePane.getPrefHeight() == PageResizeHelper.PAGE_SIZE_2) {
            this.menuBarTranslateY = PAGE_SIZE_2_MENU_BAR_TRANSLATE_X;
            this.menuBarTranslateX = PAGE_SIZE_2_MENU_BAR_TRANSLATE_Y;
        } else if (this.pagePane.getPrefHeight() == PageResizeHelper.PAGE_SIZE_3) {
            this.menuBarTranslateY = PAGE_SIZE_3_MENU_BAR_TRANSLATE_X;
            this.menuBarTranslateX = PAGE_SIZE_3_MENU_BAR_TRANSLATE_Y;
        }
    }

    private void setChangeListeners(double initWidth, double initHeight, MenuBar settingMenuBar, Stage currStage) {
        this.setPaneWidthChangeListener(initWidth, initHeight, settingMenuBar);
        this.setPaneHeightChangeListener(initWidth, initHeight, settingMenuBar);
        this.setStageMaximizedChangeListener(currStage, settingMenuBar, initWidth, initHeight);
    }

    private void setStageMaximizedChangeListener(Stage currStage, MenuBar settingMenuBar, double initWidth,
            double initHeight) {
        currStage.maximizedProperty().addListener((observableMaximized, oldMaximized, newMaximized) -> {
            if (newMaximized) {
                this.canScaleNow = false;
                this.setMenuBarLayoutIfNotNull(settingMenuBar, this.menuBarTranslateX, PageResizeHelper.INITIAL_VALUE,
                        PageResizeHelper.MAXIMIZED_STYLE);
            } else {
                this.canScaleNow = true;
                this.setMenuBarLayoutIfNotNull(settingMenuBar, PageResizeHelper.INITIAL_VALUE, -this.menuBarTranslateY,
                        PageResizeHelper.MINIMIZED_STYLE);
                this.pagePane.snapPositionX(PageResizeHelper.INITIAL_VALUE);
                this.pagePane.snapPositionY(PageResizeHelper.INITIAL_VALUE);
                this.pagePane.snapPositionX(this.pagePane.getWidth() / PageResizeHelper.CENTER_OFFSET);
                this.pagePane.snapPositionY(this.pagePane.getHeight() / PageResizeHelper.CENTER_OFFSET);
            }
        });
    }

    private void setPaneHeightChangeListener(double initWidth, double initHeight, MenuBar settingMenuBar) {
        this.pagePane.heightProperty().addListener((observableHeight, oldHeight, newHeight) -> {
            this.setLayoutNodesTranlateY(newHeight.doubleValue(), oldHeight.doubleValue());
            this.helpScaleNodesWithMenu(initWidth, initHeight, settingMenuBar);
        });
    }

    private void setPaneWidthChangeListener(double initWidth, double initHeight, MenuBar settingMenuBar) {
        this.pagePane.widthProperty().addListener((observableWidth, oldWidth, newWidth) -> {
            this.setLayoutNodesTranlateX(newWidth.doubleValue(), oldWidth.doubleValue());
            this.helpScaleNodesWithMenu(initWidth, initHeight, settingMenuBar);
        });
    }

    private void helpScaleNodesWithMenu(double initWidth, double initHeight, MenuBar settingMenuBar) {
        double centerX = this.pagePane.getLayoutBounds().getCenterX();
        double centerY = this.pagePane.getLayoutBounds().getCenterY();
        if (this.canScaleNow) {
            this.scaleLayoutNodes(initWidth, initHeight, PageResizeHelper.INITIAL_VALUE,
                    PageResizeHelper.INITIAL_VALUE);
        } else {
            this.setMenuBarTranslateYIfNotNull(settingMenuBar, this.menuBarTranslateY);
            this.scaleLayoutNodes(initWidth, initHeight, centerX, centerY);
        }
    }

    private void scaleLayoutNodes(double initWidth, double initHeight, double pivotX, double pivotY) {
        double newHeight = this.pagePane.getHeight();
        double newWidth = this.pagePane.getWidth();
        double ratio = initWidth - initHeight;
        double scaleFactor = this.determineScaleFactor(newWidth, newHeight, initWidth, initHeight, ratio);
        if (scaleFactor >= PageResizeHelper.RATIO_BOUNDARY) {
            Scale scale = new Scale(scaleFactor, scaleFactor);
            scale.setPivotX(pivotX);
            scale.setPivotY(pivotY);
            this.pagePane.getScene().getRoot().getTransforms().setAll(scale);
        } else {
            this.pagePane.setPrefWidth(Math.max(initWidth, newWidth));
            this.pagePane.setPrefHeight(Math.max(initHeight, newHeight));
        }
    }

    private void setLayoutNodesTranlateX(double newWidth, double oldWidth) {
        for (Node child : this.pagePane.getChildren()) {
            if (!(child instanceof MenuBar)) {
                child.setLayoutX(child.getLayoutX() + ((newWidth - oldWidth) / PageResizeHelper.CENTER_OFFSET));
            }
        }
    }

    private void setLayoutNodesTranlateY(double newHeight, double oldHeight) {
        for (Node child : this.pagePane.getChildren()) {
            if (!(child instanceof MenuBar)) {
                child.setLayoutY(child.getLayoutY() + ((newHeight - oldHeight) / PageResizeHelper.CENTER_OFFSET));
            }
        }
    }

    private void bindMenuBarWidthIfNotNull(MenuBar settingMenuBar, Stage currStage) {
        if (settingMenuBar != null) {
            settingMenuBar.minWidthProperty().bind(currStage.widthProperty());
        }
    }

    private void setMenuBarLayoutIfNotNull(MenuBar settingMenuBar, double layoutX, double layoutY, String padding) {
        if (settingMenuBar != null) {
            settingMenuBar.setLayoutY(layoutY);
            settingMenuBar.setLayoutX(layoutX);
            settingMenuBar.getMenus().get(PageResizeHelper.INITIAL_VALUE).setStyle(padding);
        }
    }

    private void setMenuBarTranslateYIfNotNull(MenuBar settingMenuBar, double translateY) {
        if (settingMenuBar != null) {
            settingMenuBar.setTranslateY(translateY);
        }
    }

    private void setMenuBarLayoutXIfNotNull(MenuBar settingMenuBar, double layoutX) {
        if (settingMenuBar != null) {
            settingMenuBar.setLayoutX(layoutX);
        }
    }

    private double determineScaleFactor(double newWidth, double newHeight, double initWidth, double initHeight,
            double ratio) {
        double scaleFactor = PageResizeHelper.INITIAL_VALUE;
        if (newWidth / newHeight > ratio) {
            scaleFactor = newHeight / initHeight;
        } else {
            scaleFactor = newWidth / initWidth;
        }
        return scaleFactor;
    }

    private MenuBar findSettingMenuBar(Pane pagePane) {
        MenuBar bar = null;
        for (Node child : this.pagePane.getChildren()) {
            if (child instanceof MenuBar) {
                bar = (MenuBar) child;
            }
        }
        return bar;
    }
}