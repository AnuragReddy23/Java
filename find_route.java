/*
Name: Anurag Reddy Pingili
UTA ID: 1001863623
Class: 2222-CSE-5360-001
*/


import java.io.*;
import java.util.*;

public class find_route {
	
    public static int Nodes_expanded;
	public static int Nodes_generated;
    private static int cost;
	public static String goalcity;
    public static FileReader hueristic;
	public static FileReader inputFile;
    public static BufferedReader B1;
    public static BufferedReader B2;
    public static final String EndofInput = "END OF INPUT";
    private static boolean h = true;
    public static File hFile;
    public static File inputPathFile;
    public static ArrayList<String> nodesvisited = new ArrayList<String>();
    public static ArrayList<city_details> fringe = new ArrayList<city_details>();
    public static ArrayList<city_details> data_input = new ArrayList<city_details>();
    public static HashMap<String, Integer> hData = new HashMap<String, Integer>();
    public static ArrayList<city_details> data_visit = new ArrayList<city_details>();

    public static void main(String[] args) {
        try {
            String StartCity = null;
           	String inputFileName = null;
            String hFileName = null;
            
             /* Get arguments from command line.
              If number of parameters passed is less or more than required, exit.*/
             
            switch(args.length) 
            {
                case 3:
                inputFileName = args[0];
                StartCity = args[1];
                goalcity = args[2];
                break;

                case 4:
                inputFileName = args[0];
                StartCity = args[1];
                goalcity = args[2];
                hFileName = args[3]; 
                h = false;
                break;

                default:
            	System.out.println("Required number of parameters: 3 or 4");
                System.out.println("1- Input file");
                System.out.println("2- Start");
                System.out.println("3- Goal");
                System.out.println("4- Heuristic file");
            	System.exit(0);
            }
         

            inputPathFile = new File(inputFileName);
            inputFile = new FileReader(inputPathFile);
            B1 = new BufferedReader(inputFile);
            details(B1);
            fringe.add(new city_details(null, StartCity, 0));
            cost = 0;
            
             /* If there is a heuristic file,
             read and store the information
             */

            if (!h) 
            {
                hFile = new File(hFileName);
                hueristic = new FileReader(hFile);
                B2 = new BufferedReader(hueristic);
                heuristic_details(B2);
            }

            if (StartCity.equals(goalcity)) 
            {
                System.out.println("distance : 0 km \nroute \n" + StartCity + " to " + goalcity + ", 0 km");
            } 
            else 
            {
                findNeighbors(fringe.get(0));
            }
        } 
        catch (Exception exception) 
        {
            System.out.println("exception - " + exception.getMessage());
        }
    }

    /**
     * save the details into an array
     */ 

     public static void details(BufferedReader placereader) throws Exception 
     {
 
    	String inputText;
        while (!(inputText = placereader.readLine()).contains(EndofInput)) 
        {
            String[] place = inputText.split(" ");
            data_input.add(new city_details(place[0], place[1], Integer.parseInt(place[2])));
            data_input.add(new city_details(place[1], place[0], Integer.parseInt(place[2])));
        }
    }
    
    //System.out.println("Prints here")

    public static void heuristic_details(BufferedReader placereader) throws Exception 
    {
        String inputText;
        while (!(inputText = placereader.readLine()).contains(EndofInput)) 
        {
            String[] place = inputText.split(" ");
            hData.put(place[0], Integer.parseInt(place[1]));
        }
    }


     public static void findNeighbors(city_details place) throws Exception 
     {
        cost = place.totalCost;
        if (fringe.size() <= 0) 
        {
            System.out.println("Distance: Infinity");
            System.out.println("Route: \nDoes not exist");
        } 
        else if (place.CurrentNode.equals(goalcity)) 
        {
            data_visit.add(place);
            calculate_path(data_visit);
        } 
        else if (fringe.size() > 0) 
        {
            fringe.remove(place);
            Nodes_expanded++;
            for (city_details CurrentNodePlace : data_input) 
            {
            	 if (CurrentNodePlace.ParentNode.equals(place.CurrentNode)) 
                 {
                	if (!nodesvisited.contains(place.CurrentNode)) 
                    {
                		CurrentNodePlace.totalCost = CurrentNodePlace.cost + cost;
                        fringe.add(CurrentNodePlace);
                        Nodes_generated++;
                        //System.out.println("Going Inside IF");
                    }
                }
            }
            if (!nodesvisited.contains(place.CurrentNode)) 
            {
                data_visit.add(place);
                nodesvisited.add(place.CurrentNode);

            }
            if (fringe.size() > 0) 
            {
                if (!h) 
                {
                	Collections.sort(calculate_heuristic_cost(fringe), city_details.comparator_cost);
                    findNeighbors(fringe.get(0));
                } else 
                {
                	Collections.sort(fringe, city_details.comparator_cost);
                    findNeighbors(fringe.get(0));
                }
            }
             else 
             {
                calculate_path(null);
                System.out.println("Distance: Infinity");
                System.out.println("Route: \nDoes not exist");
            }
        }
    }

    public static ArrayList<city_details> calculate_heuristic_cost(ArrayList<city_details> list) throws Exception 
    {
        for (int i = 0; i < list.size(); i++) 
        {
            list.get(i).hCost = hData.get(list.get(i).CurrentNode);
            list.get(i).totalCost = list.get(i).cost + list.get(i).hCost;
        }
        //Syste.out.println("For ends here");
        return list;
    }

    public static void calculate_path(ArrayList<city_details> OptimalRoute) 
    {
         if (OptimalRoute != null) 
         {
            Nodes_expanded++;
        }
        //System.out.println("Print Nodes Expanded and Generated");
        System.out.println("");
        System.out.println("Nodes Expanded:" + (Nodes_expanded));
        System.out.println("Nodes Generated:" + (Nodes_generated));
        
        if (OptimalRoute != null) 
        {
            ArrayList<city_details> finalPath = new ArrayList<city_details>();        
            finalPath.add(OptimalRoute.get(OptimalRoute.size() - 1));
            int totalCost = finalPath.get(0).cost;
            String ParentNode = finalPath.get(0).ParentNode;
            for(int i = OptimalRoute.size() - 2; i != 0; i--) 
            {
                if (OptimalRoute.get(i).CurrentNode.equals(ParentNode)) 
                {
                    ParentNode = OptimalRoute.get(i).ParentNode;
                    totalCost += OptimalRoute.get(i).cost;
                    finalPath.add(OptimalRoute.get(i));
                 
                }
            }
            //System.out.println("Print Distance and Route");
            System.out.println("Distance: " + (float) totalCost + " KM");
            System.out.println("Route:");
                   
            int j = finalPath.size() - 1; 
            while(j >= 0) 
            {
                System.out.println(finalPath.get(j).ParentNode + " to " + finalPath.get(j).CurrentNode + ": " + (float) finalPath.get(j).cost + " KM");
                j--;
            }
        }
    }


     static class city_details {
        String CurrentNode;
        String ParentNode;
        int cost;
        int hCost;
        int totalCost = 0;

        public city_details(String ParentNode, String name, int cost) {
            this.ParentNode = ParentNode;
            this.CurrentNode = name;
            this.cost = cost;
        }

        public static Comparator<city_details> comparator_cost = new Comparator<city_details>() {
            public int compare(city_details s1, city_details s2) {
                //System.out.println("Inside compare method");
                return s1.totalCost - s2.totalCost;
            }
        };
    }
}