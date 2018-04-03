package WinLossApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;


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

	private DataProcessor data;

	private boolean showWinrate;

	Window()
	{
		JPanel panel = new JPanel();
		data = new DataProcessor();

		setUpFrame(panel);
		setUpMenu();
		setUpButtons(panel);

	}
	private void setUpFrame(JPanel panel)
	{
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null);
		frame.setLocation(frame.getX() - 250, frame.getY() - 100);

		try
		{
			ImageIcon icon = new ImageIcon(this.getClass().getResource("resources/owo1.jpg"));
			frame.setIconImage(icon.getImage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		frame.add(recordLabel);
		frame.add(panel, BorderLayout.SOUTH);

		panel.setLayout(new GridLayout(2, 3));

		recordLabel.setOpaque(true);
		recordLabel.setBackground(Color.GREEN);

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
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu options = new JMenu("Option");

		JMenuItem save = new JMenuItem("Save");
		JMenuItem load = new JMenuItem("Load");
		JMenuItem reset = new JMenuItem("Reset");

		JCheckBoxMenuItem winrateCheck = new JCheckBoxMenuItem("Show Winrate", false);

		file.add(save);
		file.add(load);
		file.add(reset);

		options.add(winrateCheck);

		save.addActionListener(e -> {
			frame.setVisible(false);
			SaveManager.save(data);
			Timer timer = new Timer();
			timer.schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					frame.setVisible(true);
				}
			}, 200);

		});

		load.addActionListener(e -> {
			frame.setVisible(false);
			data = SaveManager.load(data);
			updateRecord();
			frame.setVisible(true);
		});

		reset.addActionListener(e -> {
			int selection = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset your record?", null,
					JOptionPane.YES_NO_OPTION);
			if (selection != JOptionPane.NO_OPTION)
			{
				data = new DataProcessor();
				updateRecord();
			}
		});

		winrateCheck.addActionListener(e -> {
			showWinrate = winrateCheck.getState();
			updateRecord();
		});

		menuBar.add(file);
		menuBar.add(options);

		frame.setJMenuBar(menuBar);
	}

	private void updateRecord()
	{
		DecimalFormat df = new DecimalFormat("###.##");
		float winrate = data.getWinsToGames();
		if (showWinrate) recordLabel.setText(winrate != -1 ?
				"W: " + data.getWins() + " | " + "L: " + data.getLosses() + " | " + "T: " + data.getTies() + "\n" +
						" Winrate: " + df.format(winrate * 100) + "%" :
				"W: " + data.getWins() + " | " + "L: " + data.getLosses() + " | " + "T: " + data.getTies() +
						" Winrate: 0%");
		else recordLabel
				.setText("W: " + data.getWins() + " | " + "L: " + data.getLosses() + " | " + "T: " + data.getTies());
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
