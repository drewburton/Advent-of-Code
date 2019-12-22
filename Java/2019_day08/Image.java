import java.util.*;
import java.io.*;

class Image
{
    ArrayList<String> rows = new ArrayList<String>();
    ArrayList<ArrayList<String>> layers = new ArrayList<ArrayList<String>>();

    Image(String filename, int width, int height)
    {
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

            int layerWithLeastZerosIndex = CountZeros();
            PrintOnesAndTwos(layerWithLeastZerosIndex);
            scanner.close();
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("File not found!");
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