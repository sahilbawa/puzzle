
public class Block {
	public char type;
	public int id;
	public int width = 0;
	public int height = 0;
	public int xposition = 0;
	public int yposition = 0;
	public boolean moveUp = false;
	public boolean moveLeft = false;
	public boolean moveRight = false;
	public boolean moveDown = false;
	
	public Block() {}
	
	public Block(char type0, int id0) {
		id = id0;
		type = type0;
		if(type == 'A') {
			width = 2;
			height = 1;
		}
		if(type == 'B') {
			width = 1;
			height = 2;
		}
		if(type == 'C') {
			width = 2;
			height = 2;
		}
		if(type == 'D') {
			width = 1;
			height = 1;
		}
		if(type == 'E') {
			width = 1;
			height = 1;
		}
	}
	
	public void getMovements(Block[] blockArray, int[][] map) {
		int xIndex = this.xposition;
		int yIndex = this.yposition;
		
		//---CHECK DOWN---
		if(yIndex != 3) {
			if((type == 'A' && blockArray[map[yIndex+1][xIndex]-1].type == 'E' && blockArray[map[yIndex+1][xIndex+1]-1].type == 'E') ||
					(type == 'B' && blockArray[map[yIndex+2][xIndex]-1].type == 'E') ||
					(type == 'C' && blockArray[map[yIndex+2][xIndex]-1].type == 'E' && blockArray[map[yIndex+2][xIndex+1]-1].type == 'E') ||
					(type == 'D' && blockArray[map[yIndex+1][xIndex]-1].type == 'E'))
				this.moveDown = true;
		}
		
		//---CHECK UP---
		if(yIndex != 0) {
			if((type == 'A' && blockArray[map[yIndex-1][xIndex]-1].type == 'E' && blockArray[map[yIndex-1][xIndex+1]-1].type == 'E') ||
					(type == 'B' && blockArray[map[yIndex-1][xIndex]-1].type == 'E') ||
					(type == 'C' && blockArray[map[yIndex-1][xIndex]-1].type == 'E' && blockArray[map[yIndex-1][xIndex+1]-1].type == 'E') ||
					(type == 'D' && blockArray[map[yIndex-1][xIndex]-1].type == 'E'))
				this.moveUp = true;
		}
		
		//---CHECK RIGHT---
		if(xIndex != 4) {
			if((type == 'A' && blockArray[map[yIndex][xIndex+2]-1].type == 'E') ||
					(type == 'B' && blockArray[map[yIndex][xIndex+1]-1].type == 'E' && blockArray[map[yIndex+1][xIndex+1]-1].type == 'E') ||
					(type == 'C' && blockArray[map[yIndex][xIndex+2]-1].type == 'E' && blockArray[map[yIndex+1][xIndex+2]-1].type == 'E') ||
					(type == 'D' && blockArray[map[yIndex][xIndex+1]-1].type == 'E'))
				this.moveRight = true;
		}
		
		//---CHECK LEFT---
		if(xIndex != 0) {
			if((type == 'A' && blockArray[map[yIndex][xIndex-1]-1].type == 'E') ||
					(type == 'B' && blockArray[map[yIndex][xIndex-1]-1].type == 'E' && blockArray[map[yIndex+1][xIndex-1]-1].type == 'E') ||
					(type == 'C' && blockArray[map[yIndex][xIndex-1]-1].type == 'E' && blockArray[map[yIndex+1][xIndex-1]-1].type == 'E') ||
					(type == 'D' && blockArray[map[yIndex][xIndex-1]-1].type == 'E'))
				this.moveLeft = true;
		}

		if(this.moveDown)
			System.out.println("Block " + this.type + " at (" + xIndex + ", " + yIndex + ") can move down.");

		if(this.moveUp)
			System.out.println("Block " + this.type + " at (" + xIndex + ", " + yIndex + ") can move up.");

		if(this.moveLeft)
			System.out.println("Block " + this.type + " at (" + xIndex + ", " + yIndex + ") can move left.");

		if(this.moveRight)
			System.out.println("Block " + this.type + " at (" + xIndex + ", " + yIndex + ") can move right.");
		
	}
	
}
