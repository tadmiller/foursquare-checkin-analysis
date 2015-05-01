/***********************************************************************
/* @authors Katya Handler, Larisa Sidorovich, Robyn Silber, Tad Miller *
/***********************************************************************
/* Foursquare is a mobile application for "checking in" to places people 
/* have visited. This program collects data about where people have
/* "checked in" using Foursquare's API (Application Program Interface).
/* The "check in" data is stored in a CheckIn object, along with the date,
/* time, and name of the place the check in came from. 
/* 
/* In this project, we continuously collected check in data from Foursquare
/* for a list of pre-defined places. The data, or "CheckIn" objects are stored
/* in a LinkedList of CheckIns which all share the same time from which they were
/* collected. This LinkedList is then put into a HashMap of LinkedLists of CheckIns,
/* where each LinkedList's key is the date and time it was collected. The HashMap is
/* stored into a file using Serializable.
/* 
/* Using this data we have collected each hour of each day, we can figure out things
/* such as what the most popular place is given any day, what places are "declining"
/* (least popular), what place has the most fluctuation of checkins, etc.
/**********************************************************************/

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Set;
import java.util.Scanner;

public class UserInterface // To clarify: this is not a GUI, just the .java filed used to interface with the user.
{
	public static void main(String[] args)
	{
		Foursquare foursquare = new Foursquare(); // Initialize a Foursquare object. This contains all of the methods for acquiring check in data.
		SerializeUtility serializer = new SerializeUtility(); // Initialize a SerializeUtility object. This contains all of the methods for reading/writing data to a file.
		HashMap<CalendarDate, LinkedList<CheckIn>> allCheckins = serializer.deSerialize(); // If we already have a file, we use this to read the data out of it so we can add more.
		
		Scanner s = new Scanner(System.in);
		System.out.println("Would you like to: \n1) Acquire new CheckIn data \n2) Get venue data \n3) Enter continuous data collection mode");
		String input = s.next(); // Get the user input on which action they wish to perform.
		
		if (input.equals("1")) // This acquires new check in data.
		{
			getCheckIns(allCheckins, foursquare, serializer);
		}
		else if (input.equals("2"))
			printVenueOptions(allCheckins);
		else if (input.equals("3")) // This mode collects new check in data every hour until the program is stopped.
		{							// To collect our data, this mode is running on a server machine.
			System.out.println("\nContinuous data collection mode activated.\n");
			
			while (true) // Indefinitely repeat the action.
			{	
				CalendarDate date = new CalendarDate(); // A CalendarDate tells us the current date and time.
				System.out.print("The current time is " + date.getTime() + ". "); // Our output, which lets us know if the program is still running.
				
				if (date.getMinute() == 0) // This will only happen if the time is say 4:00, 5:00, 6:00... etc.
				{
					getCheckIns(allCheckins, foursquare, serializer); // Acquire new check in data.
				}
				else
					System.out.println("No data fetch is required.");
				
				try // Sleep for 60 seconds.
				{
					Thread.sleep(60 * 1000);	//1000 milliseconds is one second. 60 * 1000 = 60 seconds.
				}
				catch(InterruptedException ex)
				{
					Thread.currentThread().interrupt();
				}
			}
		}
	}
	
