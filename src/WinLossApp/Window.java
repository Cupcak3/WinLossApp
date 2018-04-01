package WinLossApp;

import javax.swing.*;
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

	private JFrame frame = new JFrame("Overloss");
	private DataProcessor data;
	Window()
	{
		data = new DataProcessor();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 3));

		ButtonGroup group = new ButtonGroup();

		addWin.addActionListener(new ButtonListener());
		addLoss.addActionListener(new ButtonListener());
		addTie.addActionListener(new ButtonListener());
		minusWin.addActionListener(new ButtonListener());
		minusLoss.addActionListener(new ButtonListener());
		minusTie.addActionListener(new ButtonListener());

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

		recordLabel.setOpaque(true);
		recordLabel.setBackground(Color.GREEN);

		frame.add(recordLabel);

		frame.add(panel, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setLocation(frame.getX() - 150, frame.getY() - 100);

		frame.setSize(500, 200);

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

	private class ButtonListener implements ActionListener
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
