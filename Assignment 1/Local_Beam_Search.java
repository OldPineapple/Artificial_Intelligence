package Q3Pb;

import java.util.Random;

public class Local_Beam_Search {
	
	static double max = 10.0;
	static double min = 0.0;
	static double stepsize = 0.01;
	static int beamwidth = 2;
	
	public static double getRandomX() {
		Random r = new Random();
	    double x = min + (max - min) * r.nextDouble();
	    return x;
	}
	
	public static double getRandomY() {
		Random r = new Random();
	    double y = min + (max - min) * r.nextDouble();
	    return y;
	}
	
	public static double calculate(double a, double b) {
		double result;
		if (a > 10)
			a = 10;
		else if (a < 0)
			a = 0;
		if (b > 10)
			b = 10;
		else if (b < 0)
			b = 0;
		//result = Math.sin(2 * a) + Math.cos(b / 2);
		result = Math.abs(a - 2) + Math.abs(0.5 * b + 1) - 4;
		return result;
	}
	
	public static double getMax(double[] t) {
	    double max = t[0];
	    for (int i=1; i<t.length; i++) {
	        if (t[i] > max) {
	            max = t[i];
	        }
	    }
	    return max;
	}
	
	public static int getMinNum(int [] t) {
		int index = 0;
		int min = t[0];
		for (int i = 1; i < t.length; i++) {
			if(t[i] < min) {
				min = t[i];
				index = i;
			}
		}
		return index;
	}
	
	public static int getNum(double x, double y) {
		double xarray[] = {x, x, x + stepsize, x + stepsize, x + stepsize, x - stepsize, x - stepsize, x - stepsize};
		double yarray[] = {y + stepsize, y - stepsize, y, y + stepsize, y - stepsize, y, y + stepsize, y - stepsize};
		double result[] = new double[8];
		for (int i = 0; i < 8; i++) {
			result[i] = calculate(xarray[i], yarray[i]);
		}
		double max = getMax(result);
		for (int j = 0; j < 8; j ++) {
			if (max == result[j])
				return j;
		}
		return 0;
	}
	
	public static double average1 (int a[], int n) {
		double sum = 0.0;
		for (int i = 0; i < n; i++) {
			sum += a[i];
		}
		return sum / n;
	}
	
	public static double standev1 (int a[]) {
		double mean = average1 (a, a.length);
		double sd = 0.0;
		for (int i = 0; i < a.length; i++) {
			sd += Math.pow((a[i] - mean),2);
		}
		sd = Math.sqrt(sd / a.length);
		return sd;
	}
	
	public static double average2 (double a[], int n) {
		double sum = 0.0;
		for (int i = 0; i < n; i++) {
			sum += a[i];
		}
		return sum / n;
	}
	
	public static double standev2 (double a[]) {
		double mean = average2 (a, a.length);
		double sd = 0.0;
		for (int i = 0; i < a.length; i++) {
			sd += Math.pow((a[i] - mean),2);
		}
		sd = Math.sqrt(sd / a.length);
		return sd;
	}

	public static void main(String args[]) {
		int converge[] = new int[100];
		double finalval[] = new double[100];
		for (int n = 0; n < 100; n++) {
			double tempval[] = new double[beamwidth];
			int tempconv[] = new int[beamwidth];
			for (int k = 0; k < beamwidth; k++) {
				double x = getRandomX();
				double y = getRandomY();
				double values[] = new double[2000];
				values[0] = calculate (x, y);
				int j = 1;
				while (j < 2000) {
					double xarray[] = {x, x, x + stepsize, x + stepsize, x + stepsize, x - stepsize, x - stepsize, x - stepsize};
					double yarray[] = {y + stepsize, y - stepsize, y, y + stepsize, y - stepsize, y, y + stepsize, y - stepsize};
					int m = getNum(x, y);
					x = xarray[m];
					y = yarray[m];
					values[j] = calculate(x, y);
					if (values[j] == values[j-1]) {
						break;
					}
					if (j == 1999)
						break;
					j++;
				}
				tempval[k] = values[j];
				tempconv[k] = j;
			}
			int index = getMinNum(tempconv);
			finalval[n] = tempval[index];
			converge[n] = tempconv[index];
		}
		double mean1 = average1 (converge, converge.length);
		double mean2 = average2 (finalval, finalval.length);
		double sd1 = standev1 (converge);
		double sd2 = standev2 (finalval);	
		System.out.println("average steps:" + mean1);
		System.out.println("standard deviation steps:" + sd1);
		System.out.println("average final value:" + mean2);
		System.out.println("standard deviation final value:" + sd2);
	}
}
