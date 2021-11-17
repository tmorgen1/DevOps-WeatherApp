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

    private static final double INITIAL_VALUE = 0;

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
    }

    private void determineMenuBarCoordinateTranslations() {
        if (this.pagePane.getPrefHeight() == 756.0) {
            this.menuBarTranslateY = 130;
            this.menuBarTranslateX = 200;
        } else if (this.pagePane.getPrefHeight() == 577.0) {
            this.menuBarTranslateY = 220;
            this.menuBarTranslateX = 340;
        }
    }

    private void setChangeListeners(double initWidth, double initHeight, MenuBar settingMenuBar, Stage currStage) {
        this.setPaneWidthChangeListener();
        this.setPaneHeightChangeListener();
        this.setPaneLayoutBoundsChangeListener(initWidth, initHeight, settingMenuBar);
        this.setStageMaximizedChangeListener(currStage, settingMenuBar);
    }

    private void setStageMaximizedChangeListener(Stage currStage, MenuBar settingMenuBar) {
        currStage.maximizedProperty().addListener((observableMaximized, oldMaximized, newMaximized) -> {
            if (newMaximized) {
                this.canScaleNow = false;
                settingMenuBar.setLayoutY(PageResizeHelper.INITIAL_VALUE);
                settingMenuBar.setLayoutX(this.menuBarTranslateX);
                settingMenuBar.getMenus().get(0).setStyle("-fx-padding:  2 2 2 45");
            } else {
                settingMenuBar.setLayoutY(-this.menuBarTranslateY);
                settingMenuBar.setLayoutX(PageResizeHelper.INITIAL_VALUE);
                settingMenuBar.getMenus().get(0).setStyle("-fx-padding:  2 2 2 2");
                this.canScaleNow = true;
                this.pagePane.snapPositionX(PageResizeHelper.INITIAL_VALUE);
                this.pagePane.snapPositionY(PageResizeHelper.INITIAL_VALUE);
                this.pagePane.snapPositionX(this.pagePane.getWidth() / PageResizeHelper.CENTER_OFFSET);
                this.pagePane.snapPositionY(this.pagePane.getHeight() / PageResizeHelper.CENTER_OFFSET);
            }
        });
    }

    private void setPaneLayoutBoundsChangeListener(double initWidth, double initHeight, MenuBar settingMenuBar) {
        this.pagePane.layoutBoundsProperty().addListener((observableBounds, oldBounds, newBounds) -> {
            double centerX = this.pagePane.getLayoutBounds().getCenterX();
            double centerY = this.pagePane.getLayoutBounds().getCenterY();
            if (this.canScaleNow) {
                this.scaleLayoutNodes(initWidth, initHeight, PageResizeHelper.INITIAL_VALUE,
                        PageResizeHelper.INITIAL_VALUE);
            } else {
                settingMenuBar.setTranslateY(this.menuBarTranslateY);
                this.scaleLayoutNodes(initWidth, initHeight, centerX, centerY);
            }
        });
    }

    private void setPaneHeightChangeListener() {
        this.pagePane.heightProperty().addListener((observableHeight, oldHeight, newHeight) -> {
            this.setLayoutNodesTranlateY(newHeight.doubleValue(), oldHeight.doubleValue());
        });
    }

    private void setPaneWidthChangeListener() {
        this.pagePane.widthProperty().addListener((observableWidth, oldWidth, newWidth) -> {
            this.setLayoutNodesTranlateX(newWidth.doubleValue(), oldWidth.doubleValue());
        });
    }

    private void scaleLayoutNodes(double initWidth, double initHeight, double pivotX, double pivotY) {
        double newHeight = this.pagePane.getHeight();
        double newWidth = this.pagePane.getWidth();
        double ratio = initWidth - initHeight;
        double scaleFactor = this.determineScaleFactor(newWidth, newHeight, initWidth, initHeight, ratio);
        if (scaleFactor >= 1) {
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