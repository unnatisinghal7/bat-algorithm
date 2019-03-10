import java.util.*;
import java.lang.Math;
import java.io.*;

class BatAlgo
{
	public static final double alfa=0.5264;
	public static final double gamma=4.411;
	public static double A0=0.45;//0.5026;
	public static double r0=0.5;//0.4205;
	public static final int N=30;		//no.of bats
	public static final int DIM=10;
	public static final double fmin=0;
	public static final double fmax=2;
	public static final int itr=10000;		//iterations
	
	/*public static final double xmin=-5.12;//-10;
	public static final double xmax=5.12;//10;
	//fitness manipulation of an array
	static double Fitcalc(double a[])
	 {
		 double out=0; 
		 for(int i=0;i<DIM;i++)
		 { 
			out+=(a[i])*(a[i]);
		 }	 
		 return out;	 
	 }*/
	 
	//f1 function
	//DIM=1
	/*public static final double xmin=0;
	public static final double xmax=20;
	static double Fitcalc(double a[])
	{
		double out=1;
		for(int i=1;i>=10;i++)
		{
			out*=a[0]-2*i;
		}
		return out;
	}*/
	
	//f2 function
	//DIM=2
	/*public static final double xmin=-100;
	public static final double xmax=100;
	static double Fitcalc(double a[])
	{
		double temp=a[0]*a[0]-a[1]*a[1];
		double out=0.5-((Math.pow(Math.sin(Math.sqrt(temp)),2)-0.5)/(Math.pow((1.0+0.001*temp),2)));
		return out;
	}*/
	
	//f3 function
	//DIM=5		//multi
	/*public static final double xmin=-0.5;
	public static final double xmax=0.5;
	static double Fitcalc(double a[])
	{
		int n=5;
		double out=0;
		for(int i=0;i<DIM;i++)
		{
			out+=Math.abs((Math.sin(Math.PI*n*a[i]))/((Math.PI*n*a[i])+0.00001));
		}
		return out;
	}*/
		
	//f4 function De Jong
	//DIM=30			//multi
	/*public static final double xmin=-1.28;
	public static final double xmax=1.28;
	static double Fitcalc(double a[])
	{
		Random r=new Random();
		double out=0;
		for(int i=0;i<DIM;i++)
		{
			out+=(i*Math.pow(a[i],4))+r.nextGaussian();	
		}
		return out;
	}*/
	
	//Rosenbrock function
	//DIM=n
	public static final double xmin=-2.048;
	public static final double xmax=2.048;
	static double Fitcalc(double a[])
	{
		double out=0;
		for(int i=0;i<DIM-1;i++)
		{
			out+=((1.0-a[i]*a[i])*(1.0-a[i]*a[i]))+(100*Math.pow(a[i+1]-a[i]*a[i],2));
		}
		return out;
	}
	
	//booth
	//dim=2
	/*public static final double xmin=-10;
	public static final double xmax=10;
	static double Fitcalc(double x[])
	{
		double out=0;
		for(int i=0;i<DIM;i++)
		{
			out=(x[0]+2.0*x[1]-7.0)*(x[0]+2.0*x[1]-7.0)+(2.0*x[0]+x[1]-5.0)*(2.0*x[0]+x[1]-5.0);
		}
		return out;
	}*/
	
	//easom
	//dim=2
	/*public static final double xmin=-10;
	public static final double xmax=10;
	static double Fitcalc(double x[])
	{
		double out=0;
		for(int i=0;i<DIM;i++)
		{
			out=-Math.cos(x[0])*Math.cos(x[1])*Math.exp(-(x[0]-Math.PI)*(x[0]-Math.PI)-(x[1]-Math.PI)*(x[1]-Math.PI));;
		}
		return out;
	}*/
	
	
	static double[] minvalindex(double a[])
	{
		double index=0;
		double value=a[0];
		for(int i=0;i<a.length;i++)
		{
			if(a[i]<value)
			{
				value=a[i];
				index=i;
			}
		}
		double[] ans=new double[2];
		ans[0]=value;
		ans[1]=index;
		return ans;
	}
	
