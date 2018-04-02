package WinLossApp;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;


public class Window
{
	private JButton addWin;
	private JButton addLoss;
	private JButton addTie;
	private JButton minusTie;
	private JButton minusLoss;
	private JButton minusWin;
	private JLabel recordLabel;

	private JFrame frame = new JFrame("Win Loss Tracker");
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem save, load;

	private DataProcessor data;

	Window()
	{
		JPanel panel = new JPanel();
		data = new DataProcessor();

		setUpMenu();
		setUpButtons(panel);

		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		panel.setLayout(new GridLayout(2, 3));

		recordLabel.setOpaque(true);
		recordLabel.setBackground(Color.GREEN);

		frame.add(recordLabel);

		frame.add(panel, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setLocation(frame.getX() - 250, frame.getY() - 100);

		frame.setSize(500, 200);

	}
	private void setUpButtons(JPanel panel)
	{
		ButtonGroup group = new ButtonGroup();

		addWin.addActionListener(new Listener());
		addLoss.addActionListener(new Listener());
		addTie.addActionListener(new Listener());
		minusWin.addActionListener(new Listener());
		minusLoss.addActionListener(new Listener());
		minusTie.addActionListener(new Listener());

		group.add(addWin);
		group.add(addLoss);
		group.add(addTie);
		group.add(addTie);
		group.add(minusTie);
		group.add(minusLoss);
		group.add(minusWin);

		panel.add(addWin, BorderLayout.SOUTH);
		panel.add(addLoss, BorderLayout.SOUTH);
		panel.add(addTie, BorderLayout.SOUTH);
		panel.add(minusWin, BorderLayout.SOUTH);
		panel.add(minusLoss, BorderLayout.SOUTH);
		panel.add(minusTie, BorderLayout.SOUTH);
	}
	private void setUpMenu()
	{
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		JMenuItem reset = new JMenu("Reset");

		save = new JMenuItem("Save");
		load = new JMenuItem("Load");

		menu.add(save);
		menu.add(load);
		menu.add(reset);

		save.addActionListener(e -> {
			SaveManager.save(data);
			System.out.println("Saved");
		});

		load.addActionListener(e -> {
			System.out.println("Loading");
			data = SaveManager.load(data);
			updateRecord();
		});

		((JMenu) reset).addMenuListener(new MenuListener()
		{
			@Override
			public void menuSelected(MenuEvent e)
			{
				System.out.println("Resetting");
				int selection = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset your record?", null, JOptionPane.YES_NO_OPTION);
				if (selection == JOptionPane.NO_OPTION)
				{
					final JMenu menu = (JMenu) e.getSource();
					new Timer(100, e1 -> {
						menu.setSelected(false);
						((Timer) e1.getSource()).stop();
					}).start();
					return;
				}
				data = new DataProcessor();
				updateRecord();
				final JMenu menu = (JMenu) e.getSource();
				new Timer(100, e14 -> {
					menu.setSelected(false);
					((Timer) e14.getSource()).stop();
				}).start();
			}
			@Override
			public void menuDeselected(MenuEvent e)
			{
				final JMenu menu = (JMenu) e.getSource();
				new Timer(100, e13 -> {
					menu.setSelected(false);
					((Timer) e13.getSource()).stop();
				}).start();
			}
			@Override
			public void menuCanceled(MenuEvent e)
			{
				final JMenu menu = (JMenu) e.getSource();
				new Timer(100, e12 -> {
					menu.setSelected(false);
					((Timer) e12.getSource()).stop();
				}).start();

			}
		});

		menuBar.add(menu);
		menuBar.add(reset);

		frame.setJMenuBar(menuBar);
	}

	private void updateRecord()
	{
		DecimalFormat df = new DecimalFormat("###.##");
		float winrate = data.getWinsToGames();
		if (winrate != -1)
			recordLabel.setText("W: " + data.getWins() + " | " + "L: " + data.getLosses() + " | " + "T: " + data.getTies() + "\n" + " Winrate: " + df.format(winrate * 100) + "%");
		else
			recordLabel.setText("W: " + data.getWins() + " | " + "L: " + data.getLosses() + " | " + "T: " + data.getTies());
	}

	void run()
	{
		frame.setVisible(true);
	}

	private class Listener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Object source = e.getSource();

			if (source.equals(addWin)) data.addWin();
			else if (source.equals(addLoss)) data.addLoss();
			else if (source.equals(addTie)) data.addTie();
			else if (source.equals(minusWin)) data.removeWin();
			else if (source.equals(minusLoss)) data.removeLoss();
			else if (source.equals(minusTie)) data.removeTie();

			updateRecord();
		}
	}
}
