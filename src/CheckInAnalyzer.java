/***********************************************************************
/* @authors Katya Handler, Larisa Sidorovich, Robyn Silber, Tad Miller *
/***********************************************************************
/* The CheckInAnalyzer class contains all the methods for analyzing
/* venue data. It can tell us what the most popular venue, or "Place"
/* is out of all the data we collect. It can tell us what the most popular
/* Place is given a certain day. It can tell us what the Kth most popular
/* place is, and is able to sort the CheckIn objects by the number of
/* check ins using Merge Sort.
/**********************************************************************/

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Set;

public class CheckInAnalyzer
{
	private HashMap<CalendarDate, LinkedList<CheckIn>> allCheckins;
	private Places places;
	
	/**
	 * This constructor gives us the HashMap of checkins, initializes a Places object,
	 * and sorts the CheckIn objects by the number of check ins.
	 * 
	 * @param checkins The HashMap of LinkedLists containing all of the CheckIn objects.
	 */
	public CheckInAnalyzer(HashMap<CalendarDate, LinkedList<CheckIn>> checkins)
	{
		allCheckins = checkins;
		places = new Places();
		//sortByCheckIns();
	}
	
	/**
	 * This method sorts all of the LinkedLists within the HashMap
	 * by check in count.
	 * @deprecated
	 */
	public void sortByCheckIns()
	{
		Set<CalendarDate> keys = allCheckins.keySet();
		Iterator<CalendarDate> i = keys.iterator();
		
		while(i.hasNext()) // Sort each LinkedList.
		{
			LinkedList<CheckIn> temp = allCheckins.get(i.next());
			temp = mergeSort(temp); // LinkedLists act peculiar, so we must use an assignment statement to set them equal to their sorted counterpart.
		}
	}
	
	/**
	 * This method prints all of the check ins for a given venue
	 * by each day that it has a check in.
	 * 
	 * @param name The name of the venue to print the number of check ins by day.
	 */
	public void printCheckinsByDay(String name)
	{
		Set<CalendarDate> keys = allCheckins.keySet();
		Iterator<CalendarDate> i = keys.iterator();
		int first = i.next().getDay();
		int last = first;
		int month = 4; // Assume it's 4 since our data only spands into April.
		
		while (i.hasNext()) // Find the first date we got check in data for this venue (It's April 20th)
		{					// Find the last date we got check in date for this venue (That may change)
			CalendarDate date = i.next();
			
			if (date.getDay() < first)
				first = date.getDay();
			else if (date.getDay() > last)
				last = date.getDay();
		}
		
		while (first <= last) // Iterate through the data, and print out the associated information for the venue by each day.
		{
			Places p = tallyCheckInsOnDay(first, 4);
			Place place = p.search(name, 0, p.getPlaces().size() - 1);
			
			System.out.println("Date: " + month + "/" + first + " Venue: " + place.getName() + " Check ins: " + place.getTotalCheckIns());
			first++;
		}
	}
	
	/**
	 * Prints a list of all of the venue by check ins, highest to lowest.
	 */
	public void printByCheckins()
	{
		Places p = tallyCheckInsOnDay(-1, -1);
		p.sortByCheckins(0, p.getPlaces().size() - 1); // Sort the list by check ins. We can also sort it by name.
		
		for (int i = 0; i < p.getPlaces().size(); i++)
			System.out.println(p.getPlaces().get(i).getName() + ", checkins: " + p.getPlaces().get(i).getTotalCheckIns());
	}
	
	/**
	 * Returns the least popular place.
	 * 
	 * @return A Place object containing the least popular venue, or the venue with the least amount of check ins.
	 */
	public Place getLeastPopular()
	{
		return getLeastPopularOnDay(-1, -1);
	}
	
