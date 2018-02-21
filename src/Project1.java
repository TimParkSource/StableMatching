// Timothy Park
// Project 1

import java.util.Scanner;
import java.io.File;
import java.net.*;

public class Project1
{
    //due to strange array reactions to 0 I'll be starting every array index of 1 for consistency 
    private static int[][] manList = new int[10][10]; //man preferences [man][man rank] = man
    private static int[][] womanList = new int[10][10]; //woman preferences [man][man rank] = man
    private static int n; // number of men/women
    private static int m = 0; // number of stable matchings
    private static int[][] manPropose = new int[10][10]; //man propose match [man][1] = woman
    private static int[][] womanPropose = new int[10][10]; //woman propose match [woman][1] = man
    private static int[][] womanProposeMan = new int[10][10]; //woman propose match [man][1] = woman ; used for comapirsons

    public static void main(String[] args)
    {
        setPreferenceLists();
        menPropose(1); // p=1
        womenPropose(1);
        int[][] stablePropose = new int[10][10]; //stable propose match [man][1] = woman
        match(1, stablePropose); // now let's match based on the man and woman propose lists
        System.out.println(m);
               
        //test(); //=-=-=-=-=-=-=-=TESTING=-=-=-=-=-=-=-=-=-=-==-=-=
    }
    
    public static void test()
    {
        System.out.println("manList");
        printArray(manList);
        System.out.println("womanList");
        printArray(womanList);
        System.out.println("manPropose");
        printArray(manPropose);
        System.out.println("womanPropose");
        printArray(womanPropose);
        System.out.println("womanProposeMan");
        printArray(womanProposeMan);
    }
    
