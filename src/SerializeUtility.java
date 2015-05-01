/***********************************************************************
/* @authors Katya Handler, Larisa Sidorovich, Robyn Silber, Tad Miller *
/***********************************************************************
/* The SerializeUtility class allows us to store our data into a file,
/* and then re-read it back out from the file when we run our program
/* again.
/**********************************************************************/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.HashMap;


public class SerializeUtility
{
	private String fileName;

	public SerializeUtility() // Constructor, sets the file name.
	{
		fileName = "checkins.gwu";
		// NOTE: The extension of the file doesn't matter, as long as we keep it consistent each time we read/write to it.
	}

	/**
	 * This method stores a HashMap of LinkedLists into file "checkins.gwu".
	 * 
	 * @param serialized The HashMap of LinkedLists to serialize into a file.
	 */
	public void serialize(HashMap<CalendarDate, LinkedList<CheckIn>> serialized)
	{ 
		try
		{
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(serialized);
			out.flush();
			out.close();
			fileOut.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	/**
	 * This method looks for the file that contains our data,
	 * and creates a new HashMap of LinkedLists if it can't find
	 * one.
	 * 
	 * @return A HashMap of LinkedLists containing the data it may or may not have read from a file.
	 */
	public HashMap<CalendarDate, LinkedList<CheckIn>> deSerialize()
	{
		HashMap<CalendarDate, LinkedList<CheckIn>> deSerialized = null;
		File file = new File(fileName);
		
		if (file.exists()) // Check to see if the file exists first.
		{
			try
			{
				FileInputStream fileIn = new FileInputStream(file);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				deSerialized = (HashMap<CalendarDate, LinkedList<CheckIn>>) in.readObject();
				in.close();
				fileIn.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();   
			}
			catch(ClassNotFoundException ex)
			{
				ex.printStackTrace();
			}
		}
		else
			deSerialized = new HashMap<CalendarDate, LinkedList<CheckIn>>(); // In the case the file doesn't exist, we just return a HashMap object.
		
		return deSerialized;
	}	
}
