/***********************************************************************
/* @authors Katya Handler, Larisa Sidorovich, Robyn Silber, Tad Miller *
/***********************************************************************
/* A Foursquare object contains all of the information for accessing
/* check in data from the Foursquare API.
/**********************************************************************/

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CheckinGroup;

import java.util.LinkedList;
import java.util.ListIterator;

public class Foursquare
{
	FoursquareApi foursquareApi;
	Places places;
	
	public Foursquare() // Initializes a FourSquareApi object. We don't need all the methods from it so we're using our own called Foursquare.
	{
		String client_ID = "IGAPDAWEUNLWIS44YFCIFBKUKCPSUFZ21ENFKC4E5RDVZWH0";
		String client_Secret = "QRZVXMPB13JEJ51A2R3QR0NCVITLPCV4PFTUBBCNNNGYWU0V";
		String callbackURL = "CS1112.tk";
		foursquareApi = new FoursquareApi(client_ID, client_Secret, callbackURL);
		places = new Places(); // Initializes the Places object to get a LinkedList of places.
	}
	
	public LinkedList<CheckIn> getCheckins() // Returns a LinkedList of CheckIns based on the places stored in the Places object.
	{
		LinkedList<CheckIn> checkins = new LinkedList<CheckIn>(); // Make a LinkedList for storing the checkins.
		
		ListIterator<Place> i = places.getPlaces().listIterator(); // Our iterator for iterating through the places.
		
		while (i.hasNext())
		{
			try
			{
				Place place = (Place)i.next(); // Get the next place.
				Result<CheckinGroup> result = foursquareApi.venuesHereNow(place.getID(), 500, 0, 0L); // Get the checkins for that place.
				checkins.add(new CheckIn(place.getName(), result.getResult().getCount().intValue())); // Make a new CheckIn object and store it in the LinkedList of CheckIns.
			}
			catch(FoursquareApiException ex) // Must have in case we hit an exception.
			{
			}
		}
		
		return checkins; // Returns the LinkedList of CheckIns.
	}
}
