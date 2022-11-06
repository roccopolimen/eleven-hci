import java.io.*;
import java.util.*;

public class ScoreList
{
    private String fileName;

    public ScoreList(String fileName)
    {
        this.fileName = fileName;
    }

    public String [] topFive()
    {
        String [] ans = new String [5];
        String line = null;
        try 
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int n = 0;
            while((line = bufferedReader.readLine()) != null && n < 5) 
            {
                ans[n] = line;
                n++;
            }   
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                  
        }
        return ans;
    }

    public String highScore()
    {
        String ans = "";
        String line = null;
        try 
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int n = 0;
            while((line = bufferedReader.readLine()) != null && n < 1) 
            {
                ans = line;
                n++;
            }   
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                  
        }
        return ans;
    }

    public void addScore(double score)
    {
        double [] scores = pullFromFile();
        scores = addNum(scores,score);
        bubbleSort(scores);
        writeToFile(convertToString(scores));
    }

    public void writeToFile(String [] lines)
    {
        try {
            // Assume default encoding.
            FileWriter fileWriter = new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(int i = 0; i < lines.length-1; i++)
            {
                bufferedWriter.write(lines[i]);
                bufferedWriter.newLine();
            }
            bufferedWriter.write(lines[lines.length-1]);

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + fileName + "'");
        }
    }

    public double [] pullFromFile()
    {
        ArrayList<Double> nums = new ArrayList<Double>();
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                nums.add(Double.parseDouble(line));
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
        }
        double [] numbs = new double [nums.size()];
        for(int i = 0; i < numbs.length; i++)
            numbs[i] = nums.get(i);

        return numbs;
    }

    public void bubbleSort(double [] arr)
    //Bubble Sort
    {
        boolean swap = true;
        double place = 0;
        while(swap == true)
        {
            swap = false;
            for(int i = 0; i < arr.length-1; i++)
            {
                if(arr[i] > arr[i+1])
                {
                    place = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = place;
                    swap = true;
                }
            }
        }
    }

    public String [] convertToString(double [] nums)
    {
        String [] ans = new String [nums.length];

        for(int i = 0; i< nums.length; i++)
            ans[i] = nums[i] + "";

        return ans;
    }

    public double [] addNum(double [] arr, double num)
    {
        double [] ans = new double [arr.length+1];
        for(int i = 0; i < arr.length; i++)
            ans[i] = arr[i];
        ans[ans.length-1] = num;
        return ans;
    }
}