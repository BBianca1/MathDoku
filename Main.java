import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		
		launch(args);
	}
		
	Stage stage = new Stage();
	FileChooser fileChooser = new FileChooser();
	File file;
	Grid grid;
	boolean ok;
	UndoRedo undoRedo;
	BorderPane mainPane;
	int max;
		
	@Override
	public void start(Stage stage) throws Exception {
		
		//pain of the game
		mainPane = new BorderPane();
		mainPane.setPadding(new Insets(10));

		//pain of the buttons
		VBox btnPane = new VBox(5);
		btnPane.setAlignment(Pos.CENTER);
		
		Button loadFromFile = new Button("Load from file");
		Button loadFromText = new Button("Load from text");
		btnPane.getChildren().addAll(loadFromFile, loadFromText);
		mainPane.setCenter(btnPane);
		
		loadFromFile.setOnAction(
				new EventHandler<ActionEvent>() {
	                @Override
	                 
					public void handle(final ActionEvent e) {
		
	                	ok = true;
	                	file = fileChooser.showOpenDialog(stage);
	                	if(file != null) {
	                	max = 0;
	                		
	                		try {
	                			Scanner scanner = new Scanner(file);
	                			String line ;
	                			ArrayList<Integer> checkCells = new ArrayList<Integer>();
	                			for (int i = 0; i < 65; i++) {
	                				  checkCells.add(i, 0);
	                			}
	                			
	                			//find the dimension of the grid
	                			while (scanner.hasNextLine()) {
	                				
	                				int integer;
	                				line = scanner.nextLine();
	                				String split[] = line.split(" ");
	                				if(split.length == 2) {
	                					if(split[0].replaceAll("([+, -, x, ÷, 0-9])", "").equals("") ||
	                							 split[0].replaceAll("([+, -, x, ÷, 0-9])", "").substring(0, split[0].length() - 1).equals("-")) {
	                						
	                						split = split[1].split(",");
	                						
	                						for(int i = 0; i < split.length; i++) {
	                							
	                							integer = Integer.parseInt(split[i]);
	                							checkCells.add(integer, 1);
	                							checkCells.remove(integer + 1);
	                							if (integer > max)
	                								max = integer;
	                						}
	                					}
	                					else {
	                						
	                						if(!"([+, -, x, ÷, 0-9])".contains(split[0].substring(split[0].length() - 1)) && split[0].length() > 2) {
		                						
		                						split[0] = split[0].substring(0, split[0].length() - 1);
		                						split[0] = split[0].replace(split[0].substring(split[0].length() - 1), "÷");
		                						split = split[1].split(",");
		                						for(int i = 0; i < split.length; i++) {
		                							
		                							integer = Integer.parseInt(split[i]);
		                							checkCells.add(integer, 1);
		                							checkCells.remove(integer + 1);
		                							if (integer > max)
		                								max = integer;
		                						}
		                					}
		                					else {
		                						
		                						ok = false;
		                						break;
		                					}
	                					}
	                				}
	                				else{
                						
	                					ok = false;
                						break;
                					}
	                			}
	                			if(max == (int) Math.sqrt(max) * (int) Math.sqrt(max)) {
	                				
	                				for(int i = 1; i <= max; i++) {
	                					
	                					if(checkCells.get(i) == 0) {
	                						
	                						ok = false;
	                						break;
	                					}
	                				}
	                			}
	                			else
            						
                					ok = false;
	                			if(ok == true) {
		                				
	                				mainPane = new BorderPane();
	                				Cage cage = new Cage(file, (int) Math.sqrt(max));
	                				scanner.close();
	                				CheckBox showMistakes = new CheckBox("Show mistakes");
	                				grid = new Grid((int) Math.sqrt(max), cage, showMistakes.isSelected());
	                				undoRedo = new UndoRedo(grid);
	                				VBox btnPane = undoRedo.getButtonPane();
	                				btnPane.getChildren().add(showMistakes);
	                				showMistakes.setSelected(false);
	                				this.showMistakes(showMistakes);
	                				mainPane.setRight(btnPane);
	                				mainPane.setCenter(grid.getGamePane());
	                				Scene scene = new Scene(mainPane, 700, 700);
	                				stage.setScene(scene);
	                				stage.show();
		                			}
	                			else {
	                				try {

		                				TextArea ta = new TextArea("The file dose not have the corect format!");
		                				mainPane.setTop(ta);
		                				Scene scene = new Scene(mainPane, 700, 700);
		                				stage.setScene(scene);
		                				stage.show();
									} catch (IllegalArgumentException e2) {
										// TODO: handle exception
									}
	                			}
	                			
	                		} catch (FileNotFoundException e1) {
	  
	                			e1.printStackTrace();
	                		}
	                	}
	                }
	                public void showMistakes(CheckBox cBox) {
	                	
	                	cBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
	                		
	                		@Override
	                		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	                			
	                			if(oldValue.booleanValue() != newValue.booleanValue()) {
	                				
	                				mainPane = new BorderPane();
		                			Cage cage = null;
		                			try {
		                				cage = new Cage(file, (int) Math.sqrt(max));
		                			} catch (FileNotFoundException e) {
		                				// TODO Auto-generated catch block
		                				e.printStackTrace();
		                			}
//		                			CheckBox showMistakes = new CheckBox("Show mistakes");
		                			grid = new Grid((int) Math.sqrt(max), cage, newValue.booleanValue());
		                			undoRedo = new UndoRedo(grid);
		                			VBox btnPane = undoRedo.getButtonPane();
		                			btnPane.getChildren().add(cBox);
		                			cBox.setSelected(newValue.booleanValue());
		                			mainPane.setRight(btnPane);
		                			mainPane.setCenter(grid.getGamePane());
		                			Scene scene = new Scene(mainPane, 700, 700);
		                			stage.setScene(scene);
		                			stage.show();
	                			}
	                		}
	                	});
	                }
	            });
		
		Scene scene = new Scene(mainPane, 700, 700);
		stage.setMinHeight(900);
		stage.setMinWidth(900);
		stage.setScene(scene);
		stage.show();
	}
	
	

}
