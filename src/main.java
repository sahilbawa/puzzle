import java.util.*;


public class main {
	public static void main(String[] args) {
		Debugger debugger = new Debugger();
		Debugger.enabled = false;
		
		/*
		 * AADCBDEDEAAD : 116 winning moves
		 * EACADEABDADD : 9 winning moves
		 * DAABEDAACDED : 7 winning moves
		 * DDEADDCDCDEA : 5 winning moves
		 * DAADEAEBACDD : 2 winning moves
		 * 
		 */
		String config = "AADCBDEDEAAD";//"DAADEAEBACDD";//"AADCBDEDEAAD";//"EACADEABDADD";//"DAABEDAACDED";//"DDEADDCDCDEA";
		LinkedList<String> winningList = null;
		
		HashMap<String, String> table = new HashMap<String, String>(5000);
		
		//create queue of linkedlist of type string
		Queue<LinkedList<String>> helperQueue = new LinkedList<LinkedList<String>>();

		//the first linked list to be added to the queue
		LinkedList<String> alpha = new LinkedList<String>();
		
		//adds starting config to the alpha list and adds alpha list to queue
		alpha.add(config);
		helperQueue.add(alpha);
		table.put(config, config);
		
		//for(int LOOPER = 0; LOOPER < 4; LOOPER++) {
		mainLoop:
		while(true) {
		
		int[][] map = new int[4][5];
		Block[] blockArray = new Block[12];
		int xIndex = 0;
		int yIndex = 0;
		String[] moveID = new String[12];
		
		
		config = helperQueue.peek().peekLast();
		
		
		
		//-----------ITERATION OF SEQUENCE INTO BLOCKARRAY-----------
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
			

		if(Debugger.enabled) {
			System.out.println("Starting configuration:");
			printIntMap(map, blockArray);
		}
		

		for(int j = 0; j < 12; j++) {
			moveID[j] = blockArray[j].getMovements(blockArray, map);
		}
		Debugger.log("\nArray of Moves: " + Arrays.toString(moveID));
		
		
		
		
		//Iterate through each element of moveID and store appropriate data in linkedlist & hashtable
		for(int i = 0; i < 12; i++) {
			if(moveID[i] != null) {
				int listOffset = 0;
				int xpos;
				int ypos;
				boolean moveDown = false;
				boolean moveUp = false;
				boolean moveLeft = false;
				boolean moveRight = false;
				int tmpblockID;
				int height;
				int width;
				String newconfig;
				String configID;
				
				int[][] helperMap = new int[map.length][];
				for(int k = 0; k < map.length; k++)
				    helperMap[k] = map[k].clone();
				
			
				ypos = (int)moveID[i].charAt(1) - 48;
				xpos = (int)moveID[i].charAt(2) - 48;
				
				if(moveID[i].charAt(3) != 0)
					moveDown = true;
				if(moveID[i].charAt(4) != 0)
					moveUp = true;
				if(moveID[i].charAt(5) != 0)
					moveLeft = true;
				if(moveID[i].charAt(6) != 0)
					moveRight = true;
				
				tmpblockID = map[ypos][xpos];
				height = blockArray[tmpblockID-1].height;
				width = blockArray[tmpblockID-1].width;
				//Debugger.log("" + ypos + xpos + tmpblockID + height + width + );
				if(moveDown) {
					//height used for C, to switch top layer of C-blocks with empty blocks 2 rows down
					map[ypos][xpos] = map[ypos+height][xpos]; //replace block 1 with empty 1
					map[ypos+height][xpos] = tmpblockID; //replace empty 1 with block 1
					if(width == 2) { //for A & C blocks
						map[ypos][xpos+1] = map[ypos+height][xpos+1]; //replace block 2 with empty 2
						map[ypos+height][xpos+1] = tmpblockID; //replace empty 2 with block 2
					}
					if(Debugger.enabled)
						printIntMap(map,blockArray);
					newconfig = translateMap(map, blockArray);
					configID = newconfig + moveID[i].charAt(0) + moveID[i].charAt(1) + moveID[i].charAt(2) + moveID[i].charAt(3);
					
					Debugger.log("configID: " + configID);
					
					if(!table.containsKey(newconfig)) {
						table.put(newconfig, newconfig);
						LinkedList<String> tmplist = (LinkedList<String>) (helperQueue.peek().clone());
						tmplist.add(configID);
						helperQueue.add(tmplist);
					} else {
						Debugger.log("CONFIG ALREADY EXISTS\n");
					}

					if(checkWin(blockArray, map))
						break mainLoop;
					//reset map
					for(int k = 0; k < map.length; k++)
					    map[k] = helperMap[k].clone();
					
					
					
					
					
					
				} if(moveUp) {
					map[ypos+height-1][xpos] = map[ypos-1][xpos]; //replace bottommost left square with empty 1
					map[ypos-1][xpos] = tmpblockID; //replace empty 1 with block 1
					if(width == 2) { //for A & C blocks
						map[ypos+height-1][xpos+1] = map[ypos-1][xpos+1]; //replace bottommost right square with empty 2
						map[ypos-1][xpos+1] = tmpblockID; //replace empty 2 with block 2
					}
					if(Debugger.enabled)
						printIntMap(map,blockArray);
					newconfig = translateMap(map, blockArray);
					configID = newconfig + moveID[i].charAt(0) + moveID[i].charAt(1) + moveID[i].charAt(2) + moveID[i].charAt(4);
					
					Debugger.log("configID: " + configID);
					
					if(!table.containsKey(newconfig)) {
						table.put(newconfig, newconfig);
						LinkedList<String> tmplist = (LinkedList<String>) (helperQueue.peek().clone());
						tmplist.add(configID);
						helperQueue.add(tmplist);
					} else {
						Debugger.log("CONFIG ALREADY EXISTS\n");
					}
					
					if(checkWin(blockArray, map))
						break mainLoop;
					//reset map
					for(int k = 0; k < map.length; k++)
					    map[k] = helperMap[k].clone();
					
					
				} if(moveLeft) {
					map[ypos][xpos+width-1] = map[ypos][xpos-1]; //index square replaced with empty 1
					map[ypos][xpos-1] = tmpblockID;
					if(height == 2) {
						map[ypos+1][xpos+width-1] = map[ypos+1][xpos-1];
						map[ypos+1][xpos-1] = tmpblockID;
					}
					if(Debugger.enabled)
						printIntMap(map,blockArray);
					newconfig = translateMap(map, blockArray);
					configID = newconfig + moveID[i].charAt(0) + moveID[i].charAt(1) + moveID[i].charAt(2) + moveID[i].charAt(5);
					
					Debugger.log("configID: " + configID);
					
					if(!table.containsKey(newconfig)) {
						table.put(newconfig, newconfig);
						LinkedList<String> tmplist = (LinkedList<String>) (helperQueue.peek().clone());
						tmplist.add(configID);
						helperQueue.add(tmplist);
					} else {
						Debugger.log("CONFIG ALREADY EXISTS\n");
					}
					if(checkWin(blockArray, map))
						break mainLoop;
					//reset map
					for(int k = 0; k < map.length; k++)
					    map[k] = helperMap[k].clone();
					
					
				} if(moveRight) {
					map[ypos][xpos] = map[ypos][xpos+width]; //index square replaced with empty 1
					map[ypos][xpos+width] = tmpblockID;
					if(height == 2) {
						map[ypos+1][xpos] = map[ypos+1][xpos+width];
						map[ypos+1][xpos+width] = tmpblockID;
					}
					if(Debugger.enabled)
						printIntMap(map,blockArray);
					newconfig = translateMap(map, blockArray);
					configID = newconfig + moveID[i].charAt(0) + moveID[i].charAt(1) + moveID[i].charAt(2) + moveID[i].charAt(6);
					
					Debugger.log("configID: " + configID);
					
					if(!table.containsKey(newconfig)) {
						table.put(newconfig, newconfig);
						LinkedList<String> tmplist = (LinkedList<String>) (helperQueue.peek().clone());
						tmplist.add(configID);
						helperQueue.add(tmplist);
					} else {
						Debugger.log("CONFIG ALREADY EXISTS\n");
					}

					if(checkWin(blockArray, map))
						break mainLoop;
					//reset map
					for(int k = 0; k < map.length; k++)
					    map[k] = helperMap[k].clone();
					
					
				}
				
				
				//reset map
				for(int k = 0; k < map.length; k++)
				    map[k] = helperMap[k].clone();
			}
		}
		//Debugger.log("Root node: " + helperQueue.remove().peekLast());

		if(Debugger.enabled)
			printQueue(helperQueue);
		Debugger.log("\n----------------------\n\n\n");
		helperQueue.remove();
		}
		
		//-------END OF GIANT WHILE LOOP-----------//
		if(Debugger.enabled)
			printQueue(helperQueue);
		Debugger.log("\n----------------------\n\n\n");
		while(helperQueue.peek() != null)
			winningList = helperQueue.remove();
		
		printList(winningList);
		
	}
	
	
	
	
	
