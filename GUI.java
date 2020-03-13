

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class GUI extends Application{

	public static void main(String[] args) {
		
		
		
		launch(args);
	}

	public void start(Stage stage) throws Exception {


		BorderPane mainPane = new BorderPane();
		mainPane.setPadding(new Insets(10));
		
		Grid grid = new Grid(5);
		
		TilePane pane = new TilePane(Orientation.HORIZONTAL);
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(10);
		pane.setVgap(10);
		
		Button undoBtn = new Button("Undo");
		Button redoBtn = new Button("Redo");
		Button loadBtn = new Button("Load game from file");
		CheckBox showMistakes = new CheckBox("Show mistakes");
		showMistakes.setSelected(true);
		
        pane.getChildren().addAll(undoBtn, redoBtn, loadBtn, showMistakes);
		mainPane.setLeft(pane);
		mainPane.setCenter(grid.getGrid());
		
        Scene scene = new Scene(mainPane,700,700);
		
        stage.setScene(scene);
        stage.setTitle("My Lunch App");

        if(showMistakes != null) {
        	//show mistakes
        }
        
        stage.show();
	}


}
