package de.gravitex.bpmn.client.fx;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.camunda.bpm.engine.repository.DiagramNode;

import de.gravitex.bpmn.client.singleton.ProcessingSingleton;
import de.gravitex.bpmn.server.dto.DiagramElementDTO;

@SuppressWarnings("restriction")
public class FxProcessViewer extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    private Group renderingTarget;

    private int translateY = 0;

    private int translateX = 0;

    private double maxRenderBoundsX;
    
    private double maxRenderBoundsY;

    private Parent zoomPane;

    public void start(final Stage stage)
    {
        buildRenderingTarget();
        
        createZoomPane();
     
        VBox layout = new VBox();
        layout.getChildren().setAll(createMenuBar(stage), zoomPane);

        VBox.setVgrow(zoomPane, Priority.ALWAYS);
        Scene scene = new Scene(layout);
        System.out.println("getAntiAliasing : " + scene.getAntiAliasing());

        stage.setTitle("Process viewer");
        stage.setWidth(maxRenderBoundsX);
        stage.setHeight(maxRenderBoundsY);
        stage.setScene(scene);
        stage.show();   
    }

    private void buildRenderingTarget()
    {
        Collection<Node> nodes = createNodes();
        renderingTarget = new Group();
        renderingTarget.getChildren().addAll(nodes);
    }

    private Collection<Node> createNodes()
    {
    	Collection<Node> nodes = new ArrayList<Node>();
    	try
		{
    		DiagramNode diagramNode = null;
    		Rectangle rect = null;
			for (DiagramElementDTO dto : ProcessingSingleton.getInstance().queryDiagramElements("SignalTestReviewProcess:1:5", "SignalTestReviewProcess"))
			{
				if (dto.getElement() instanceof DiagramNode)
				{
					diagramNode = (DiagramNode) dto.getElement();
					System.out.println("rect for id : " + diagramNode.getId() + " :: [type:"+dto.getBpmn2TypeName()+"]");
					rect = new Rectangle(diagramNode.getX(), diagramNode.getY(), diagramNode.getWidth(), diagramNode.getHeight());
					rect.setFill(Color.TRANSPARENT);
					rect.setStroke(Color.BLACK);
					rect.setStrokeWidth(1);
					nodes.add(rect);
					nodes.add(new Text(diagramNode.getX(), diagramNode.getY(), dto.getBpmn2TypeName()));
				}
			}
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
        return nodes;
    }
    
    @SuppressWarnings("incomplete-switch")
    private void handleTranslation(KeyEvent e)
    {
        System.out.println("OnKeyReleased : " + e.getCode());
        switch (e.getCode())
        {
            case UP:
                retranslateTarget(0, 1, false);
                break;
            case DOWN:
                retranslateTarget(0, -1, false);
                break;
            case LEFT:
                retranslateTarget(1, 0, false);
                break;
            case RIGHT:
                retranslateTarget(-1, 0, false);
                break;
        }
    }

    private void retranslateTarget(double diffX, double diffY, boolean dragged)
    {
        translateX += diffX;
        translateY += diffY;
        System.out.println("translating to [x:" + translateX + "|y:" + translateY + "].");
        renderingTarget.setTranslateX(dragged
                ? (translateX - (maxRenderBoundsX / 2))
                : translateX);
        renderingTarget.setTranslateY(dragged
                ? (translateY - (maxRenderBoundsY / 2))
                : translateY);
    }

    private void createZoomPane()
    {
        final double SCALE_DELTA = 1.1;
        zoomPane = new StackPane();

        ((StackPane) zoomPane).getChildren().add(renderingTarget);

        // necessary for enabling key pressed events...
        zoomPane.setFocusTraversable(true);

        zoomPane.setOnMouseDragged(new EventHandler<MouseEvent>()
        {
            private double oldX;

            private double oldY;

            public void handle(MouseEvent e)
            {
                double diffX = e.getX() - oldX;
                double diffY = e.getY() - oldY;
                System.out.println("setOnMouseDragged : [diffX:" + diffX + "|diffY:" + diffY + "]");
                retranslateTarget(diffX, diffY, true);
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        zoomPane.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent e)
            {
                System.out.println("OnKeyPressed : " + e.getCode());
                handleTranslation(e);
            }
        });

        zoomPane.setOnScroll(new EventHandler<ScrollEvent>()
        {
            public void handle(ScrollEvent event)
            {
                event.consume();

                if (event.getDeltaY() == 0)
                {
                    return;
                }

                double scaleFactor = (event.getDeltaY() > 0)
                        ? SCALE_DELTA
                        : 1 / SCALE_DELTA;

                renderingTarget.setScaleX(renderingTarget.getScaleX() * scaleFactor);
                renderingTarget.setScaleY(renderingTarget.getScaleY() * scaleFactor);
            }
        });

        zoomPane.layoutBoundsProperty().addListener(new ChangeListener<Bounds>()
        {
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldBounds, Bounds bounds)
            {
                zoomPane.setClip(new Rectangle(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(),
                        bounds.getHeight()));
            }
        });              
    }

    private MenuBar createMenuBar(final Stage stage)
    {
        Menu fileMenu = new Menu("_File");
        MenuItem exitMenuItem = new MenuItem("E_xit");
        exitMenuItem.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                stage.close();
            }
        });
        fileMenu.getItems().setAll(exitMenuItem);
        Menu zoomMenu = new Menu("_Zoom");
        MenuItem zoomResetMenuItem = new MenuItem("Zoom _Reset");
        zoomResetMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.ESCAPE));
        zoomResetMenuItem.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                resetScalingAndZoom();
            }
        });
        MenuItem zoomInMenuItem = new MenuItem("Zoom _In");
        zoomInMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.I));
        zoomInMenuItem.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                renderingTarget.setScaleX(renderingTarget.getScaleX() * 1.5);
                renderingTarget.setScaleY(renderingTarget.getScaleY() * 1.5);
            }
        });
        MenuItem zoomOutMenuItem = new MenuItem("Zoom _Out");
        zoomOutMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O));
        zoomOutMenuItem.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                renderingTarget.setScaleX(renderingTarget.getScaleX() * 1 / 1.5);
                renderingTarget.setScaleY(renderingTarget.getScaleY() * 1 / 1.5);
            }
        });
        MenuItem applyModelItem = new MenuItem("Apply Model");
        applyModelItem.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                System.out.println("applyModelItem");
            }
        });
        zoomMenu.getItems().setAll(zoomResetMenuItem, zoomInMenuItem, zoomOutMenuItem, applyModelItem);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().setAll(fileMenu, zoomMenu);
        return menuBar;
    }
    
    private void resetScalingAndZoom()
    {
        renderingTarget.setScaleX(1);
        renderingTarget.setScaleY(1);
        
        renderingTarget.setTranslateX(0);
        renderingTarget.setTranslateY(0);
    }    
}