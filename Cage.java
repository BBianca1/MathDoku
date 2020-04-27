import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Cage {

	HashMap<Integer, Integer> cells; //cellId -> cageId
	HashMap<Integer, String> operations; //cageId -> the operation
	HashMap<Integer, Integer> firstCells; //cageId -> cellId of the first cell of the cage
	String split[];
	Boolean onLineL;
	Boolean onLineR;
	Boolean onColUp;
	Boolean onColDown;
	ArrayList<Border> cages;
	
	public Cage(File file, int size) throws FileNotFoundException {
	
		operations = new HashMap<Integer, String>();
		firstCells = new HashMap<Integer, Integer>();
		cells = new HashMap<Integer, Integer>(); //cellId - > cageId
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(file);
		int counter = 1;
		Border border2;
		cages = new ArrayList<Border>();
		for (int i = 0; i < size * size + 1; i++) {
			  cages.add(i, null);
		}
		BorderStroke border ;
		while(scanner.hasNextLine()) {
			
			String line = scanner.nextLine();
			split = line.split(" ");
//			System.out.println(line);
			if(!"([+, -, x, ÷, 1-9])".contains(split[0].substring(split[0].length() - 1)) && split[0].length() > 1) {
				
				split[0] = split[0].substring(0, split[0].length() - 1);
				split[0] = split[0].replace(split[0].substring(split[0].length() - 1), "÷");
			}
			operations.put(counter, split[0]);
			split = split[1].split(",");
			firstCells.put(counter, Integer.parseInt(split[0]));
			//creats the boarder for each cell
			for(int i = 0; i < split.length; i++) {
				int cellId1 = Integer.parseInt(split[i]);
				onLineL = true;
				onLineR = true;
				onColUp = true;
				onColDown = true;
				
				for(int j = 0; j < split.length; j++) {
					
					if(split[i] != split[j]) {
						
						int cellId2 = Integer.parseInt(split[j]);
						if((cellId1 % size != 0 && cellId2 % size != 0) || (cellId1 % size == 0 && cellId2 % size == 0)) {
							
							if( cellId1 / size == cellId2 / size) {
								
								if(cellId1 < cellId2 && onLineR)
									
									onLineR = false;
								else 
									
									if(cellId1 > cellId2 && onLineL)
										
										onLineL = false;
							}
							
							if( cellId1 % size == cellId2 % size) {
								
								if(cellId1 < cellId2 && onColDown)
									
									onColDown = false;
								else 
									
									if(cellId1 > cellId2 && onColUp)
										
										onColUp = false;
							}
						}
						else {
							
							if(cellId1 % size == 0){
								
								if( cellId1 / size - 1 == cellId2 / size) {
									
									if(cellId1 < cellId2 && onLineR)
										
										onLineR = false;
									
									if(cellId1 > cellId2 && onLineL)
										
										onLineL = false;
								}
							}
							else {
								
								if(cellId1 / size == cellId2 / size - 1) {
									
									if(cellId1 < cellId2 && onLineR)
										
										onLineR = false;
									else 
										
										if(cellId1 > cellId2 && onLineL)
											
											onLineL = false;
								}
							}
						}
					}
				}
				
				if(onLineR) {
					
					
					if(onLineL) {
						
						
						if(onColUp) {
							
							
							if(onColDown) {
								//full cage 
								border = new BorderStroke(Color.DARKCYAN, Color.DARKCYAN, Color.DARKCYAN, Color.DARKCYAN, 
														BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, 
														BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, 
														CornerRadii.EMPTY, new BorderWidths(4),
				                                        Insets.EMPTY
														);
							}
							else
								//no down border
								border = new BorderStroke(Color.DARKCYAN, Color.DARKCYAN, Color.TRANSPARENT, Color.DARKCYAN, 
										BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, 
										BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, 
										CornerRadii.EMPTY, new BorderWidths(4),
										Insets.EMPTY
										);
						}
						else {
							
							if(onColDown) {
								//no up border
								border = new BorderStroke(Color.TRANSPARENT, Color.DARKCYAN, Color.DARKCYAN, Color.DARKCYAN, 
														BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, 
														BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, 
														CornerRadii.EMPTY, new BorderWidths(4),
				                                        Insets.EMPTY
														);
							}
							else
								//no up and down borders
								border = new BorderStroke(Color.TRANSPARENT, Color.DARKCYAN, Color.TRANSPARENT, Color.DARKCYAN, 
										BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, 
										BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, 
										CornerRadii.EMPTY, new BorderWidths(4),
										Insets.EMPTY
										);
							}
						}
						else {
							
							if(onColUp) {
								
								
								if(onColDown) {
									//no left border
									border = new BorderStroke(Color.DARKCYAN, Color.DARKCYAN, Color.DARKCYAN, Color.TRANSPARENT, 
															BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, 
															BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, 
															CornerRadii.EMPTY, new BorderWidths(4),
					                                        Insets.EMPTY
															);
								}
								else
									//no left and down borders
									border = new BorderStroke(Color.DARKCYAN, Color.DARKCYAN, Color.TRANSPARENT, Color.TRANSPARENT, 
											BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, 
											BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, 
											CornerRadii.EMPTY, new BorderWidths(4),
											Insets.EMPTY
											);
							}
							else {
								
								if(onColDown) {
									//no left and up borders
									border = new BorderStroke(Color.TRANSPARENT, Color.DARKCYAN, Color.DARKCYAN, Color.TRANSPARENT, 
															BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, 
															BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, 
															CornerRadii.EMPTY, new BorderWidths(4),
					                                        Insets.EMPTY
															);
								}
								else
									//only right border
									border = new BorderStroke(Color.TRANSPARENT, Color.DARKCYAN, Color.TRANSPARENT, Color.TRANSPARENT, 
											BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, 
											BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, 
											CornerRadii.EMPTY, new BorderWidths(4),
											Insets.EMPTY
											);
							}
						}
					}
					else {
						
						if(onLineL) {
							
							
							if(onColUp) {
								
								
								if(onColDown) {
									//no right border
									border = new BorderStroke(Color.DARKCYAN, Color.TRANSPARENT, Color.DARKCYAN, Color.DARKCYAN, 
															BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, 
															BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, 
															CornerRadii.EMPTY, new BorderWidths(4),
					                                        Insets.EMPTY
															);
								}
								else
									//no right and down borderd
									border = new BorderStroke(Color.DARKCYAN, Color.TRANSPARENT, Color.TRANSPARENT, Color.DARKCYAN, 
											BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, 
											BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, 
											CornerRadii.EMPTY, new BorderWidths(4),
											Insets.EMPTY
											);
							}
							else {
								
								if(onColDown) {
									//no right and up borders
									border = new BorderStroke(Color.TRANSPARENT, Color.TRANSPARENT, Color.DARKCYAN, Color.DARKCYAN, 
															BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, 
															BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, 
															CornerRadii.EMPTY, new BorderWidths(4),
					                                        Insets.EMPTY
															);
								}
								else
									//only left borders
									border = new BorderStroke(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.DARKCYAN, 
											BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, 
											BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, 
											CornerRadii.EMPTY, new BorderWidths(4),
											Insets.EMPTY
											);
								}
							}
							else {
								
								if(onColUp) {
									
									
									if(onColDown) {
										//no right and left borders
										border = new BorderStroke(Color.DARKCYAN, Color.TRANSPARENT, Color.DARKCYAN, Color.TRANSPARENT, 
																BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, 
																BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, 
																CornerRadii.EMPTY, new BorderWidths(4),
						                                        Insets.EMPTY
																);
									}
									else
										//only up border
										border = new BorderStroke(Color.DARKCYAN, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, 
												BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, 
												BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, 
												CornerRadii.EMPTY, new BorderWidths(4),
												Insets.EMPTY
												);
								}
								else {
									
									if(onColDown) {
										//only down border
										border = new BorderStroke(Color.TRANSPARENT, Color.TRANSPARENT, Color.DARKCYAN, Color.TRANSPARENT, 
																BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, 
																BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, 
																CornerRadii.EMPTY, new BorderWidths(4),
						                                        Insets.EMPTY
																);
									}
									else
										//no borders
										border = new BorderStroke(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, 
												BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, 
												BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, 
												CornerRadii.EMPTY, new BorderWidths(4),
												Insets.EMPTY
												); 
							}
						} 
					}
				border2 = new Border(border);
				//adds element to the cellId -> cageId hashMap
				cells.put(cellId1, counter);
				//add the borders in array 
				cages.add(cellId1, border2);
				cages.remove(cellId1 + 1);
				}
			counter ++;
			}
		} 
	
	public HashMap<Integer, Integer> getFirstCells() {
	
		return firstCells;
	}
	
	public ArrayList<Border> getCages() {
		
		return cages;
	}
	
	public HashMap<Integer, Integer> getCells() {
	
		return cells;
	}
	
	public HashMap<Integer, String> getOperations() {
	
		return operations;
	}
	
}
 