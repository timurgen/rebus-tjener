package student.hin.no;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;


public class GameRebus implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idGame;
	private String authorName;
	private String name;
	private int varighet;
	private boolean isOpen;
	private long startDate;
	private ArrayList<Long> partisipants;
	private ArrayList<GamePunktRebus> pointList;
	private int currentPoint;
	private String dateString;
	
	public GameRebus(String _idGame, String _authorName, String _name, int _varighet, boolean _isOpen, String _start) throws ParseException
	{
		//DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date date;
		date = new Date(Long.valueOf(_start));
		//date = formatter.parse(_start);
		startDate = date.getTime();
		idGame = Long.parseLong(_idGame);
		authorName = _authorName;
		name = _name;
		varighet = _varighet;
		isOpen = _isOpen;
		currentPoint = 0;
		dateString = _start;
		
		partisipants = new ArrayList<Long>();
		pointList = new ArrayList<GamePunktRebus>();
	}//end of constructor
	
	/**
	 * GET or SET metodes
	 * *************************************************************************************************
	 */
	
	    public Long getId() {
	        return idGame;
	    }

	    public boolean isIsOpen() {
	        return isOpen;
	    }

	    public void setIsOpen(boolean isOpen) {
	        this.isOpen = isOpen;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public long getStartDate() {
	        return startDate;
	    }
	    
	    public String getStartDateString()
	    {
	    	/*Time time = new Time();
	    	time.set(startDate);
	    	String startDateString = time.toString();*/
	    	//String startDateString = Long.toString(startDate);
	    	return dateString;
	    }

	    public void setStartDate(long startDate) {
	        this.startDate = startDate;
	    }
	
	    public int getVarighet() {
	        return varighet;
	    }
	    
	    public String getVarighetString()
	    {
	    	String varighetString = Integer.toString(varighet);
	    	return varighetString;
	    }

	    public void setVarighet(int varighet) {
	        this.varighet = varighet;
	    }

	    public String getAuthorName() {
	        return authorName;
	    }
	    
	    /**
	     * END OF GET or SET
	     * **********************************************************************************************
	     */
	    
	    public void addPoint(GamePunktRebus gamePunktRebus) {
	        this.pointList.add(gamePunktRebus);
	    }
	    
	    public GamePunktRebus getFirstPoint()
	    {
	    	return pointList.get(0);
	    }
	    
	    public GamePunktRebus getNextPunkt() throws Exception {
	        if(this.currentPoint < this.pointList.size()) {   
	            return this.pointList.get(this.currentPoint++);
	        }
	        else {
	           throw new Exception();  
	        }
	    }
	    
	    /**
	     * 
	     * @param id til deltaker
	     * @throws Exception
	     */
	    public void addPartisipant(long id) throws Exception {
	        if(this.partisipants.contains(id)){
	            throw new Exception("user allerede i list");
	        }
	        else {
	            this.partisipants.add(id);
	        }
	    }
	    
	    public ArrayList<Long> getAllPartisipants() {
	        return this.partisipants;
	    }
	    
	    public boolean getPartisipant(long id) {
	        return (this.partisipants.contains(id));
	    }
	    
	    
	
}//End of GameRebus class
