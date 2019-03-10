import java.util.*;
import java.util.Random;
import java.lang.Math;
import java.io.*;

class KMeans
{
	static int iterations=0;
	static double distance(double[] a, double[] b)
	{
		double sum=0;
		for(int i=0;i<b.length;i++)
		{
			double d=a[i]-b[i];
			sum+=d*d;
		}
		return Math.sqrt(sum);
	}
	
	static int closest(double[] v)
	{
		double min=v[0];
		int minlabel=0;
		for(int i=1;i<v.length;i++)
		{
			if(v[i]<min)
			{
				min=v[i];
				minlabel=i;
			}
		}
		return minlabel;
	}
	
	static void algo(double[][] c, double[][] a, int m)
	{
		int k=c.length;
		int n=a.length;
		//System.out.println("k="+k+"\tm="+m+"\tn="+n);
		double[][] oldc=new double[k][m];
		for(int i=0;i<k;i++)
			for(int j=0;j<m;j++)
				oldc[i][j]=c[i][j];
		
		
		double[][] dist=new double[n][k];
		//ArrayList<double>[] al=new ArrayList[k];
		
		for(int i=0;i<k;i++)
		{
			for(int j=0;j<n;j++)
			{
				dist[j][i]=distance(a[j],c[i]);
			}
		}
		
		double[][] sum=new double[k][m];
		int[] count=new int[k];			//for counting number of elements in the cluster
		for(int i=0; i<k;i++)
		{
			for(int j=0;j<m;j++)
			{
				sum[i][j]=0;			//initializing sum=0
			}
			count[i]=0;
		}
		for(int i=0;i<n;i++)
		{
			int minc=closest(dist[i]);
			for(int j=0;j<m;j++)
			{
				sum[minc][j]+=a[i][j];
			}
			count[minc]+=1;
		}
		
		//new centroid	
		for(int i=0;i<k;i++)
		{
			System.out.println("Count"+i+"="+count[i]);
			for(int j=0;j<m;j++)
			{
				//System.out.println("sum= "+sum[i][j]);
				c[i][j]=sum[i][j]/(count[i]);
			}
		}
		
		for(int i=0;i<k;i++)
		{
			System.out.print("Centroid"+i+" : ");
			for(int j=0;j<m;j++)
			{
				System.out.print(c[i][j]+"\t");
			}
			System.out.println("");
		}

		if(!shouldStop(oldc,c,k,m))
			algo(c,a,m);
	}
	
	static boolean shouldStop(double[][] oldc, double[][] c, int k, int m)
	{
		iterations++;
		
		//System.out.println("checking code");
		if(iterations>=10)
			return true;
		
		for(int i=0;i<k;i++)
		{
			for(int j=0;j<m;j++)
			{
				if(oldc[i][j]!=c[i][j])
					return false;
			}
		}
		return true;			//satisfy
	}
	
	public static void main(String args[]) throws IOException, FileNotFoundException
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the file name: ");
		String fName=sc.next();
		BufferedReader br=new BufferedReader(new FileReader(fName));
		System.out.println("Enter the no. of datasets: ");
		int n=sc.nextInt();
		System.out.println("Enter the number of attributes: ");
		int m=sc.nextInt();
		//int[][] attribute=new int[m][n];
		//ArrayList<String>[] attribute=new ArrayList[m];
		double[][] a=new double[n][m];
		String[] label=new String[n];
		
		String line;
		for(int i=0;(line=br.readLine())!=null;i++)
		{
			String[] temp=line.split(",");
			int j;
			for(j=0;j<m;j++)
			{
				a[i][j]=Double.parseDouble(temp[j]);
				//System.out.print(a[i][j]+"\t");
			}
			label[i]=temp[j];
			//System.out.println("");
		}
			
		System.out.println("Enter the number of clusters: ");
		int k=sc.nextInt();
		
		double[][] c=new double[k][m];
		Random r=new Random();
		int[] x=new int[k];			//getting random index
		x[0]=r.nextInt(n);
		int f=1;
		while(f<k)
		{
			x[f]=r.nextInt(n);
			if(x[f]==x[f-1])
				continue;
			else
				f++;
		}		//to avoid duplicate
		
		for(int i=0;i<k;i++)
			for(int j=0;j<m;j++)
				c[i][j]=a[x[i]][j];
		
		//KMeans ob=new KMeans();
		algo(c,a,m);
		System.out.println("\nNumber of iterations: "+iterations);
	}
}