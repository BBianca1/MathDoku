
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class GUI extends Application{

	public static void main(String[] args) {
		
		
		
		launch(args);
	}

	public void start(Stage stage) throws Exception {


		TilePane pane = new TilePane(Orientation.HORIZONTAL);
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(10);
		pane.setVgap(10);
		
		Button undoBtn = new Button("Undo");
		Button redoBtn = new Button("Redo");
		Button loadBtn = new Button("Load game");
		CheckBox showMistakes = new CheckBox("Show mistakes");
		showMistakes.setSelected(true);
		
        pane.getChildren().addAll(undoBtn, redoBtn, loadBtn, showMistakes);
		
        Scene scene = new Scene(pane,450,150);
		
        stage.setScene(scene);
        stage.setTitle("My Lunch App");

        if(showMistakes != null) {
        	//show mistakes
        }
        
        stage.show();
	}


}