	public static boolean checkWin(Block[] blockArray, int[][] map) {
		if(blockArray[map[1][3]-1].type == 'C' && blockArray[map[1][4]-1].type == 'C' && blockArray[map[2][3]-1].type == 'C' && blockArray[map[2][4]-1].type == 'C') {
			return true;
		} else
			return false;
	}
	
	
	public static void printList(LinkedList<String> list) {
		char[][] map = new char[4][5];
		char[] configID = new char[16];
		String move = "";
		
		String tmpConfig = list.pollFirst();
		createMap(map, tmpConfig);
		System.out.println("Initial configuration:");
		printCharMap(map);
		System.out.println("\nSolved in " + list.size() + " moves.\n");
		while((tmpConfig = list.pollFirst()) != null) {
			configID = tmpConfig.toCharArray();
			if(configID[15] == 'd')
				move = "down";
			if(configID[15] == 'u')
				move = "up";
			if(configID[15] == 'l')
				move = "left";
			if(configID[15] == 'r')
				move = "right";
			System.out.println("\nMove " + configID[12] + " block at position (" + configID[14] + ", " + configID[13] + ") " + move + ".\n");
			createMap(map, tmpConfig);
			printCharMap(map);
			
			
		}
	}
	
	public static void createMap(char[][] map, String c) {
		int xIndex = 0;
		int yIndex = 0;
		char type;
		int width = 0;
		int height = 0;
		char[] config = c.toCharArray();
		
		for(int y = 0; y < 4; y++) {
			for(int x = 0; x < 5; x++) {
				map[y][x] = 0;
			}
		}
		
		for(int i = 0; i < 12; i++) {
			type = config[i];
			if(type == 'A') {
				width = 2;
				height = 1;
			}
			else if(type == 'B') {
				width = 1;
				height = 2;
			}
			else if(type == 'C') {
				width = 2;
				height = 2;
			}
			else if(type == 'D') {
				width = 1;
				height = 1;
			}
			else if(type == 'E') {
				width = 1;
				height = 1;
			}
			
			while(map[yIndex][xIndex] != 0) {
				xIndex++;
				if(xIndex == 5) {
					xIndex = 0;
					yIndex++;
				}
			}
			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					map[yIndex + y][xIndex + x] = type;
				}
			}
			xIndex = xIndex + width;
			if(xIndex == 5) {
				xIndex = 0;
				yIndex++;
			}
		}
	}
	
	public static void printQueue(Queue<LinkedList<String>> queue) {
		Queue<LinkedList<String>> q = new LinkedList<LinkedList<String>>(queue);
		LinkedList<String> tmplist;
		int size = q.size();
		System.out.println("Queue: ");
		for(int i = 0; i < size; i++) {
			tmplist = q.remove();
			for(int j = 0; j < tmplist.size(); j++) {
				System.out.print(tmplist.get(j) + " -> ");
			}
			System.out.println();
		}
	}

	public static void printCharMap(char[][] map) {
		for(int w = 0; w<4; w++)
		{
		    for(int j = 0; j<5; j++)
		    {
		        System.out.print(map[w][j]);
		    }
		    System.out.println();
		}
	}
	

	public static void printIntMap(int[][] map, Block[] blockArray) {
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
