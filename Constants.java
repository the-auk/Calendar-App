package project;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/*************************************************************
 * Constants class.
 * 
 * All constants in this program.
 *************************************************************/
public final class Constants {
	
	/*****************************
	 * VIEW CONFIGURATION
	 *****************************/
	
	public static final int ELEMENT_HEIGHT = 50;
	
	public static final int VIEW_PANEL_WIDTH = 400;
	
	public static final int TAB_HEIGHT = 30;
	
	//CalendarStatus
	public static final int CALENDAR_STATUS_WIDTH = 400;
	public static final Color CALENDAR_STATUS_COLOR = new Color(233,235,205);
	
	//EventTab 
	public static final Color EVEN_EVENT_TAB_COLOR = new Color(233,235,245);
	public static final Color ODD_EVENT_TAB_COLOR = new Color(124,185,232);
	
	
	/*****************************
	 * CALENDAR TABLE CONFIGURATION
	 *****************************/
	public static final int CELL_SIZE = 40;
	public static final Font CALENDAR_FONT =  new Font("Arial", Font.BOLD, 15);
	public static final Font CALENDAR_TIME_TAB_FONT =  new Font("Arial", Font.BOLD, 18);
	public static final Font CALENDAR_lABEL_FONT =  new Font("Arial", Font.BOLD, 20);
	
	public static final Color CELL_COLOR_EVEN = new Color(200,200,200);
	public static final Color CELL_COLOR_ODD = new Color(128,128,128);
	
	public static final Color CELL_BACKGROUND_COLOR_ENTER = new Color(179, 196, 255);
	
	public static final Color CELL_COLOR_HIGHLIGHT = new Color(60,60,60);
	public static final Border CELL_BORDER_HIGHLIGHT = BorderFactory.createLineBorder(new Color(179,179,0), 4);
	
	public static final Color CALENDAR_COLOR_NOT_IN_MONTH = new Color(170,170,170);
}