	/**
	 * Returns the least popular place, given a day and month,
	 * 
	 * @param day The day we want to find the least popular place.
	 * @param month The month we want to find the least popular place.
	 * @return The least popular place on a certain day and month.
	 */
	public Place getLeastPopularOnDay(int day, int month)
	{
		Places p = tallyCheckInsOnDay(day, month); // Tally the amount of check ins on the day we're given.
		Place min = p.getPlaces().get(0);
		
		for (int i = 1; i < p.getPlaces().size(); i++) // Find the place with the least amount of check ins.
		{
			if (p.getPlaces().get(i).getAverageCheckIns() < min.getAverageCheckIns())
				min = p.getPlaces().get(i);
		}
		
		return min;		
	}
	
	/**
	 * 
	 * @param k The number on a list of venues, which will be sorted by the number of check ins.
	 * @return The venue placed k on a list of venues sorted by the number of check ins.
	 */
	public Place getKthMostPopular(int k)
	{
		return getKthMostPopularOnDay(-1, -1, k);
	}
	
	/**
	 * 
	 * @param day The day which we wish to find the Kth most popular venue.
	 * @param month The month which we wish to find the Kth most popular venue.
	 * @param k The number on a list of venues, which will be sorted by the number of check ins.
	 * @return The venue placed k on a list of venues sorted by the number of check ins.
	 */
	public Place getKthMostPopularOnDay(int day, int month, int k)
	{
		Places p = tallyCheckInsOnDay(day, month);
		p.sortByCheckins(0, p.getPlaces().size() - 1); // Sort the list of Place objects by check ins.
		
		return p.getPlaces().get(k - 1); // Return k - 1 because our ArrayList starts at 0. If we were looking for a venue in place x,
																			// our ArrayList would have it stored in slot x - 1.
	}
	
	/**
	 * Get the most popular venue of all time.
	 * 
	 * @return The most popular venue of all time.
	 */
	public Place getMostPopular()
	{
		return getMostPopularOnDay(-1, -1);
	}
	
	/**
	 * Given a day and month, retrieves the most popular venue.
	 * 
	 * @param day The day which we wish to find the most popular venue.
	 * @param month The month which we wish to find the most popular venue.
	 * @return The most popular venue on the day and month that we are given.
	 */
	public Place getMostPopularOnDay(int day, int month)
	{
		Places p = tallyCheckInsOnDay(day, month); // Count the number of check ins for each place on the given month and day.
		Place max = p.getPlaces().get(0);
		
		for (int i = 1; i < p.getPlaces().size(); i++) // Find the place with the max amount of check ins.
		{
			if (p.getPlaces().get(i).getAverageCheckIns() > max.getAverageCheckIns())
				max = p.getPlaces().get(i);
		}
		
		return max;
	}
	
	/**
	 * Tallys all of the check ins for each venue on a given day and month.
	 * 
	 * @param day The day we wish to tally the number of check ins for all the venues on. If set as -1, it will do for every day in the given month.
	 * @param month // The month we wish to tally the number of check ins for all the venues on. If set as -1, it will do for every month.
	 * @return A Places object, which contains an ArrayList of Place objects.
	 */
	public Places tallyCheckInsOnDay(int day, int month)
	{
		Places p = new Places(); // Create a new Places object. We can tally up the check ins in this object for each Place.
		Set<CalendarDate> keys = allCheckins.keySet(); // This is for iterating through a HashMap.
		Iterator<CalendarDate> i = keys.iterator(); // And of course a Set requires an Iterator.
		
		while(i.hasNext()) // Iterate through our HashMap using keys.
		{
			CalendarDate date = i.next();
			if ((((date.getDay() == day) && (date.getMonth() == month))) || (day == -1) && (month == -1))
			{	// Check to see if the day and month the HashMap's key is assigned to
				// is equal to the day and month we wish to tally check ins on.
				// NOTE: If the day and month are set to -1, we will just tally ALL of the check ins.
				LinkedList<CheckIn> temp = allCheckins.get(date);
				ListIterator<CheckIn> j = temp.listIterator();
				
				while(j.hasNext())
				{
					// search p (Places) for the place, count the checkins
					CheckIn c = j.next();
					p.search(c.getName(), 0, p.getPlaces().size() - 1).addCheckIns(c.getCheckins());
				}
			}
		}
		
		return p; // Return the Places.
	}
	
