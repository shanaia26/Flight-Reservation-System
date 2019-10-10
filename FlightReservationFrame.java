import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Color;

public class FlightReservationFrame extends JFrame {

	// Panels
	// private JPanel parentPanel = new JPanel();
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();

	// Components for Panel1
	private JLabel departureLabel = new JLabel("Departure: ");
	private JComboBox departureBox;
	private JLabel destinationLabel = new JLabel("Destination: ");
	private JComboBox destinationBox;
	private JButton searchButton = new JButton("Search");
	private JButton showButton = new JButton("Show All");

	// Components for ComboBox
	private String[] departureCities = { "Copenhagen", "Dublin", "Edinburgh", "London", "New York", "Oslo",
			"San Francisco" };
	private String[] destinationCities = { "Copenhagen", "Dublin", "Edinburgh", "London", "New York", "Oslo",
			"San Francisco" };

	// Components for Panel2
	// Components for Panel3
	private JLabel ticketsLabel = new JLabel("Number of Tickets: ");
	private JTextField ticketsField = new JTextField(10);
	private JButton purchaseButton = new JButton("Purchase");
	private JLabel dateLabel = new JLabel("Today's Date: ");
	private JLabel date = new JLabel();

	public FlightReservationFrame() {
		FlightReservationModel tm = new FlightReservationModel();
		JTable tbl = new JTable(tm);
		tbl.setPreferredScrollableViewportSize(new Dimension(500, 70));
		tbl.setFillsViewportHeight(true);

		// Create comboBox
		departureBox = new JComboBox(departureCities);
		destinationBox = new JComboBox(destinationCities);

		tbl.setAutoCreateRowSorter(true);

		// Calls Today's date method
		reformat();

		// Action Listener for Buttons

		// Creating the sorter
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tbl.getModel());
		tbl.setRowSorter(sorter);

		// Add filtering to button
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (departureBox.getSelectedItem() == destinationBox.getSelectedItem()) {
					JOptionPane.showMessageDialog(panel1, "Departure and destination cities cannot be the same.");

				} else {

					sorter.setRowFilter(RowFilter.regexFilter("(^)+(?i)" + departureBox.getSelectedItem(), 0));
					sorter.setRowFilter(RowFilter.regexFilter("(^)+(?i)" + destinationBox.getSelectedItem(), 1));
				}
			}
		});
		
		//Formats Textfield to only taking in numbers
		int num = 0;
		try{
			num = Integer.parseInt(ticketsField.getText());
		}
		catch(NumberFormatException e){
			JOptionPane.showMessageDialog(this, "Must enter a number",
					"Warning", JOptionPane.WARNING_MESSAGE);
		}
		panel1.add(departureLabel);
		panel1.add(departureBox);
		// Default to Dublin
		departureBox.setSelectedIndex(1);
		panel1.add(destinationLabel);
		panel1.add(destinationBox);
		panel1.add(searchButton);
		panel1.add(showButton);

		JScrollPane srl = new JScrollPane(tbl);
		panel2.add(srl);

		panel3.add(ticketsLabel);
		panel3.add(ticketsField);
		panel3.add(purchaseButton);
		panel3.add(dateLabel);
		panel3.add(date);

		// parentPanel.add(panel1);
		add(panel1, BorderLayout.NORTH);

		// parentPanel.add(panel2);
		add(panel2);

		add(panel3, BorderLayout.SOUTH);

		// set frame properties
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		setTitle("Flight Reservation System");
	}

	/** Formats and displays today's date. */
	public void reformat() {
		Date today = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		try {
			String dateString = formatter.format(today);
			date.setForeground(Color.black);
			date.setText(dateString);
		} catch (IllegalArgumentException iae) {
			date.setForeground(Color.red);
			date.setText("Error: " + iae.getMessage());
		}
	}

	public static void main(String[] args) {
		new FlightReservationFrame();

	}

}
