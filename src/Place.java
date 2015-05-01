/***********************************************************************
/* @authors Katya Handler, Larisa Sidorovich, Robyn Silber, Tad Miller *
/***********************************************************************
/* A Place object represents a physical place. It has a name, an ID 
/* (Used by Foursquare), a total number of check ins, and an average.
/* 
/* The ID is used when requesting CheckIn data about the place from
/* Foursquare. If you give the API an ID, it will return to you the
/* amount of CheckIns at the time of the request.
/* 
/* The total number of check ins is used when we want to analyze the
/* check in data. Each CheckIn object's check ins for each place will
/* be added to the Place's total check in count.
/* 
/* The timesAdded variable is used when determining the most popular place. 
/* Before the Continuous Data Collection mode was created, data was collected
/* only once per day. The timesAdded count allows us to accurately use the old
/* data, by counting each time we add check ins to the total number of
/* check ins. If we add y check ins x amount of times, the average check
/* ins, C-ave, would be C-ave = y / x.
/**********************************************************************/


public class Place
{
	private String id; // The ID used by Foursquare when requesting check in data.
	private String name; // The name of the place.
	private int totalCheckIns; // The total number of check ins.
	private int timesAdded; // The amount of times we added check ins to the total number of check ins.
	
	public Place(String i, String n) // Creates a new place with a given id and name.
	{
		id = i;
		name = n;
		totalCheckIns = 0;
		timesAdded = 0;
	}
	
	public String getName() // Returns the name of the place.
	{
		return name;
	}
	
	public String getID() // Returns the ID of the place.
	{
		return id;
	}
	
	public void addCheckIns(int n) // Adds n amount of check ins to the check in count. Increments the amount of times we've added check ins.
	{
		totalCheckIns += n;
		timesAdded++;
	}
	
	public int getTotalCheckIns() // Returns the total number of check ins.
	{
		return totalCheckIns;
	}
	
	public double getAverageCheckIns() // Returns the average amount of check ins.
	{								  // This is used to figure out the most popular place.
		if (timesAdded > 0) // If we call this before we add any CheckIns, we may end up with an exception.
			return (double)getTotalCheckIns() / (double)timesAdded;
		else
			return getTotalCheckIns();
	}
}
