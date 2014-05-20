package net.firesquared.hardcorenomad.helpers;

import java.util.ArrayList;
import java.util.List;

import scala.collection.mutable.Queue;

public class Helper
{
	public enum Numeral
	{
		N(0),
		I(1),
		IV(4),
		V(5),
		IX(9),
		X(10),
		XL(40),
		L(50),
		XC(90),
		C(100),
		CD(400),
		D(500),
		CM(900),
		M(1000);
		int val;
		public int value()
		{
			return val;
		}
		Numeral(int val)
		{
			this.val = val;
		}
		public static Numeral fromString(String s)
		{
			if(s.length() == 0 || s.length() > 2)
				throw new IllegalArgumentException();
			if(s.length() == 1)
				return fromChar(s.charAt(0));
			else
			{
				Numeral first = fromChar(s.charAt(0));
				Numeral second = fromChar(s.charAt(1));
				if(second.val < first.val || second.val/first.val>10)
					throw new IllegalArgumentException();
				return fromInt(second.val-first.val);
			}
		}
		public static Numeral fromChar(char c)
		{
			switch(c)
			{
				case 'N': return N;
				case 'I': return I;
				case 'V': return V;
				case 'X': return X;
				case 'L': return L;
				case 'C': return C;
				case 'D': return D;
				case 'M': return M;				
				default:
					throw new IllegalArgumentException();
			}
		}
		public static Numeral fromInt(int i)
		{
			for(Numeral n : Numeral.values())
				if(n.value() == i)
					return n;
			return null;
		}
		
		public static Numeral nextSmallest(int i)
		{
			if(i>=1000)
				return M;
			Numeral out = N;
			for(Numeral n : Numeral.values())
			{
				if(n.value() <= i)
					out = n;
				else
					break;
			}
			return out;
		}

		//derived from algorithm at http://www.blackwasp.co.uk/NumberToRoman.aspx
		public static String ToRoman(int i)
		{
			if(i < 0 || i > 3999)
				throw new IndexOutOfBoundsException("Roman numeral function bounded between 0 and 3999");
			if(i == 0)
				return "N";
			return RRoman(i);
			
		}
		private static String RRoman(int i)
		{
			if(i == 0)
				return "";
			else
			{
				Numeral n = nextSmallest(i);
				return n.toString().concat(RRoman(i-n.value()));
			}
		}
		public static int Parse(String numeral)
		{
			List<Numeral> nums = new ArrayList<Numeral>();
			for(char c : numeral.toCharArray())
			{
				nums.add(fromChar(c));
			}
			int sum = 0, i = 0;
			for(; i < nums.size(); i++)
			{
				if(nums.size() - i > 1 && nums.get(i).val<nums.get(i+1).val)
				{
					sum -= nums.get(i).val - nums.get(++i).val;
					continue;
				}
				else
					sum += nums.get(i).val;
			}
			
			return sum;
		}
	}
	

}
