import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Grid{

	VBox gamePane;
	Group grid;
	HBox btnPane;
	HashMap<Integer, Rectangle> cells; //cellId -> cell
	HashMap<Integer, Integer> cages; //cellId -> cageId
	HashMap<Integer, Integer> values; //cellId -> cell value
	HashMap<Integer, TextField> textFields; //cellId -> text field
	Scene scene;
	StackPane cellPane = null;
	TextField noField;
	int size;
	Stack<Integer> undoValues;
	Stack<Integer> undoId;

	public Grid(int size, Cage cage, int fontSize) {
		
		undoValues = new Stack<Integer>();
		undoId = new Stack<Integer>();
		this.size = size;
		gamePane = new VBox(10);
		gamePane.setAlignment(Pos.CENTER);
		btnPane = new HBox(5);
		grid = new Group();//made of stackPane
		textFields = new HashMap<Integer, TextField>();
		Rectangle cell;
		cells = new HashMap<Integer, Rectangle>(); //cellId -> cell
		values = new HashMap<Integer, Integer>();
		ArrayList<Border> borders = cage.getCages();
		HashMap<Integer, Integer> firstCells  = cage.getFirstCells(); //cageId -> cellId of the first cell of the cage
		cages = cage.getCells();//cellId -> cageId
		HashMap<Integer, String> operations = cage.getOperations();
		int dimentions = 100;
		TextArea opertaionArea;
		if(size > 6)
			
			dimentions = 80;
		
		for(int i = 0; i < size; i++) {

			for(int j = 0; j < size; j++) {
				
				cell = new Rectangle(dimentions, dimentions);
				cell.setFill(Color.WHITE);
				cell.setStroke(Color.BLACK);
				Integer id = new Integer(size*i + j + 1);
				values.put(id, null);
				cells.put(id, cell);
				cellPane = new StackPane();
				noField = new TextField();
				noField.setFont(Font.font("Verdana", FontWeight.NORMAL, fontSize));
				textFields.put(id, noField);
				noField.setFocusTraversable(true);
				this.textCheck(noField, id);
				noField.setBackground(Background.EMPTY);
				noField.setAlignment(Pos.CENTER);
				noField.setPrefSize(dimentions, dimentions);
				cellPane.getChildren().add(cell);
				int cellId = firstCells.get(cages.get(id));
				if(id == cellId){
					
					opertaionArea = new TextArea(operations.get(cages.get(id)));
					opertaionArea.setFont(Font.font("Verdana", FontWeight.NORMAL, fontSize));
					opertaionArea.setPrefSize(dimentions, dimentions);
					opertaionArea.setEditable(false);
					cellPane.getChildren().add(opertaionArea);
				}
				cellPane.getChildren().add(noField);
				cellPane.relocate(j * dimentions, i * dimentions);
				cellPane.setBorder(borders.get(id));
				grid.getChildren().add(cellPane);
				}
			
			
			Button noBtn = new Button(Integer.toString(i+1));
			btnPane.getChildren().add(noBtn); 
			btnPane.setAlignment(Pos.CENTER);
		}
		gamePane.getChildren().addAll(grid, btnPane);
		gamePane.setAlignment(Pos.CENTER);
		gamePane.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	public void checkMistakes(TextField tf, int id) {
		
		tf.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				boolean ok = true; //we presume that the value respects the rules
				if (newValue.intValue() > oldValue.intValue()) {
					
					if(id % size != 0)
					for(int i = 0 ; i < size; i++) {
						
						//checkesa it column
						if(id != size * i + id % size ) {
							
							if(Integer.toString(values.get(size * i + id % size )) == tf.getText())
								
								ok = false;
						}
						
						if(id != i + size + id / size)
							
							if(Integer.toString(values.get(i + size + id / size)) == tf.getText())
								
								ok = false;
					}
				}
//				if(ok == false)
					
			}
			
			
		});
		
	}
	
	//checks if the given text is acceptable
	public void textCheck(TextField tf, int id){
		
		tf.lengthProperty().addListener(new ChangeListener<Number>() {

	        @Override
	        public void changed(ObservableValue<? extends Number> observable,
	                Number oldValue, Number newValue) {
	        	if (newValue.intValue() > oldValue.intValue()) {
		        	
	        		// Check if the new character is greater than 1
	        		if (tf.getText().length() > 1) {
	        			
	        			tf.setText(tf.getText().substring(0, 1));
	        		}
		            //checks if the number in the field is biger then the size of the grid
		        	try {
						
		            	if(Integer.parseInt(tf.getText()) > size)
			            	
		            		tf.setText("");
		            	//eliminates all the characters
		            	if(!tf.getText().replaceAll("([0-9])", "").equals("")) {
		            		
		            		tf.setText("");
		            	}
		            	values.put(id, Integer.parseInt(tf.getText()));
		            	if(undoId.search(id) < 0){
		            		
		            		undoId.push(id);
		            		undoValues.push(null);
		            	}
		            	undoId.push(id);
	            		undoValues.push(Integer.parseInt(tf.getText()));
					} catch (NumberFormatException e) {
						
						//handels the case when the cell is empty
					}
		        	
	            }
	        }
	    });
	}
	
	public HashMap<Integer, TextField> getTextFields() {
	
		return textFields;
	}
	
	public Stack<Integer> getUndoId() {
	
		return undoId;
	}
	
	public Stack<Integer> getUndoValues() {
	
		return undoValues;
	}
	
	public HashMap<Integer, Rectangle> getCells() {
		
		return cells;//cellId -> cell
	}
	
	public HashMap<Integer, Integer> getCages() {
	
		return cages;//cellId - > cageId
	}
	
	public int getSize() {
	
		return size;
	}
	
	public VBox getGamePane() {
	
		return gamePane;
	}
	
	public Scene getScene() {
		
		return scene;
	}
	

}
