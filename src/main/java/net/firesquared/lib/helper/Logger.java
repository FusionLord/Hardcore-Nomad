package net.firesquared.lib.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.logging.log4j.Level;

public class Logger
{
	String modIdentifier;
	boolean timeStamp;
	public Logger(String modIdentifier, boolean useTimestamp)
	{
		this.modIdentifier = "[ " + modIdentifier + " ] ";
	}
	public void log(Level logLevel, Object object)
	{
		if(timeStamp)
			System.out.println(getTimeStamp() + modIdentifier + String.valueOf(object));
		else
			System.out.println(modIdentifier + String.valueOf(object));
	}
	
	private String getTimeStamp()
	{
		GregorianCalendar cal = new GregorianCalendar();
		return "["+cal.get(cal.HOUR_OF_DAY) + cal.get(cal.MINUTE) + cal.get(cal.SECOND) + cal.get(cal.MILLISECOND) + "]";
	}

	public void all(Object object)
	{
		log(Level.ALL, object);
	}

	public void trace(Object object)
	{
		log(Level.TRACE, object);
	}

	public void fatal(Object object)
	{
		log(Level.FATAL, object);
	}

	public void error(Object object)
	{
		log(Level.ERROR, object);
	}

	public void warn(Object object)
	{
		log(Level.WARN, object);
	}

	public void info(Object object)
	{
		log(Level.INFO, object);
	}

	public void off(Object object)
	{
		log(Level.OFF, object);
	}
}
