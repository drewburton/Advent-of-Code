import java.util.*;
import java.io.*;

class Image
{
    private ArrayList<String> rows = new ArrayList<String>();
    private ArrayList<ArrayList<String>> layers = new ArrayList<ArrayList<String>>();
    private int width;
    private int height;

    Image(String filename, int w, int h)
    {
        width = w;
        height = h;
        try
        {
            Scanner scanner = new Scanner(new File(filename));
            
            String input = "";
            while (scanner.hasNext())
            {
                input = scanner.nextLine();
            }

            for (int i = width - 1; i < input.length(); i += width)
            {        
                rows.add(input.substring(i - (width - 1), i + 1));
            }

            for (int i = height - 1; i < rows.size(); i += height)
            {
                ArrayList<String> temp = new ArrayList<String>(rows.subList(i - (height - 1), i + 1));
                layers.add(temp);
            }

            DecodeImage();
            // int layerWithLeastZerosIndex = CountZeros();
            // PrintOnesAndTwos(layerWithLeastZerosIndex);
            scanner.close();
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("File not found!");
        }
    }

    private void DecodeImage()
    {        
        // array to keep track of which positions are found, initialized to false
        ArrayList<ArrayList<Boolean>> foundVisible = new ArrayList<ArrayList<Boolean>>();
        for (int r = 0; r < height; r++)
        {
            foundVisible.add(new ArrayList<Boolean>());
            for (int c = 0; c < width; c++)
            {
                foundVisible.get(r).add(false);
            }
        }

        // array for the top visible pixels, initialized to transparent
        ArrayList<ArrayList<Integer>> topVisible = new ArrayList<ArrayList<Integer>>();
        for (int r = 0; r < height; r++)
        {
            topVisible.add(new ArrayList<Integer>());
            for (int c = 0; c < width; c++)
            {
                topVisible.get(r).add(2);
            }
        }

        // scan through the 3D layers and find the top visible
        for (ArrayList<String> layer : layers)
        {
            for (int row = 0; row < layer.size(); row++)
            {
                for (int column = 0; column < layer.get(row).length(); column++)
                {
                    if (!foundVisible.get(row).get(column) && layer.get(row).charAt(column) != '2')
                    {
                        foundVisible.get(row).set(column, true);
                        topVisible.get(row).set(column, Integer.parseInt(Character.toString(layer.get(row).charAt(column))));
                    }
                }
            }
        }

        // print out the top visible pixels
        for (ArrayList<Integer> row : topVisible)
        {
            for (int pixel : row)
            {
                if (pixel == 1)
                {
                    System.out.print("#");
                }
                else
                {
                    System.out.print(" ");
                }
                // System.out.print(pixel);
            }
            System.out.println();
        }
    }

    private int CountZeros()
    {
        int indexOfLowest = 0;
        int leastZeros = Integer.MAX_VALUE;
        for (int i = 0; i < layers.size(); i++)
        {
            int zeros = 0;
            for (String row : layers.get(i))
            {
                for (int rowI = 0; rowI < row.length(); rowI++)
                {
                    if (row.charAt(rowI) == '0')
                        zeros++;
                }
            }
            if (zeros < leastZeros)
            {
                leastZeros = zeros;
                indexOfLowest = i;
            }
        }
        return indexOfLowest;
    }

    private void PrintOnesAndTwos(int indexOfLayer)
    {
        int ones = 0;
        int twos = 0;
        for (String layer : layers.get(indexOfLayer))
        {
            for (int i = 0; i < layer.length(); i++)
            {
                if (layer.charAt(i) == '1')
                {
                    ones++;
                }
                if (layer.charAt(i) == '2')
                {
                    twos++;
                }
            }
        }
        System.out.println(ones * twos);
    }
}