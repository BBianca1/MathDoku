import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Grid extends Application{

	GridPane grid;
	public Grid(int size) {
		
		
		grid = new GridPane();
		grid.setPadding(new Insets(10));
		grid.setVgap(10);
		grid.setHgap(10);
		
		Rectangle cell;
		
		Group squares = new Group();
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				cell = new Rectangle(80, 80);
				cell.setStroke(Color.CADETBLUE);
				cell.setFill(Color.BLACK);
				cell.relocate(i * 80, j * 80);
				squares.getChildren().addAll(cell);
			}
		}
		Scene scene = new Scene(squares,700,700);
		stage.setScene(scene);
	}
	
	public Pane getGrid() {
		
		return grid;
	}
	
	Stage stage;
	
	public void start(Stage primaryStage) throws Exception {
		
		stage = new Stage();
		int size = 6;
		Grid grid = new Grid(size);
		
		stage.show();
		
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}

}
