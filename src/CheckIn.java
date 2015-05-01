/***********************************************************************
/* @authors Katya Handler, Larisa Sidorovich, Robyn Silber, Tad Miller *
/***********************************************************************
/* A CheckIn object represents the count of people at a certain venue
/* given a certain time. It has a date and time, the name of the venue,
/* and the amount of check ins (people) there. Because check ins
/* remove themselves every hour on Foursquare, we can fetch new check
/* using the continuous data fetch mode for better analytics.
/**********************************************************************/

import java.io.Serializable;

public class CheckIn implements Serializable
{
	private String name; // The name of the place.
	private int checkins; // The number of checkins at the time of initialization of the CheckIn object.
	private CalendarDate date; // The date/time of initialization of the CheckIn object.
	
	CheckIn(String n, int c) // n is the name, c is the amount of checkins
	{
		name = n;
		checkins = c;
		date = new CalendarDate(); // This initializes a new CalendarDate object which has the current date and time of the CheckIn object initialization.
	}
	
	public String getTime() // Returns the time of the initialization of the CheckIn object.
	{
		return date.getTime();
	}
	
	public CalendarDate getDate() // Returns the CalendarDate object.
	{
		return date;
	}
	
	public String getName() // Returns the name of the place for the CheckIn object.
	{
		return name;
	}

	public int getCheckins() // Returns the number of checkins for the CheckIn object.
	{
		return checkins;
	}
}
