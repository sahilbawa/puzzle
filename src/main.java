import java.util.*;


public class main {

	public static void main(String[] args) {
		Hashtable<String, String> table = new Hashtable<String, String>(5000);
		
		//create queue of linkedlist of type string
		Queue<LinkedList<String>> helperQueue = new LinkedList<LinkedList<String>>();
		
		int[][] map = new int[4][5];
		Block[] blockArray = new Block[12];
		int xIndex = 0;
		int yIndex = 0;
		String config = "AADEECBDDAAD";
		String[] moveID = new String[12];
		
		
		//the first linked list to be added to the queue
		LinkedList<String> alpha = new LinkedList<String>();
		
		//adds starting config to the alpha list and adds alpha list to queue
		alpha.add(config);
		helperQueue.add(alpha);
		table.put(config, config);
		
		
		//-----------ITERATION OF SEQUENCE-----------
		for(int i = 0; i < 12; i++) {
			//create block object for each character in sequence
			blockArray[i] = new Block(config.charAt(i), i+1);
			
			//skip through to empty part of map
			while(map[yIndex][xIndex] != 0) {
				xIndex++;
				if(xIndex == 5) {
					xIndex = 0;
					yIndex++;
				}
			}
			blockArray[i].xposition = xIndex;
			blockArray[i].yposition = yIndex;
			
			for(int x = 0; x < blockArray[i].width; x++) {
				for(int y = 0; y < blockArray[i].height; y++) {
					map[yIndex + y][xIndex + x] = blockArray[i].id;
				}
			}
			if(xIndex == 5) {
				xIndex = 0;
				yIndex++;
			}
			
		    
		}
		
		//-----------END of ITERATION OF SEQUENCE-----------
		
		
		
		printMap(map, blockArray);
		

		for(int j = 0; j < 12; j++) {
			moveID[j] = blockArray[j].getMovements(blockArray, map);
		}
		System.out.println("moveID Array: " + Arrays.toString(moveID));
		
		
		
		
		//Iterate through each element of moveID and store appropriate data in linkedlist & hashtable
		for(int i = 0; i < 12; i++) {
			char type;
			int xpos;
			int ypos;
			char direction;
			int tmpblockID;
			int height;
			int width;
			String newconfig;
			String configID;
			
			int[][] helperMap = new int[map.length][];
			for(int k = 0; k < map.length; k++)
			    helperMap[k] = map[k].clone();
			
			if(moveID[i] != null) {
				type = moveID[i].charAt(0);
				ypos = (int)moveID[i].charAt(1) - 48;
				xpos = (int)moveID[i].charAt(2) - 48;
				direction = moveID[i].charAt(3);
				tmpblockID = map[ypos][xpos];
				height = blockArray[tmpblockID-1].height;
				width = blockArray[tmpblockID-1].width;
				//System.out.println("" + ypos + xpos + tmpblockID + height + width + );
				if(direction == 'd') {
					//height used for C, to switch top layer of C-blocks with empty blocks 2 rows down
					map[ypos][xpos] = map[ypos+height][xpos]; //replace block 1 with empty 1
					map[ypos+height][xpos] = tmpblockID; //replace empty 1 with block 1
					if(width == 2) { //for A & C blocks
						map[ypos][xpos+1] = map[ypos+height][xpos+1]; //replace block 2 with empty 2
						map[ypos+height][xpos+1] = tmpblockID; //replace empty 2 with block 2
					}
				} else if(direction == 'u') {
					map[ypos+height-1][xpos] = map[ypos-1][xpos]; //replace bottommost left square with empty 1
					map[ypos-1][xpos] = tmpblockID; //replace empty 1 with block 1
					if(width == 2) { //for A & C blocks
						map[ypos+height-1][xpos+1] = map[ypos-1][xpos+1]; //replace bottommost right square with empty 2
						map[ypos-1][xpos+1] = tmpblockID; //replace empty 2 with block 2
					}
				}else if(direction == 'l') {
					map[ypos][xpos+width-1] = map[ypos][xpos-1]; //index square replaced with empty 1
					map[ypos][xpos-1] = tmpblockID;
					if(height == 2) {
						map[ypos+1][xpos+width-1] = map[ypos+1][xpos-1];
						map[ypos+1][xpos-1] = tmpblockID;
					}
				}else if(direction == 'r') {
					map[ypos][xpos] = map[ypos][xpos+width]; //index square replaced with empty 1
					map[ypos][xpos+width] = tmpblockID;
					if(height == 2) {
						map[ypos+1][xpos] = map[ypos+1][xpos+width];
						map[ypos+1][xpos+width] = tmpblockID;
					}
				}
				
				printMap(map,blockArray);
				newconfig = translateMap(map, blockArray);
				configID = newconfig + moveID[i];
				
				System.out.println("configID: " + configID + '\n');
				
				if(!table.contains(newconfig)) {
					table.put(newconfig, newconfig);
					LinkedList<String> tmplist = (LinkedList<String>) (helperQueue.peek().clone());
					tmplist.add(newconfig);
					helperQueue.add(tmplist);
				} else {
					System.out.println("CONFIG ALREADY EXISTS\n");
				}
				
				
				
				//reset map
				for(int k = 0; k < map.length; k++)
				    map[k] = helperMap[k].clone();
			}
		}
		//System.out.println("Root node: " + helperQueue.remove().peekLast());
		
		printQueue(helperQueue);
		
		helperQueue.remove();
		
		
		/*
		//--TO TEST OUT FUNCTION--//
		
		System.out.println("Moving block A down to test translateMap function...");
		int tmpblock1 = map[0][0];
		int tmpblock2 = map[0][1];
		map[0][0] = map[1][0];
		map[0][1] = map[1][1];
		map[1][0] = tmpblock1;
		map[1][1] = tmpblock2;
		System.out.println();
		printMap(map, blockArray);
		System.out.println();
		
		//--END TESTING--//
		*/
		
		System.out.println("final sequence: " + translateMap(map, blockArray));
	}
	
	
	