	/**
	 * Sort a LinkedList of CheckIns by the number of check ins.
	 * 
	 * In hindsight, it's probably better to convert a LinkedList into an array and sort it,
	 * then re-convert it back into a LinkedList.
	 * 
	 * @param checkins A LinkedList of CheckIn objects to sort, by check in number.
	 * @return A sorted LinkedList of CheckIn objects.
	 * @deprecated
	 */
	public LinkedList<CheckIn> mergeSort(LinkedList<CheckIn> checkins) // Use Merge Sort to sort a LinkedList by check in count.
	{
		int size = checkins.size(); // The size of the LinkedList of CheckIns.
		// We do this because calling size() is an O(n) operation for LinkedLists.
		
		if (size < 2) // Merge Sort's base case: if there is only one element in the list.
			return checkins;
		
		ListIterator<CheckIn> i = checkins.listIterator(); // Iterates through the LinkedList we want to sort.
		LinkedList<CheckIn> first = new LinkedList<CheckIn>(); // Two new LinkedLists for splitting the main one into.
		LinkedList<CheckIn> second = new LinkedList<CheckIn>();
		int count = 0; // We count the amount of times we iterate through the LinkedList, because there is no index in a LinkedList.

		while (i.hasNext() && count < size / 2) // Split the first half of the main LinkedList into the first LinkedList.
		{
			first.add(i.next());
			count++; // Count so we know when where we are in the LinkedList.
		}
		
		while (i.hasNext()) // We don't need to count for the second LinkedList, because we will just iterate
		{					// from the point we left off in the first LinkedList until the end.
			second.add(i.next());
		}
		
		first = mergeSort(first); // The recursive calls to split the first LinkedList into two more LinkedLists.
		second = mergeSort(second); // and the second LinkedList.
		
		return checkins = merge(first, second); // Merge the two LinkedLists together and return that LinkedList.
	}
	
	/**
	 * Given two sorted LinkedLists, merges them into one larger LinkedList and returns it.
	 * 
	 * @param first The first LinkedList to merge.
	 * @param second The second LinkedList to merge.
	 * @return A sorted LinkedList consisting of elements from the first and second parameters.
	 */
	public LinkedList<CheckIn> merge(LinkedList<CheckIn> first, LinkedList<CheckIn> second)
	{
		LinkedList<CheckIn> all = new LinkedList<CheckIn>(); // Create a new LinkedList for adding the elements into.
		ListIterator<CheckIn> i = first.listIterator(); // The listIterator for the first LinkedList.
		ListIterator<CheckIn> j = second.listIterator(); // and for the second.
		
		CheckIn q, p; // For storing the CheckIn objects in, because we have to use next()
		// to get a new CheckIn and we need to access it on multiple occasions.
		while (i.hasNext() && j.hasNext()) // While the first and second LinkedList's ListIterators have next elements.
		{
			q = i.next(); // Get the next elements for each LinkedList.
			p = j.next();

			if (q.getCheckins() >= p.getCheckins())
			{

				all.add(q);
				p = j.previous(); // Because we q and p are the next elements from each LinkedList, we send the one we don't
			}					  // add into the new LinkedList back because we are going to say next() at the beginning of
			else if (q.getCheckins() < p.getCheckins()) // the loop. This prevents us from losing elements in our LinkedList.
			{
				all.add(p);
				q = i.previous();
			}
		}
		
		while (i.hasNext()) // Add any remaining elements into the new LinkedList.
			all.add(i.next());
		
		while(j.hasNext())
			all.add(j.next());
		
		return all; // Return the new, merged LinkedList, containing elements from the first and second LinkedLists (and it's sorted)
	}
}