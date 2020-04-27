import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UndoRedo {

	VBox buttonPane;
	Stack<Integer> undoId;
	Stack<Integer> undoValues;
	Stack<Integer> redoId;
	Stack<Integer> redoValues;
	HashMap<Integer, TextField> textFields; // cellId -> textFields
	Stack<Integer> values;
	Stack<Integer> ids;
	
	public UndoRedo(Grid grid) {
	
		Button undoBtn = new Button("Undo");
		Button redoBtn = new Button("Redo");
		Button clearBtn = new Button("Clear");
		buttonPane = new VBox();
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.getChildren().addAll(undoBtn, redoBtn, clearBtn);
		undoId = new Stack<Integer>();
		undoValues = new Stack<Integer>();
		redoId = new Stack<Integer>();
		redoValues = new Stack<Integer>();
		undoValues = grid.getUndoValues();
		undoId = grid.getUndoId();
		textFields = grid.getTextFields();
		values = new Stack<Integer>();
		ids = new Stack<Integer>();
		//undoes the last move
		undoBtn.setOnAction(undoEvent -> {
			
			int id = undoId.pop();
			redoId.push(id);
			redoValues.push(undoValues.pop());
			TextField tf = textFields.get(id);
			if(redoValues.peek() != null) {
				
				while(undoId.peek() != id) {
					
					values.push(undoValues.pop());
					ids.push(undoId.pop());
				}
				if(undoValues.peek() == null) {
					
					tf.setText("");
					undoValues.pop();
					undoId.pop();
				}
				else {
					
					tf.setText(Integer.toString(undoValues.peek()));
//					undoId.pop();
					
				}
				while(!values.empty()){
					
					undoValues.push(values.pop());
					undoId.push(ids.pop());
				}
			}
			else {
				
				tf.setText("");
			}
		});
		
		//redoes the last move
		redoBtn.setOnAction(redoEvent -> {
			
			int id = redoId.pop();
			int val = redoValues.pop();
			TextField tf = textFields.get(id);
			if(undoId.search(id) < 0){
        		
        		undoId.push(id);
        		undoValues.push(null);
        	}
			
			tf.setText(Integer.toString(val));
			undoValues.push(val);
			undoId.push(id);
		});
		
		//clears the board
		clearBtn.setOnAction(clearEvent -> {
			
			TextArea ta = new TextArea("Are you sure you want to clear the board?");
			ta.setBackground(Background.EMPTY);
			ta.setPrefSize(300, 100);
			Button yesBtn = new Button("Yes");
			Button noBtn = new Button("No");
			VBox popUp = new VBox(10);
			HBox yn = new HBox(5);
			popUp.setAlignment(Pos.CENTER);
			yn.setAlignment(Pos.CENTER);
			yn.getChildren().addAll(yesBtn, noBtn);
			popUp.getChildren().addAll(ta, yn);
			Scene scene = new Scene(popUp, 300, 300);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			yesBtn.setOnAction(event ->{
				
				stage.close();
				for(TextField i : textFields.values()) {
					
					i.setText("");
				}
			});
			
		});
	}
	
	public VBox getButtonPane() {
		
		return buttonPane;
	}
	
}
