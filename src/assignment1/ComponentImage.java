package assignment1;

import java.awt.Color;
import edu.princeton.cs.introcs.Picture;
import edu.princeton.cs.introcs.StdIn;

public class ComponentImage 
{
	double thresholdPixelValue = 128.0;
	Picture pic;
	int width;
	int height;
	int pixels;
	int choice;
	int[] grid;
	int[] index;
	int[] size;
	int count;
	int endOfRow;
	int bottomRow;
	String picChoice;

	public ComponentImage(String pic1) 
	{
		super();
		this.pic = new Picture(pic1);
		this.width = pic.width();
		this.height = pic.height();
		this.pixels = height*width;
		this.count = pixels;
		this.endOfRow = width-1;
		this.bottomRow = pixels-width;
		this.picChoice = ("square.bmp");
		grid = new int[pixels];
		size = new int[pixels];
		index = new int[pixels];
		for (int i = 0; i < pixels; i++)
		{
			grid[i] = i;
			index[i] = i;
		}
		for (int i = 0; i < pixels; i++)
		{
			size[i] = 1;
		}
	}

	public static void main(String[] args)
	{
		ComponentImage app = new ComponentImage("../Assignment01/images/square.bmp");
		app.run();
	}

	public int mainMenu()
	{
		//Main Menu - to choose the action on the image
		System.out.println("     ~~~ MAIN MENU ~~~");
		System.out.println("1. Change the image to greyscale.");
		System.out.println("2. Binarize the image.");
		System.out.println("3. Count the amount of components in the image");
		System.out.println("4. Change the picture.");
		System.out.println("0. Exit");
		System.out.println("Please choose an action (Index 1-4) you want to execute: ");
		System.out.print("=> ");
		int choice = StdIn.readInt();
		System.out.println(" ");
		return choice;
	}

	private void run()
	{
		int choice = mainMenu();
		while (choice != 0)
		{
			switch (choice)
			{
			case 1 : 
				greyscale();
				break;
			case 2: 
				binaryComponentImage();
				break;
			case 3:
				countComponents();
				break;
			case 4:
				changePicture();
				break;
			default:
				break;   
			}
			pic = new Picture("../Assignment01/images/" + picChoice);
			choice = mainMenu();
			System.out.println(" ");
		}
	}

	private void changePicture() 
	{
		System.out.println("Please choose from the following pictures: ");
		System.out.println("1. square.bmp, 2. stones.jpg");
		System.out.println("3.white_box.bmp");
		System.out.print("Please choose between index 1-3: ");
		int choice = StdIn.readInt();
		System.out.println("");
		if(choice == 1)
		{
			picChoice = ("square.bmp");
		}
		if(choice == 2)
		{
			picChoice = ("stones.jpg");
		}
		if(choice == 3)
		{
			picChoice = ("white_box.bmp");
		}
	}

	private void greyscale() 
	{
		for (int x = 0; x < width; x++) 
		{
			for (int y = 0; y < height; y++) 
			{
				Color color = pic.get(x, y);
				Color gray = Luminance.toGray(color);
				pic.set(x, y, gray);
			}
		}
		pic.show();
	}

	private void binaryComponentImage() 
	{
		for (int x = 0; x < width; x++) 
		{
			for (int y = 0; y < height; y++) 
			{
				Color color = pic.get(x, y);
				if (Luminance.lum(color) < thresholdPixelValue) 
				{
					pic.set(x, y, Color.BLACK);
				}
				else 
				{
					pic.set(x, y, Color.WHITE);
				}

			}
		}
		pic.show();		
	}

	private void colourComponentImage() 
	{
		
	}

	private void countComponents() 
	{
		int position = 0;
		int checking = 0;
		
		for (int x = 0; x < width; x++) 
		{
			for (int y = 0; y < height; y++) 
			{
				Color color = pic.get(x, y);
				if (Luminance.lum(color) < thresholdPixelValue) 
				{
					pic.set(x, y, Color.BLACK);
					grid[position] = 0;
				}
				else 
				{
					pic.set(x, y, Color.WHITE);
					grid[position] = 1;
				}
				position++;
			}
		}
		pic.show();
		
		for(int i = 0; i < pixels-1; i++)
		{
			position = i;
			if(position == endOfRow)
			{
				//Only check below
				checking = i+width;
				if (connected(position, checking))
				{
					
				}
				else
				{
					if(grid[position] == grid[checking])
					{
						union(position, checking);
					}
				}
				endOfRow = endOfRow + width;
			}
			if(position >= bottomRow)
			{
				//Only check right
				checking = i+1;
				if (connected(position, checking))
				{
					
				}				
				else
				{
					if(grid[position] == grid[checking])
					{
						union(position, checking);
					}
				}
			}
			else
			{
				//Check below and right
				checking = i+width;
				if (connected(position, checking))
				{
					
				}
				else
				{
					if(grid[position] == grid[checking])
					{
						union(position, checking);
					}
				}
				checking = i+1;
				if (connected(position, checking))
				{
					
				}				
				else
				{
					if(grid[position] == grid[checking])
					{
						union(position, checking);
					}
				}
			}
		}
		System.out.println(count-1 + " components in this image.");
		System.out.println("");
	}

	private void highlightComponentImage() 
	{

	}
	
	public int count() 
    {
        return count;
    }
	
	public int find(int p) 
    {
        return grid[p];
    }
	
	private int root(int i)
	{
		while(i != index[i])
		{
			index[i] = index[index[i]];
			i = index[i];
		}
		return i;
	}
	
	public boolean connected(int position, int checking)
	{
		if(position < pixels && checking < pixels)
		{
			return root(position) == root(checking);
		}
		return false;
	}
	
	public void union(int p, int q)
	{
		int i = root(p);
		int j = root(q);
		if(size[i] < size[j])
		{
			index[i] = j;
			size[j] += size[i];
		}
		else
		{
			index[j] = i;
			size[i] += size[j];
		}
		count--;
	}
}
