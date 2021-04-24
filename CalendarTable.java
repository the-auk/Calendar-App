package project;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import javax.swing.border.EmptyBorder;

/**************************************************************
 * CalendarTable class.
 * 
 * This is a JPanel that shows a calendar in one month. It has
 * 42 cells spread out in 6 rows (7 cells for each). Each cell 
 * is clickable and represents 1 day. It also has a JLabel showing
 * which month it is at and 2 JButtons to shift the calendar backward
 * and forward in time. 
 * 
 * This JPanel is the View part of the MVC pattern.
 *************************************************************/
public class CalendarTable extends JPanel {

	private static final long serialVersionUID = -118033593491818509L;
	
	private StateMachine stateMachine;
	
	private LocalDate firstDayOfMonth;
	
	private Cell [] cells;
	private JLabel monthLabel;
	private JButton forwardB;
	private JButton backwardB;
	
	/* 
	 * Constructor method.
	 * ------
	 * Create a CalendarTable object. 
	 * 
	 * Initialize all 42 cells, set firstDayOfMonth attribute to the
	 * first day of current month, set monthLabel, initialize 2 JButton
	 * to shift the calendar forward and backward in time.
	 */
	public CalendarTable(StateMachine sm) {
		super();
		
		stateMachine = sm;
		cells = new Cell[42];

		setLayout(new BorderLayout(0,10));
		setBorder(new EmptyBorder(10,10,10,10));
		
		JPanel calendarTable = createCalendarTable();
		
		firstDayOfMonth = LocalDate.now() 
								   .minusDays(
										   LocalDate.now()
										   .getDayOfMonth() -1);
		
		setCalendar();
		
		monthLabel = createMonthLabel();
		setMonthLabel();
		
		forwardB = createBrowseButton(">", l -> {
			firstDayOfMonth = firstDayOfMonth.plusMonths(1);
			setCalendar();
			setMonthLabel();
		});
		
		backwardB = createBrowseButton("<", l -> {
			firstDayOfMonth = firstDayOfMonth.minusMonths(1);
			setCalendar();
			setMonthLabel();
		});
		
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.add(monthLabel, BorderLayout.WEST);
		headerPanel.add(backwardB, BorderLayout.CENTER);
		headerPanel.add(forwardB, BorderLayout.EAST);
		
		add(calendarTable, BorderLayout.CENTER);
		add(headerPanel, BorderLayout.NORTH);
	}
	
	/* 
	 * setCalendar method.
	 * ------
	 * This method initializes all 42 cells of the CalendarTable using
	 * firstDayOfMonth attribute as the seed. 
	 */
	public void setCalendar() {
		LocalDate firstDayOfNextMonth = firstDayOfMonth.plusMonths(1);
		
		int daysFromSunday = firstDayOfMonth.getDayOfWeek().getValue()%7;
		LocalDate currentDay = firstDayOfMonth.minusDays(daysFromSunday);
		
		int index = 0;
		
		//Add days before this month
		while(currentDay.isBefore(firstDayOfMonth)) {
			cells[index].setHighLight(false);
			
			setCell(index, currentDay, Constants.CALENDAR_COLOR_NOT_IN_MONTH);						
			currentDay = currentDay.plusDays(1);
			index++;
		}
		
		//Add days of this month
		while(currentDay.isBefore(firstDayOfNextMonth)) {
			if(currentDay.isEqual(LocalDate.now())) {
				cells[index].setHighLight(true);
			}
			else {
				cells[index].setHighLight(false);
			}
			
			
			setCell(index, currentDay, Color.BLACK);						
			currentDay = currentDay.plusDays(1);
			index++;
		}		
		
		//Add days after this month
		while(index < cells.length) {
			cells[index].setHighLight(false);
			
			setCell(index, currentDay, Constants.CALENDAR_COLOR_NOT_IN_MONTH);						
			currentDay = currentDay.plusDays(1);
			index++;
		}		
		
	}

