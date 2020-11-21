import java.util.*;
import java.io.*;
import java.io.PrintWriter;
import java.util.Scanner;

public class BfFw 
{
	private static int [][] edges;
	private static int [] parents;
	private static int [] mindist; 
	private static int [][] fwdist;

	//this method inserts edge weights into our graph
	public static void insert(int v1, int v2, int edge)
	{
		edges[v1 - 1][v2 - 1] = edge;
		edges[v2 - 1][v1 - 1] = edge;
		fwdist[v1 - 1][v2 - 1] = edge;
		fwdist[v2 - 1][v1 - 1] = edge;
	}

	// this method initializes our graph so we don't get any funky issues
	public static void initGraph(int num)
	{
		for(int i = 0; i < num; i++)
		{
			for(int j = 0; j < num; j++)
			{
				edges[i][j] = Integer.MAX_VALUE;
				fwdist[i][j] = Integer.MAX_VALUE;

				if(i == j)
				{
					fwdist[i][j] = 0;
				}
			}
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

	// this is the method that performs the Bellman-Ford algorithm
	public static void bellmanFord(int source, int numVert)
	{
		parents[source - 1] = 0;
		mindist[source - 1] = 0;
		int currentNode = source - 1;

		for(int i = 0; i < numVert; i++)
		{
			mindist[i] = edges[currentNode][i];
			if(mindist[i] < Integer.MAX_VALUE)
				parents[i] = source;
		}

		for(int k = 0; k < numVert - 1; k++)
		{
			for(int j = 0; j < numVert; j++)
			{	
				for(int i = 0; i < numVert; i++)
				{
					if(edges[j][i] != Integer.MAX_VALUE && mindist[j] != Integer.MAX_VALUE && i != currentNode && mindist[i] > mindist[j] + edges[j][i])
					{
						mindist[i] = edges[j][i] + mindist[j];
						parents[i] = j + 1; 
					}
				}
			}
		}
	}

	public static void floydWarshall(int verts)
	{
		for(int k = 0; k < verts; k++)
		{
			for(int i = 0; i < verts; i++)
			{
				for(int j = 0; j < verts; j++)
				{
					if (fwdist[i][k] != Integer.MAX_VALUE && fwdist[k][j] != Integer.MAX_VALUE && fwdist[i][k] + fwdist[k][j] < fwdist[i][j]) 
                        fwdist[i][j] = fwdist[i][k] + fwdist[k][j]; 
				}
			}
		}
	}

	public static void main(String args[]) throws Exception
	{
		PrintWriter pw, pr;
		Scanner scan;
		File in = new File("cop3503-asn3-input.txt");
		String storage;
		int numVert, source, numEdge;

		try 
		{
            pw = new PrintWriter("cop3503-asn3-output-wright-kirsten-bf.txt");
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
        edges = new int[numVert][numVert];
        fwdist = new int[numVert][numVert];
        mindist = new int[numVert];
        initGraph(numVert);

        while(scan.hasNextLine())
        {
        	insert(scan.nextInt(), scan.nextInt(), scan.nextInt());
        }

        bellmanFord(source, numVert);
        floydWarshall(numVert);

        mindist[source - 1] = 0;
        pw.printf("%d\n", numVert);

        for(int i = 0; i < numVert; i++)
        {
        	pw.printf("%d %d %d \n", i + 1, mindist[i], parents[i]);
        }


        pw.close();

        try 
		{
            pr = new PrintWriter("cop3503-asn3-output-wright-kirsten-fw.txt");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return;
        }

        pr.printf("%d\n", numVert);

        for(int i = 0; i < numVert; i++)
        {
       		for(int j = 0; j < numVert; j++)
       		{
       			pr.printf("%d ", fwdist[i][j]);
       		}
       		pr.printf("\n");
        }

        pr.close();
        scan.close();
	}
}
