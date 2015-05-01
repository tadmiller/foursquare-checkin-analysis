/***********************************************************************
/* @authors Katya Handler, Larisa Sidorovich, Robyn Silber, Tad Miller *
/***********************************************************************
/* A CalendarDate object represents a date on a calendar. It has
/* a month, a day, a year, an hour, and a minute. At the time of
/* initialization of a CalendarDate object, the date and time is set.
/**********************************************************************/

import java.util.Calendar;
import java.util.TimeZone;
import java.io.Serializable;

public class CalendarDate implements Serializable
{
	private int month;
	private int day;
	private int year;
	private int hour;
	private int minute;
	
	/**
	 * Constructor.
	 * 
	 * Sets the date and time equal to the time of it's initialization.
	 */
	public CalendarDate() // Takes no arguments. Automatically sets the date equal to the time it was initialized.
	{
		Calendar c = Calendar.getInstance(TimeZone.getDefault());
		
		month = c.get(Calendar.MONTH) + 1;
		day = c.get(Calendar.DATE);
		year = c.get(Calendar.YEAR);
		hour = c.getTime().getHours();
		minute = c.getTime().getMinutes();
	}

	/**
	 * 
	 * @return The month of initialization.
	 */
	public int getMonth()
	{
		return month;
	}

	/**
	 * @return The day of initialization.
	 */
	public int getDay()
	{
		return day;
	}

	
	/**
	 * @return The year of initialization.
	 */
	public int getYear()
	{
		return year;
	}
	
	/**
	 * @return The hour of initialization.
	 */
	public int getHour()
	{
		return hour;
	}
	
	/**
	 * Concatenates a date in MM/DD/YY, HH:MM format.
	 * 
	 * @return A String containing the date and time of initialization.
	 */
	public String getDate()
	{
		return "" + getMonth() + "/" + getDay() + "/" + getYear() + ", " + getTime() + "";
	}
	
	/**
	 * Concatenates the hour and minute into a String of HH:MM format.
	 * 
	 * @return A String of this format, containing the time of initialization.
	 */
	public String getTime() 
	{
		String minutes = "" + getMinute();
		if (minute < 10)
			minutes = "0" + getMinute();
		
		if (hour > 12)
			return "" + (getHour() - 12) + ":" + minutes + " PM";
		else if (hour < 10)
			return "0" + (getHour()) + ":" + minutes + " AM";
		else
			return "" + getHour() + ":" + minutes + " AM"; 
			
	}

	/**
	 * @return An integer that is the minute of initialization.
	 */
	public int getMinute()
	{
		return minute;
	}
}