	static double[] withinbounds(double s[])
	{
		for(int i=0;i<DIM;i++)
		{
			if(s[i]<xmin)
				s[i]=xmin;
			else if(s[i]>xmax)
				s[i]=xmax;
		}
		return s;
	}
	
	public static void toCSV(double[] avgfitness, double[] bestans) throws IOException
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter file name: ");
		String fName=sc.next();
		final String COMMA_DELIMITER = ",";
	    final String NEW_LINE_SEPARATOR = "\n";
		
		double avgfitsum=0, bestfitsum=0;
		FileWriter fw = null;
		fw=new FileWriter(fName);
		for(int i=0;i<avgfitness.length;i++)
		{
			//System.out.println(avgfitness[i]+"  "+bestans[i]);
			fw.append(String.valueOf(bestans[i]));
			fw.append(COMMA_DELIMITER);
			fw.append(String.valueOf(avgfitness[i]));
			fw.append(NEW_LINE_SEPARATOR);
			bestfitsum+=bestans[i];
			avgfitsum+=avgfitness[i];
		}
		System.out.println("Average of averaage fitness="+(avgfitsum/avgfitness.length));
		System.out.println("Average of best fitness="+(bestfitsum/bestans.length));
	}
	
	public static void main(String args[]) throws IOException
	{
		Random rand=new Random();
		double[][] sol=new double[N][DIM];		//population
		double[] fitness=new double[N];
		
		double[] bestans=new double[itr];
		double[] best=new double[DIM];
		double minfit=Double.MAX_VALUE;
		//initialize
		for(int i=0;i<N;i++)
		{
			for(int j=0;j<DIM;j++)
			{
				sol[i][j]=xmin+(xmax-xmin)*Math.random();
			}
			fitness[i]=Fitcalc(sol[i]);
		}
		double d1[]=minvalindex(fitness);
		minfit=d1[0];
		int index=(int)d1[1];
		best=sol[index];
		
		
		double[] f=new double[N];				//frequency
		double[][] v=new double[N][DIM];		//velocity
		double[][] x=new double[N][DIM];		//position
		double newfit=0;
		double sumfitness=0;
		double[] avgfitness=new double[itr];
		
		for(int t=1;t<=itr;t++)
		{
			sumfitness=0;
			for(int i=0;i<N;i++)
			{
				f[i]=fmin+(fmin-fmax)*rand.nextDouble();
				for(int j=0;j<DIM;j++)
				{
					v[i][j]=v[i][j]+(sol[i][j]-best[j])*f[i];
					x[i][j]=sol[i][j]+v[i][j];
				}
				//to get the solution within bounds of function
				sol[i]=withinbounds(sol[i]);
				
				if(rand.nextDouble()>r0)
				{
					//select solution among best solutions2
					for(int j=0;j<DIM;j++)
						x[i][j]=best[j]+0.001*rand.nextGaussian();		//why?
				}
				//generate local solution around selected best
				
				//generate new solution randomly
				newfit=Fitcalc(x[i]);
				/*if(rand.nextInt(1)==1)
					newfit+=rand.nextDouble()*A0;
				else
					newfit-=rand.nextDouble()*A0;*/
				
				if(newfit<=fitness[i] && rand.nextDouble()<A0)
				{
					//accept new solution
					for(int j=0;j<DIM;j++)
					{
						sol[i][j]=x[i][j];
					}
					fitness[i]=newfit;
					r0=r0*(1-Math.exp(-gamma*t));
					A0=A0*alfa;
				}
				
				//rank bats and find current best
				if(newfit<=minfit)
				{
					for(int j=0;j<DIM;j++)
					{
						best[j]=x[i][j];
					}
					minfit=newfit;
				}
				sumfitness+=fitness[i];
				if(t==8000)
					System.out.println("sumfitness= "+sumfitness+" fitness["+i+"]= "+fitness[i]); 
			}
			avgfitness[t-1]=sumfitness/N;
			bestans[t-1]=minfit;
			//System.out.println(avgfitness[itr-1]+"  "+bestans[itr-1]);
		}
		
		System.out.println("Optimized value: "+minfit);
		for(int i=0;i<DIM;i++)
			System.out.println("x["+i+"]: "+best[i]);
		toCSV(avgfitness, bestans);
	}
}