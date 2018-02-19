// Timothy Park
// Project 1

import java.util.Scanner;
import java.io.File;

public class Project1
{
    private static int[][] manList = new int[10][10]; //man preferences list
    private static int[][] womanList = new int[10][10]; //woman preferences list
    private static int n; // number of men or women
    private static int m; // number of stable matchings
    private static int[][] manPropose = new int[10][10]; //man propose match
    private static int[][] womanPropose = new int[10][10]; //woman propose match

    public static void main(String[] args)
    {
        System.out.println("Project 1: Stable Match Check");
        setPreferenceLists("input1.txt");
        int p = 0;
        menPropose(p);
        
    }
    public static void setPreferenceLists(String fileName) // sets man pref and women pref from file into our arrays
    {
        File file = new File(fileName);
        try {
            int i = 0;
            int j = 0;

            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                // gets n
                if(i == 0) // find n
                {
                    n = sc.nextInt();
                    i++;
                }
                else if(i <= n && i > 0) //set manList
                {
                    for(j = 0; j < n; j++)
                    {
                        manList[i-1][j]= sc.nextInt();  // i-1 tomake men start at 0
                    }
                    i++;
                }
                else if( i > n && i <= 2*n ) //set womanList
                {
                    for(j = 0; j < n; j++)
                    {
                        womanList[i-1-n][j]= sc.nextInt();  //i-1-n tomake women start at 0
                    }
                    i++;
                }
            }
            sc.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void menPropose(int p) //get a match from men proposing to women
    {
        //each man proposes to his top choice
        int[][] womanSuitors = new int[10][10]; // suitors women gets
        int z; // figure outt how to keep track of z
        for(int i = 0; i < n; i++) // i represents man
        {
            womanSuitors[manList[i][p]][z] = i;  //womanSuitors[woman][suitor#] = man
            
        }
        int counter = 0;
        for (int i = 0; i < womanSuitors.length; i ++){
            if (womanSuitors[i] != null)
            counter ++;
        }
        //each woman rejects all but her top suitor
    }
}