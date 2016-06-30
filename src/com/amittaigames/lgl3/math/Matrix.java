package com.amittaigames.lgl3.math;

public class Matrix {

	public static final Matrix IDENTITY_3 = new Matrix(3, 3, new float[] {
		1, 0, 0,
		0, 1, 0,
		0, 0, 1
	});
	
	public static final Matrix IDENTITY_4 = new Matrix(4, 4, new float[] {
		1, 0, 0, 0,
		0, 1, 0, 0,
		0, 0, 1, 0,
		0, 0, 0, 1
	});
	
	public static final Matrix IDENTITY_5 = new Matrix(5, 5, new float[] {
		1, 0, 0, 0, 0,
		0, 1, 0, 0, 0,
		0, 0, 1, 0, 0,
		0, 0, 0, 1, 0,
		0, 0, 0, 0, 1
	});
	
	private int width;
	private int height;
	private float[][] data;
	
	private boolean square;
	
	public Matrix(int width, int height) {
		this.width = width;
		this.height = height;
		data = new float[height][width];
		
		if (width == height) {
			square = true;
		}
	}
	
	public Matrix(int width, int height, float[] data) {
		this.width = width;
		this.height = height;
		this.data = new float[height][width];
		
		int i = 0;
		int x = 0;
		int y = 0;
		for (int n = 0; n < width * height; n++) {
			this.set(x, y, data[i]);
			
			if (x == width - 1) {
				x = 0;
				y++;
			} else {
				x++;
			}
			
			i++;
		}
		
		if (width == height) {
			square = true;
		}
	}
	
	public static Matrix multiply(Matrix a, Matrix b) {
		if (a.height != b.width) {
			return null;
		}
		
		Matrix c = new Matrix(b.width, a.height);
		
		for (int y = 0; y < c.height; y++) {
			for (int x = 0; x < c.width; x++) {
				float[] row = a.getRow(y);
				float[] col = b.getColumn(x);
				
				float sum = 0;
				for (int z = 0; z < row.length; z++) {
					sum += row[z] * col[z];
				}
				
				c.set(x, y, sum);
			}
		}
		
		return c;
	}
	
	public float get(int x, int y) {
		return data[y][x];
	}
	
	public void set(int x, int y, float data) {
		this.data[y][x] = data;
	}
	
	public float[] getRow(int y) {
		float[] row = new float[this.width];
		for (int i = 0; i < this.width; i++) {
			row[i] = this.data[y][i]; 
		}
		return row;
	}
	
	public float[] getColumn(int x) {
		float[] col = new float[this.height];
		for (int i = 0; i < this.height; i++) {
			col[i] = this.data[i][x];
		}
		return col;
	}
	
	public void clear() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				data[y][x] = 0;
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < height; y++) {
			sb.append("[ ");
			for (int x = 0; x < width; x++) {
				sb.append(data[y][x]).append(" ");
			}
			sb.append("]");
			if (y != height - 1) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float[][] getData() {
		return data;
	}

	public boolean isSquare() {
		return square;
	}
	
}