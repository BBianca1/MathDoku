
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class Grid extends Application{

	Stage stage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		stage = new Stage();
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		grid.getStyleClass().add("game-grid");
		
		Scene scene = new Scene(grid,450,150);
		scene.getStylesheets().add("MathDoku.css");
		stage.show();
		
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}

}
