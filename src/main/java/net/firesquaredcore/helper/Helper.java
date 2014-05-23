package net.firesquaredcore.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class Helper
{
	public class Strings
	{
		public static final String MOD_ID = "firesquaredcore";
		public static final String MOD_NAME = "FireSquared Core";
		public static final String VERSION_NUMBER = "0.0.2";
		public static final String CHANNEL_NAME = MOD_ID;
	}
	private static final Logger logger = new Logger(Strings.MOD_NAME, true, Level.ALL);
	
	public static Logger getLogger()
	{
		return logger;
	}

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
		M(1000),
		IV_(4000, "I/x0304"+"V/x0304"),
		V_(5000, "V\u0304"),
		IX_(9000, "I\u0304"+"X\u0304"),
		X_(10000, "X\u0304"),
		XL_(40000, "X\u0304"+"L\u0304"),
		L_(50000, "L\u0304"),
		XC_(90000, "X\u0304"+"C\u0304"),
		C_(100000, "C\u0304"),
		CD_(400000, "C\u0304"+"D \u0304"),
		D_(500000, "D\u0304"),
		CM_(900000, "C\u0304"+"M\u0304"),
		M_(1000000, "M\u0304");
		int val;
		String text;
		public int value()
		{
			return val;
		}
		Numeral(int val)
		{
			this.val = val;
		}
		Numeral(int val, String text)
		{
			this.val = val;
			this.text = text;
		}
		@Override
		public String toString()
		{
			if(text != null)
				return text;
			else
				return super.toString();
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
			if(i>=1000000)
				return M_;
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
			if(i < 0 || i > 3999999)
				throw new IndexOutOfBoundsException("Roman numeral function bounded between 0 and 3,999,999");
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
