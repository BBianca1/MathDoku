import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		
		launch(args);
	}
		
	Stage stage = new Stage();
	FileChooser fileChooser = new FileChooser();
	File file;
		
	@Override
	public void start(Stage stage) throws Exception {
		
		//pain of the game
		BorderPane mainPane = new BorderPane();
		mainPane.setPadding(new Insets(10));

		//pain of the buttons
		TilePane btnPane = new TilePane();
		btnPane.setAlignment(Pos.CENTER);
		btnPane.setVgap(10);
		btnPane.setHgap(10);
		
		
		Button loadFromFile = new Button("Load from file");
		btnPane.getChildren().addAll(loadFromFile);

		mainPane.setLeft(btnPane);

		
		loadFromFile.setOnAction(
				new EventHandler<ActionEvent>() {
	                @Override
	                
					public void handle(final ActionEvent e) {
		
	                	file = fileChooser.showOpenDialog(stage);
	                	if(file != null) {
	                	int max = 0;
	                		
	                		try {
	                			Scanner scanner = new Scanner(file);
	                			String line = scanner.nextLine();
	                			
	                			//find the dimension of the grid
	                			while (scanner.hasNextLine()) {
	                				
	                				String split[] = line.split(" ");
	                				split = split[1].split(",");
	                				for(int i = 0; i < split.length; i++) {
	                					
	                					int integer = Integer.parseInt(split[i]);
	                					if (integer > max)
	                						max = integer;
	                				}
	                				line = scanner.nextLine();
	                			}
	                			System.out.println((int) Math.sqrt(max));
	                			scanner.close();
	                		} catch (FileNotFoundException e1) {
	                			
	                			e1.printStackTrace();
	                		}
	                	}
	                }
	            });
		
		Scene scene = new Scene(mainPane, 700, 700);	
		stage.setScene(scene);
		stage.show();
	}

}
