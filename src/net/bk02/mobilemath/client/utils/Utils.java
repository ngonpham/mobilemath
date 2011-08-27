package net.bk02.mobilemath.client.utils;

import java.util.Vector;

import net.bk02.mobilemath.client.parser.Function;
import net.bk02.mobilemath.client.parser.Variable;

public class Utils
{
	public static String []arr;
	private static double e = 2.71828182845904524;
	private static Variable v = new Variable('Y');
	private static Function f = new Function();
	private static Vector vec = new Vector();

	public static String strDelSpc(String s)
	{
		String s1 = "";
		for(int i=0; i<s.length(); i++)
			if (s.charAt(i) != ' ') s1 = s1 + String.valueOf(s.charAt(i));
		return s1;
	}

	public static void strSplitOf(String s, char c)
	{
		if (s.length() == 0)
		{
            arr = new String[0];
            return;
		}
		if (s.charAt(s.length()-1) != c) s = s + c;
		//Count
		int i, k, count = 0;
		for (i=0; i<s.length(); i++)
			if(s.charAt(i) == c) count++;
		//Split
		String ss = String.valueOf(c);
		arr = new String[count];
		count = 0;
		k = s.indexOf(ss);
		while (k >= 0)
		{
			arr[count] = s.substring(0, k);
			s = s.substring(k+1);
			k = s.indexOf(ss);
			count++;
		}
		return;
	}

	public static String strReplace(String s, String olds, String news)
	{
		int k = s.indexOf(olds);
		while (k >= 0)
		{
			if (k > 0)
				s = s.substring(0, k) + news + s.substring(k+olds.length());
			else
				s = news + s.substring(k+olds.length());
			k = s.indexOf(olds);
		}
		return s;
	}

	public static double round(double a)
	{
		if (a - Math.floor(a) < Math.ceil(a) - a)
			return Math.floor(a);
		else
			return Math.ceil(a);
	}

	public static double exp(double a)
	{
		if ((a > 20) || (a < -20)) return 1/0;
		boolean minus = false;
		if (a < 0) {minus = true; a = -a;};

		double res = 1;
		for (int i=1; i<=Math.floor(a); i++)
			res = res * e;
		a -= Math.floor(a);
		double a1 = 1, a2 = 1, r = 1;
		for (int i=1; i<21; i++)
		{
			a1 *= a; a2 *= i;
			r += a1/a2;
        }
		res *= r;
		if (minus) return 1/res;else return res;
	}

	public static double ln(double a)
	{
		if (a >= 1)
		{
			double res = 0;
			while (a >= e)
			{
				a = a / e;
				res++;
			}
			if (a > 2.225540928)
			{
				res += 0.8;
				a /= 2.225540928;
			}
			else if (a > 1.8221188)
			{
				res += 0.6;
				a /= 1.8221188;
			}
			else if (a > 1.491824698)
			{
				res += 0.4;
				a /= 1.491824698;
			}
			else if (a > 1.221402758)
			{
				res += 0.2;
				a /= 1.221402758;
			}
			a = a - 1;
			double a1 = 1;
			for (int i=1; i<21; i++)
			{
				a1 *= a;
				if (i%2==1) res += a1/i;
				else res-=a1/i;
			}
			return res;
		}
		else return 1/0;
	}

	public static double pow(double a, double b)
	{
		return exp(b*ln(a));
	}

	public static double trunc(double a)
	{
		return round(a*1000)/1000;
	}

	public static void prepareVal(char c, String exp)
	{
		v.setName(c);
		vec.removeAllElements();
		vec.addElement(v);
		f.initialize(exp);
	}

	public static double Cal(double a)
	{
		v.setValue(a);
		return f.calculate(vec);
	}
}
