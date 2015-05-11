import java.util.Arrays;


public class main {

	public static void main(String[] args) {
		int[][] map = new int[4][5];
		Block[] blockArray = new Block[12];
		int xIndex = 0;
		int yIndex = 0;
		String config = "AADEECBDDAAD";
		
		
		
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
			blockArray[j].getMovements(blockArray, map);
		}
		System.out.println();
		
		
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
		
		
		System.out.println("final sequence: " + translateMap(map, blockArray));
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
		System.out.println();
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