	public static void printQueue(Queue<LinkedList<String>> q) {
		LinkedList<String> tmplist;
		int size = q.size();
		for(int i = 0; i < size; i++) {
			tmplist = q.remove();
			for(int j = 0; j < tmplist.size(); j++) {
				System.out.print(tmplist.get(j) + " -> ");
			}
			System.out.println();
		}
	}
	
	
	public static void createMap(int[][] map, Block[] blockArray) {
		int xIndex = 0;
		int yIndex = 0;
		for(int i = 0; i < 12; i++) {
			blockArray[i].xposition = xIndex;
			blockArray[i].yposition = yIndex;
			
			for(int x = 0; x < blockArray[i].width; x++) {
				for(int y = 0; y < blockArray[i].height; y++) {
					map[yIndex + y][xIndex + x] = blockArray[i].id;
				}
			}
			if(xIndex == 5) {
				xIndex = 0;
				yIndex++;
			}
		}
	}

	public static void printMap(int[][] map, Block[] blockArray) {
		for(int w = 0; w<4; w++)
		{
		    for(int j = 0; j<5; j++)
		    {
		        System.out.print(blockArray[map[w][j]-1].type);
		    }
		    System.out.println();
		}
		//System.out.println();
	}
	
	
	public static String translateMap(int[][] map, Block[] blockArray) {
		int charIndex = 0;
		int tmpID;
		boolean containsID = false;
		char[] seqArray = new char[12];
		int[] idArray = new int[12];
		//System.out.println(map[3][3].id);
		for(int y = 0; y < 4; y++) {
			for(int x = 0; x < 5; x++) {
				tmpID = blockArray[map[y][x]-1].id;
				//check idArray for repeated ID
				for(int i = 0; i < 12; i++) {
					if(idArray[i] == tmpID)
						containsID = true;
				}
				if(!containsID) {
					idArray[charIndex] = tmpID;
					seqArray[charIndex] = blockArray[map[y][x]-1].type;
					charIndex++;
				}
				containsID = false;
			}
		}//END of FOR LOOP
		
		//System.out.println("idArray: " + Arrays.toString(idArray));
		//System.out.println("seqArray: " + Arrays.toString(seqArray));
		String output = new String(seqArray);
		return output;
	}

}
