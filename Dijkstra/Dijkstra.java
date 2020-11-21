import java.util.*;
import java.io.*;
import java.io.PrintWriter;
import java.util.Scanner;

public class Dijkstra 
{
	private static int [][] edges;
	private static boolean [] visited;
	private static int [] parents;
	private static int [] mindist; 

	//this method inserts edge weights into our graph
	public static void insert(int v1, int v2, int edge)
	{
		edges[v1 - 1][v2 - 1] = edge;
		edges[v2 - 1][v1 - 1] = edge;
	}

	// this method initializes our graph so we don't get any funky issues
	public static void initGraph(int num)
	{
		for(int i = 0; i < num; i++)
		{
			for(int j = 0; j < num; j++)
			{
				edges[i][j] = Integer.MAX_VALUE;
			}
		}

		for(int i = 0; i < num; i++)
		{
			visited[i] = false;
		}

		for(int i = 0; i < num; i++)
		{
			parents[i] = Integer.MIN_VALUE;
		}

		for(int i = 0; i < num; i++)
		{
			mindist[i] = Integer.MAX_VALUE;
		}
	}

	// this method finds the next "current" node
	public static int findNext(int node, int numVert)
	{
		int min = Integer.MAX_VALUE, mindex = -1;

		for(int i = 0; i < numVert; i++)
		{
			if(mindist[i] <= min && !visited[i])
			{
				min = mindist[i];
				mindex = i;
			}
		}

		return mindex;
	}

	// this is the method that actually performs dijkstra's 
	public static void doTheThing(int source, int numVert)
	{
		parents[source - 1] = -1;
		mindist[source - 1] = 0;
		int currentNode = source - 1;

		for(int j = 0; j < numVert; j++)
		{	
			for(int i = 0; i < numVert; i++)
			{
				if(edges[currentNode][i] != Integer.MAX_VALUE && mindist[currentNode] != Integer.MAX_VALUE && i != currentNode && mindist[i] >= mindist[currentNode] + edges[currentNode][i] && !visited[i])
				{
					mindist[i] = edges[currentNode][i] + mindist[currentNode];
					parents[i] = currentNode + 1; 
				}
			}
			visited[currentNode] = true;
			currentNode = findNext(currentNode, numVert);
		}


	}

	public static void main(String args[]) throws Exception
	{
		PrintWriter pw;
		Scanner scan;
		File in = new File("cop3503-asn2-input.txt");
		String storage;
		int numVert, source, numEdge;

		try 
		{
            pw = new PrintWriter("cop3503-asn2-output-wright-kirsten.txt");
            scan = new Scanner(in);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return;
        }

        numVert = Integer.parseInt(scan.nextLine());
        source = Integer.parseInt(scan.nextLine());
        numEdge = Integer.parseInt(scan.nextLine());

        parents = new int[numVert];
        visited = new boolean[numVert];
        edges = new int[numVert][numVert];
        mindist = new int[numVert];
        initGraph(numVert);

        while(scan.hasNextLine())
        {
        	insert(scan.nextInt(), scan.nextInt(), scan.nextInt());
        	//storage = scan.next();
            //insert(Character.getNumericValue(storage.charAt(0)), Character.getNumericValue(storage.charAt(2)), Character.getNumericValue(storage.charAt(4)));      
        }

        doTheThing(source, numVert);

        mindist[source - 1] = -1;
        pw.printf("%d\n", numVert);

        for(int i = 0; i < numVert; i++)
        {
        	pw.printf("%d %d %d \n", i + 1, mindist[i], parents[i]);
        }


        pw.close();
        scan.close();
	}
}