	/* 
	 * setCell method.
	 * ------
	 * This method initializes a single cell to a certain date in calendar
	 * 
	 * @param index an integer specifies the index of the cell to be set
	 * 				in the array.
	 * @param currentDay a LocalDate object specifies the date that the 
	 * 					 cell to be set represents.
	 * @param foreground Color of the foreground. This Color is used to 
	 * 					 differentiate between cells represents dates of
	 * 					 the month that is currently displayed and cells
	 * 					 represents dates before and after that.
	 */
	private void setCell(int index, LocalDate currentDay, Color foreground) {
		cells[index].setDay(currentDay);
		
		int row = index/7;
				
		Color background = (row%2 == 0)? 
				Constants.CELL_COLOR_EVEN 
				: Constants.CELL_COLOR_ODD; 
		
		
		cells[index].setDefaultColor(background, foreground);
		cells[index].restoreDefaultColor();
		
	}
	
	
	/* 
	 * createCalendarTable method.
	 * ------
	 * This method will be called after setCalendar method. It will create
	 * a new JPanel with 7 rows and 7 columns. The first row is the title row
	 * with labels represent 7 days of the week (S,M,T,W,T,F,S). Six rows below
	 * are populated with 42 cells.
	 * 
	 *  @return a JPanel as described above.
	 */
	private JPanel createCalendarTable() {
		JPanel cT = new JPanel(new GridLayout(7, 7, 1, 1));
		
		cT.setBackground(Color.WHITE);
		
		setTitleRow(cT);
		
		for(int i = 0; i < cells.length; i++) {
			cells[i] = new Cell();		
			cT.add(cells[i]);
		}
		
		
		
		return cT;
	}
	
	
	/* 
	 * setTitleRow method.
	 * ------
	 * This method will create the first row of the CalendarTable.
	 * This first row is populated by 7 JLabel whose contents are
	 * "S", "M", "T", "W", "T", "F", "S".
	 */	
	private void setTitleRow(JPanel cTable) {
		String [] dOfWeek = {"S", "M", "T", "W", "T", "F", "S"};
		for(int i = 0; i < 7; i++) {
			JLabel label = new JLabel(dOfWeek[i]);
			
			label.setBackground(Color.BLACK);
			label.setForeground(Color.WHITE);
			label.setOpaque(true);
			
			label.setFont(Constants.CALENDAR_FONT);
			
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.CENTER);
			
			
			cTable.add(label);
		}
	}

	/* 
	 * setTitleRow method.
	 * ------
	 * This method will create the first row of the CalendarTable.
	 * This first row is populated by 7 JLabels whose contents are
	 * "S", "M", "T", "W", "T", "F", "S".
	 */
	private JLabel createMonthLabel() {
		JLabel label = new JLabel();
		
		label = new JLabel();
		label.setPreferredSize(new Dimension(Constants.CELL_SIZE*5, Constants.CELL_SIZE));
		
		label.setFont(Constants.CALENDAR_lABEL_FONT);
		label.setBorder(new EmptyBorder(0,10,0,0));
		
		return label;
	}

	/* 
	 * setMonthLabel method.
	 * ------
	 * Set the JLabel attribute of CalendarTable (monthLabel) to the month currently
	 * displayed by the CalendarTable in format "MMMM yyyy"
	 */
	private void setMonthLabel() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL yyyy");
		monthLabel.setText(firstDayOfMonth.format(formatter));
	}
	
	
	/* 
	 * createBrowseButton method.
	 * ------
	 * Create a JButton that can shift the month represented by this CalendarTable
	 * back and forth in time.
	 */
	private JButton createBrowseButton(String title, ActionListener a) {
		JButton button = new JButton(title);
		
		button.setFont(Constants.CALENDAR_FONT);
		
		
		button.addActionListener(a);
		
		return button;
	}
	
	
	
	/**************************************************************
	 * Cell class.
	 * 
	 * This is a JPanel that implements ChangeTime and ChangeView interface.
	 * It represents a specific day in CalendarTable. Its text content is the
	 * day-of-month number. When being clicked, it changes the currentView
	 * attribute of the StateMachine to DAY_VIEW and the current period specified
	 * in the StateMachine to the day it represents.
	 *************************************************************/		
	
	private class Cell extends JLabel implements ChangeTime, ChangeView {
		private static final long serialVersionUID = 2586699234289039427L;
		
		private LocalDate day;
		
		private int size = Constants.CELL_SIZE;
		private Color backgroundColor;
		private Color foregroundColor;
		
				
		/* 
		 * Constructor method.
		 * ------
		 * Create a Cell object. 
		 * This constructor will:
		 * 			- Set size and font. 
		 * 			- Align the text to center.
		 * 			- Add MouseListener.
		 */
		public Cell() {
			super();
			
			Cell that = this;
			
			setPreferredSize(new Dimension(size, size));
			
			setFont(Constants.CALENDAR_FONT);
			
			setHorizontalAlignment(SwingConstants.CENTER);
			setVerticalAlignment(SwingConstants.CENTER);
			
			setOpaque(true);
			
			addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					stateMachine.updateState(that);
				}

				@Override
				public void mousePressed(MouseEvent e) {}

				@Override
				public void mouseReleased(MouseEvent e) {}

				@Override
				public void mouseEntered(MouseEvent e) {
					changeColor(Constants.CELL_BACKGROUND_COLOR_ENTER, foregroundColor);
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					restoreDefaultColor();
				}
				
			});
		}
		
		/* 
		 * setDay method.
		 * ------
		 * Set this cell to represent a specific day.
		 * @param d a LocalDate object represents the day that this cell
		 * 			will be set to.
		 */
		public void setDay(LocalDate d) {
			day = d;
			
			setText(day.getDayOfMonth() + "");
		}
		
		
		/* 
		 * setHighLight method.
		 * ------
		 * Change the outlook of this cell to highlight mode. This method is 
		 * used when the cell represents today's date.
		 */
		public void setHighLight(boolean highLight) {
			if(highLight) {
				setBorder(Constants.CELL_BORDER_HIGHLIGHT);
				changeColor(Constants.CELL_COLOR_HIGHLIGHT, Color.WHITE);

			}
			else {
				setBorder(BorderFactory.createEmptyBorder());
				restoreDefaultColor();
			}
		}
		
		
		/* 
		 * setDefaultColor method.
		 * ------
		 * Set the default background and foreground color of this cell
		 */
		public void setDefaultColor(Color background, Color foreground) {
			backgroundColor = background;
			foregroundColor = foreground;
		}

		/* 
		 * setDefaultColor method.
		 * ------
		 * Change the background and foreground color of this cell
		 */
		public void changeColor(Color background, Color foreground) {
			setBackground(background);
			setForeground(foreground);
		}
		
		/* 
		 * restoreDefaultColor method.
		 * ------
		 * Restore the background and foreground color of this cell to
		 * default.
		 */
		public void restoreDefaultColor() {
			setBackground(backgroundColor);
			setForeground(foregroundColor);
			
		}
		 
		
		/* 
		 * getView method.
		 * ------
		 * To change the currentView attribute of StateMachine to DAY_VIEW,
		 * the updateState of the StateMachine will call this method to get the
		 * value of DAY_VIEW to assign to currentView.
		 * 
		 * @return an integer whose value is equal to the constant DAY_VIEW defined
		 * 		  in StateMachine
		 */
		@Override
		public int getView() {
			return StateMachine.DAY_VIEW;
		}

		/* 
		 * getPeriod method.
		 * ------
		 * To change the ViewPanel to display events scheduled on the date this cell
		 * represents, this method will be called in updateState method of the 
		 * StateMachine to get the value of the 1-day-period which is this date.
		 * 
		 * @return an array of 2 LocalDate objects represents 1-day-period which is 
		 * this date.
		 */
		@Override
		public LocalDate[] getPeriod() {
			LocalDate [] period = {day, day.plusDays(1)};
						
			return period;
		}

		/* 
		 * getDirection method.
		 * ------
		 * getDirection method will be called by the updateState method of StateMachine and the
		 * return value will be passed to shiftCurrentPeriod method so that the current state of 
		 * the StateMachine will be set to the specific date represented by this cell. 
		 * 
		 * @return an integer whose value is equal to the constant SPECIFIC_DATE defined
		 * 		   in ChangeTime interface.
		 */
		@Override
		public int getDirection() {
			return ChangeTime.SPECIFIC_DATE;
		}

		/* 
		 * getDate method
		 * ------
		 * @return A LocalDate object specifies the day that this cell
		 * 		   represents. It is used in the StateMachine to set its 
		 * 		   current period. 
		 */
		@Override
		public LocalDate getDate() {
			return day;
		}

		

		
		
		
	}


}
