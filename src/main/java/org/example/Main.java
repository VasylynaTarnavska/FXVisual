package org.example;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Main extends Application {
    private double mousePosX, mousePosY;
    private double mouseOldX, mouseOldY;
    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);

    @Override
    public void start(Stage primaryStage) {

        float width = 100;
        float height = 150;
        float depth = 200;

        TriangleMesh parallelepipedMesh = new TriangleMesh();

        // Define the points of the parallelepiped
        float[] points = {
                -width / 2, -height / 2, -depth / 2, // 0
                width / 2, -height / 2, -depth / 2,  // 1
                -width / 2, height / 2, -depth / 2,  // 2
                width / 2, height / 2, -depth / 2,   // 3
                -width / 2, -height / 2, depth / 2,  // 4
                width / 2, -height / 2, depth / 2,   // 5
                -width / 2, height / 2, depth / 2,   // 6
                width / 2, height / 2, depth / 2     // 7
        };
        parallelepipedMesh.getPoints().addAll(points);

        // Define the texture coordinates for each point (not used in this example)
        float[] texCoords = {
                0, 0,
                1, 0,
                0, 1,
                1, 1
        };
        parallelepipedMesh.getTexCoords().addAll(texCoords);

        // Define the faces of the parallelepiped
        int[] faces = {
                0, 0, 2, 0, 1, 0,
                1, 0, 2, 0, 3, 0,
                1, 0, 3, 0, 5, 0,
                3, 0, 7, 0, 5, 0,
                7, 0, 6, 0, 5, 0,
                6, 0, 7, 0, 4, 0,
                6, 0, 4, 0, 2, 0,
                2, 0, 4, 0, 0, 0,
                0, 0, 4, 0, 1, 0,
                1, 0, 4, 0, 5, 0,
                2, 0, 6, 0, 3, 0,
                3, 0, 6, 0, 7, 0
        };
        parallelepipedMesh.getFaces().addAll(faces);

        MeshView parallelepiped = new MeshView(parallelepipedMesh);
        parallelepiped.setDrawMode(DrawMode.FILL);
        parallelepiped.setMaterial(new javafx.scene.paint.PhongMaterial(Color.BLUE));

        Group root = new Group(parallelepiped);
        Scene scene = new Scene(root, 400, 400, true);

        // Add mouse event handlers to rotate the parallelepiped
        scene.setOnMousePressed((MouseEvent me) -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        scene.setOnMouseDragged((MouseEvent me) -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            double deltaX = (mousePosX - mouseOldX);
            double deltaY = (mousePosY - mouseOldY);
            if (me.isPrimaryButtonDown()) {
                rotateX.setAngle(rotateX.getAngle() - deltaY);
                rotateY.setAngle(rotateY.getAngle() + deltaX);
            }
        });

        // Set up the camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-800);
        camera.setFieldOfView(30);
        scene.setCamera(camera);

        // Apply the rotation transforms to the parallelepiped
        parallelepiped.getTransforms().addAll(rotateX, rotateY);

        primaryStage.setTitle("Parallelepiped");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}