import java.util.*;
import java.util.Random;
import java.lang.Math;
import java.io.*;

class KNN
{
	static int iterations=10;
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
	
	static int findmax(double[] v)
	{
		double max=v[0];
		int maxlabel=0;
		for(int i=1;i<v.length;i++)
		{
			if(v[i]>max)
			{
				max=v[i];
				maxlabel=i;
			}
		}
		return maxlabel;
	}
	
	public static class Distance
	{
		double d;
		String label;
		
		Distance(double d1, String l1)
		{
			d=d1;
			label=l1;
		}
	}
	
	static String algo(double[][] a, double[] t, int k, String[] l)
	{
		int n=a.length;
		int m=t.length;			//no. of attributes without output data
		//System.out.println("n="+n+"\t m="+m);
		Distance[] dist=new Distance[k];
		
		//int[] neighbors=new int[k];			//holding index of k neighbors
		//to find distance of k neighbors
		for(int i=0;i<n;i++)
		{
			//double distances=distance(a[i],c);
			if(i<k)
			{
				//System.out.println(dist[i].d);
				dist[i]=new Distance(distance(a[i],t),l[i]);
				//dist[i].d=distance(a[i],t);
				//dist[i].label=l[i];
			}
			else
			{
				double max=dist[0].d;
				int maxindex=0; 
				for(int j=1;j<k;j++)
				{
					if(dist[j].d>max)
					{
						max=dist[j].d;
						maxindex=j;
					}
				}
				
				if(dist[maxindex].d>distance(a[i],t))
				{
					dist[maxindex].d=distance(a[i],t);
					dist[maxindex].label=l[i];
				}
			}
		}
		
		System.out.println("The "+k+"-nearest neighbours are: ");
		for(int i=0;i<k;i++)
			System.out.println(dist[i].d+"\t"+dist[i].label);
		
		//ArrayList<Count> count=new ArrayList<>();
		//to find max count of class
		Hashtable<String,Integer> count=new Hashtable<String,Integer>();
		for(int i=0;i<k;i++)
		{
			if(count.containsKey(dist[i].label))
			{
				int c=count.get(dist[i].label);
				count.put(dist[i].label,c++);
			}
			else
			{
				count.put(dist[i].label,1);
			}
		}
		
		//to find the class occurring maximum number of times
		Set<String> keys=count.keySet();
		Iterator<String> itr=keys.iterator();
		
		String maxcl="";
		while(itr.hasNext())
		{
			int maxval=0;
			String cl=itr.next();
			int temp=count.get(cl);
			if(temp>maxval)
			{
				maxval=temp;
				maxcl=cl;
			}
		}
		return maxcl;
	}
	
	public static void main(String args[]) throws IOException, FileNotFoundException
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the file name: ");
		String fName=sc.next();
		BufferedReader br=new BufferedReader(new FileReader(fName));
		System.out.println("Enter the no. of datasets: ");
		int n=sc.nextInt();
		System.out.println("Enter the number of attributes(including output attribute): ");
		int m=sc.nextInt();
		double[][] a=new double[n][m-1];
		String[] label=new String[n];
		
		//String line=br.readLine();
		String line;
		for(int i=0;(line=br.readLine())!=null;i++)
		{
			String[] temp=line.split(",");
			int j;
			for(j=0;j<m-1;j++)
			{
				a[i][j]=Double.parseDouble(temp[j]);
			}
			label[i]=temp[j];
			//System.out.println("");
		}
		
		double[] t=new double[m-1];
		System.out.println("Enter the test data: ");
		for(int i=0;i<m-1;i++)
		{
			System.out.println("Enter attribute"+(i+1)+": ");
			t[i]=sc.nextDouble();
		}
			
		System.out.println("Enter the number of neighbors: ");
		int k=sc.nextInt();
		
		//KMeans ob=new KMeans();
		String ans=algo(a,t,k,label);
		System.out.println(ans);
	}
}