import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Size {

	HBox sizePane;
	Button small = new Button("Small");
	Button medium = new Button("Medium");
	Button large = new Button("Large");
	int size;
	VBox popUp;
	
	public Size() {

		TextArea ta = new TextArea("Please choose a font size.");
		ta.setBackground(Background.EMPTY);
		ta.setPrefSize(300, 100);
		popUp = new VBox(10);
		popUp.setAlignment(Pos.CENTER);
		small = new Button("Small");
		medium = new Button("Medium");
		large = new Button("Large");
		sizePane = new HBox(10);
		sizePane.getChildren().addAll(small, medium, large);
		sizePane.setAlignment(Pos.CENTER);
		popUp.getChildren().addAll(ta, sizePane);
		
		small.setOnAction(event -> {
			
			
		});
		
	}
	
	public int getSize() {
	
		return size;
	}
	
	public VBox getPopUp() {
		
		return popUp;
	}

	
}