	// Prints a menu of things we can do to look at venues.
	public static void printVenueOptions(HashMap<CalendarDate, LinkedList<CheckIn>> allCheckins)
	{
		CheckInAnalyzer analyzer = new CheckInAnalyzer(allCheckins);
		Scanner s = new Scanner(System.in);
		
		System.out.println("Would you like to: \n1) Print a list of venues in order of popularity \n2) Get the most popular venue of all time" +
				 "\n3) Get the most popular venue given a day \n4) Get the least popular venue of all time " +
				 "\n5) Get the least popular venue given a day \n6) Get the kth most popular venue of all time");
		
		String input = s.next();
		
		if (input.equals("1"))
			analyzer.printByCheckins();
		else if (input.equals("2")) // This tells the user what the most popular place is out of all of the data we have analyzed.
		{
			Place p = analyzer.getMostPopular();
			System.out.println(p.getName() + " is the most popular place of all time, with a total of " + p.getTotalCheckIns() + " checkins.");
		}
		else if (input.equals("3")) // This tells the user what the most popular place is, given a certain day.
		{			
			System.out.println("Enter the month (Number): "); // We only have CheckIn data from April currently,
			int month = s.nextInt();						  // so there is no reason to enter anything other than 4.
			
			System.out.println("Enter the day (Number): ");
			int day = s.nextInt();
			Place p = analyzer.getMostPopularOnDay(day, month);
			System.out.println(p.getName() + " is the most popular place on " + month + "/" + day + ", with a total of " + p.getTotalCheckIns() + " checkins.");
		}
		else if (input.equals("4")) // This tells the user what the least popular place is out of all of the data we have analyzed.
		{
			Place p = analyzer.getLeastPopular();
			System.out.println(p.getName() + " is the least popular place of all time, with a total of " + p.getTotalCheckIns() + " checkins (clap clap, looks like someone is going out of business).");
		}
		else if (input.equals("5")) // This tells the user what the least popular place is, given a certain day.
		{			
			System.out.println("Enter the month (Number): "); // We only have CheckIn data from April currently,
			int month = s.nextInt();						  // so there is no reason to enter anything other than 4.
			
			System.out.println("Enter the day (Number): ");
			int day = s.nextInt();
			Place p = analyzer.getLeastPopularOnDay(day, month);
			System.out.println(p.getName() + " is the least popular place on " + month + "/" + day + ", with a total of " + p.getTotalCheckIns() + " checkins.");
		}
		else if (input.equals("6"))
		{
			System.out.println("Enter k (Number): ");
			int k = s.nextInt();
			
			Place p = analyzer.getKthMostPopular(k);
			System.out.println(p.getName() + " is the number " + k + " most popular place of all time, with a total of " + p.getTotalCheckIns() + " checkins.");
		}
		else if (input.equals("7"))
		{
			System.out.println("Enter the venue's name: ");
			s = new Scanner(System.in);
			String name = s.nextLine();
			
			analyzer.printCheckinsByDay(name);
		}
	}
	
	// Fetch check in data, store it in a file.
	public static void getCheckIns(HashMap<CalendarDate, LinkedList<CheckIn>> allCheckins, Foursquare foursquare, SerializeUtility serializer)
	{
		System.out.println("Fetching CheckIn data...");
		LinkedList<CheckIn> checkins = foursquare.getCheckins(); // Create a LinkedList, fetch the check in data and store it in the list.
		allCheckins.put(checkins.getFirst().getDate(), checkins); // Put the LinkedList in a HashMap, with the key as the date and time.
		// NOTE: We can use the first element of the LinkedList's date and time, because all of the check ins are collected at the exact same date and time. 
		serializer.serialize(allCheckins); // Store the HashMap into a file after collecting the data. This is for the unlikely event that the program might crash.
		System.out.println("Done.");
	}
	
	// NOTE: Use with caution. Given the size of the current HashMap (≈100KB when this comment was wrote), doing this method may cause a crash.
	public static void printData(HashMap<CalendarDate, LinkedList<CheckIn>> allCheckins) // Given a HashMap, prints out all the data from each LinkedList inside of that HashMap.
	{
		Set<CalendarDate> keys = allCheckins.keySet();
		Iterator<CalendarDate> i = keys.iterator();
		
		while(i.hasNext())
		{
			LinkedList<CheckIn> list = allCheckins.get(i.next());
			ListIterator<CheckIn> j = list.listIterator();
			
			while (j.hasNext())
			{
				CheckIn temp = j.next();
				System.out.println("Name: " + temp.getName() + "\nDate: " + temp.getDate().getDate() + "\nCheckins: " + temp.getCheckins());
			}
		}
	}
}