    public static void printArray(int[][] a) // prints array
    {
        for(int i = 1; i <= n; i++)
        {
            for(int j = 1; j <= n; j++)
            {
                System.out.print(a[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
    
    //Works
    public static void setPreferenceLists() // sets man pref and women pref from file into our arrays
    {
        URL path = Project1.class.getResource("input1.txt");
        File file = new File(path.getFile());
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
                        manList[i][j]= sc.nextInt();  // i tomake men start at 1
                    }
                    i++;
                }
                else if( i > n && i <= 2*n ) //set womanList
                {
                    for(j = 1; j <= n; j++)
                    {
                        womanList[i-n][j]= sc.nextInt();  //i-1-n tomake women start at 1
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
    //Works
    public static void menPropose(int p) //get a match from men proposing to women
    {
        //1. each man proposes to his next top choice p
        int[][] womanSuitors = new int[10][10]; // rank of suitors women gets
        
        for(int i = 1; i <= n; i++) // i represents man
        {
            if(manPropose[i][1] == 0)
            {    
                int woman = manList[i][p]; //next top woman for man i

                int counter = womanSuitors[woman][0]; // use index 0 as number of suitors
                womanSuitors[woman][0] = counter + 1; // increase number of suitors for this woman

                for(int j = 1; j <= n; j++) //now find rank of this man for this woman; it's easier to sort this way
                {
                    if(womanList[woman][j] == i)
                    {
                        womanSuitors[woman][counter+1] = j;  //womanSuitors[man][suitor#] = man rank
                    }
                }
            }
            
        } // else skip the man
        
        //2. each woman rejects all but her top suitor; rejected men become 0
       
        for(int i = 1; i <= n; i++) //i represents woman this time
        {
            if(womanSuitors[i][0] > 1) // if a woman has more than 1 choice she picks the best
            {
                for(int j = 2; j <= n; j++)
                {
                    if(womanSuitors[i][1] > womanSuitors[i][j]) // take higher ranked "lower number" man
                    { 
                        womanSuitors[i][1] = womanSuitors[i][j];
                    } //else [i][1] stays the same
                    womanSuitors[i][j] = 0; // remove j from array
                    womanSuitors[i][0]--; //decerement the suitor counter
                }
                int manRank = womanSuitors[i][1];
                int man = womanList[i][manRank];
                manPropose[man][1] = i;
                
            }
            else if(womanSuitors[i][0] == 1) // woman must pick the man
            {
                int manRank = womanSuitors[i][1];
                int man = womanList[i][manRank];
                manPropose[man][1] = i;
            }
            //else nothing happens
        }
        
        //3. check if everyone is engaged
        for(int i = 1; i <= n; i++){
            if(manPropose[i][1] == 0) //if everyone is not engaged repeat
            { 
                menPropose(++p);
                return; //stop recursion from looping
            }
        }
    }
    //Works
    public static void womenPropose(int p) //mirrored version of malePropose method
    {
        //1. each man proposes to his next top choice p
        int[][] manSuitors = new int[10][10]; // rank of suitors man gets
        
        for(int i = 1; i <= n; i++) // i represents woman
        {
            if(womanPropose[i][1] == 0)
            {    
                int man = womanList[i][p]; //next top man for woman i

                int counter = manSuitors[man][0]; //number of suitors
                manSuitors[man][0] = counter + 1; //increase suitors

                for(int j = 1; j <= n; j++) //now find rank of this woman for this man; it's easier to sort this way
                {
                    if(manList[man][j] == i)
                    {
                        manSuitors[man][counter+1] = j;  //manSuitors[man][suitor#] = woman rank
                    }
                }
            }
            
        } // else skip the woman
        
        //2. each man rejects all but his top suitor; rejected woman become 0
       
        for(int i = 1; i <= n; i++) //i represents man
        {
            if(manSuitors[i][0] > 1) // if a man has more than 1 choice he picks the best
            {
                for(int j = 2; j <= n; j++)
                {
                    if(manSuitors[i][1] > manSuitors[i][j]) // take higher ranked "lower number" woman
                    { 
                        manSuitors[i][1] = manSuitors[i][j];
                    } //else [i][1] stays the same
                    manSuitors[i][j] = 0; // remove j from array
                    manSuitors[i][0]--; //decerement the suitor counter
                }
                int womanRank = manSuitors[i][1];
                int woman = manList[i][womanRank];
                womanPropose[woman][1] = i;
                womanProposeMan[i][1] = woman; 
                
            }
            else if(manSuitors[i][0] == 1) // if man has 1 choice man must pick the woman
            {
                int womanRank = manSuitors[i][1];
                int woman = manList[i][womanRank];
                womanPropose[woman][1] = i;
                womanProposeMan[i][1] = woman; 
            }
            //else nothing happens
        }
        
        //3. check if everyone is engaged
        for(int i = 1; i <= n; i++){
            if(womanPropose[i][1] == 0) //if everyone is not engaged repeat
            { 
                womenPropose(++p);
                return; //stop recursion from looping
            }
        }
    }
    
    public static void match(int p, int[][] stablePropose) // in this case p must start at 1
    {
        // 1. Doing bound calculations for man p
        int jth = 0, kth = 0;
        for(int j = 1; j <= n; j++) //find rank of worst possible woman
        {
            if(manList[p][j] == womanProposeMan[p][1])
            {
                kth = j; 
            }
        }
        for(int j = 1; j <= n; j++) //find rank of best possible woman
        {
            if(manList[p][j] == manPropose[p][1])
            {
                jth = j; 
            }
        }        
        
        // 2. Find stable matches for man p
        for(int i = jth; i <= kth; i++) // p represents man and i represents rank
        {                
            boolean avail = true;
            for(int j = 1; j < p; j++) // Make sure woman is available
            {
                if(stablePropose[j][1] == manList[p][i])
                {
                    avail = false;
                }
            }
            
            if(avail)
            {             
                if(i<n) //if not final man recurse till final man
                {
                    stablePropose[p][1] = manList[p][i];
                    match(++p, stablePropose);
                }
                else if(i ==n) // if final man then increment stable marriage count
                {
                    m++;
                }
                
            }
        }
            
    }
}