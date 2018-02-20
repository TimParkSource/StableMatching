// Timothy Park
// Project 1

import java.util.Scanner;
import java.io.File;

public class Project1
{
    //due to strange array reactions to 0 I'll be starting every array index of 1 for consistency 
    private static int[][] manList = new int[10][10]; //man preferences [man][woman rank] = woman
    private static int[][] womanList = new int[10][10]; //woman preferences [woman][man rank] = man
    private static int n; // number of men/women
    private static int m; // number of stable matchings
    private static int[][] manPropose = new int[10][10]; //man propose match [man][1] = woman
    private static int[][] womanPropose = new int[10][10]; //woman propose match [woman][1] = man

    public static void main(String[] args)
    {
        System.out.println("Project 1: Stable Match Check");
        setPreferenceLists("input1.txt");
        int p = 0;
        menPropose(p);
        p = 0;
        womenPropose(p);
        
    }
    public static void setPreferenceLists(String fileName) // sets man pref and women pref from file into our arrays
    {
        File file = new File(fileName);
        try {
            int i = 0;
            int j;

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
                    for(j = 1; j <= n; j++)
                    {
                        manList[i][j]= sc.nextInt();  // i-1 tomake men start at 0
                    }
                    i++;
                }
                else if( i > n && i <= 2*n ) //set womanList
                {
                    for(j = 1; j <= n; j++)
                    {
                        womanList[i-n][j]= sc.nextInt();  //i-1-n tomake women start at 0
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
        //1. each man proposes to his next top choice p
        int[][] womanSuitors = new int[10][10]; // suitors women gets
        
        for(int i = 0; i < n; i++) // i represents man
        {
            if(manPropose[i][1] == 0) //means man is not matched, therefore go down the list
            {
                int woman = manList[i][p];
            
                int counter = 0; // counter is amount of suitors for a woman
                for (int j = 1; j <= 10; i ++){
                    if (womanSuitors[woman][j] != 0)
                        counter ++;
                } // we can technically do wS[][0] = counter and inc dec it and get rid of the for loop
            
                womanSuitors[woman][counter+1] = i;  //womanSuitors[woman][suitor#] = man
            }
        }
        
        
        //2. each woman rejects all but her top suitor; rejected men become 0
        
        //if woman and man are 1st ranked then always stable
        //this phase is where manPropose is filled
    }
    
}