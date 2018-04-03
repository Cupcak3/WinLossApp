package WinLossApp;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class SaveManager
{
	SaveManager()
	{
	}

	static void save(DataProcessor data)
	{
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;

		JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir")));
		SimpleDateFormat date = new SimpleDateFormat("E_MM_dd_yyyy");
		chooser.setSelectedFile(new File(new File(date.format(new Date())) + ".save"));
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

		try
		{
			int retrieval = chooser.showOpenDialog(null);
			File file = chooser.getSelectedFile();
			if (retrieval == JFileChooser.CANCEL_OPTION) return;

			while (!file.getName().substring(file.getName().length() - 5).equals(".save"))
			{
				JOptionPane.showMessageDialog(null, "Please use .save for file extension");
				retrieval = chooser.showOpenDialog(null);
				if (retrieval == JFileChooser.CANCEL_OPTION) return;
				file = chooser.getSelectedFile();
			}

			if (retrieval == JFileChooser.APPROVE_OPTION)
			{
				System.out.println("File: " + file.getPath());
				fout = new FileOutputStream(file.getPath());
				oos = new ObjectOutputStream(fout);
				oos.writeObject(data);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (fout != null) try
			{
				fout.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			if (oos != null) try
			{
				oos.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	static DataProcessor load(DataProcessor data)
	{
		JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir")));
		FileInputStream fin = null;
		ObjectInput ois = null;
		DataProcessor newData;
		try
		{
			int retrieval = chooser.showSaveDialog(null);
			File file = chooser.getSelectedFile();
			if (retrieval == JFileChooser.CANCEL_OPTION) return data;

			while (!file.getName().substring(file.getName().length() - 5).equals(".save"))
			{
				JOptionPane.showMessageDialog(null, "Please use .save for file extension");
				retrieval = chooser.showOpenDialog(null);
				if (retrieval == JFileChooser.CANCEL_OPTION) return data;
				file = chooser.getSelectedFile();
			}

			fin = new FileInputStream(file.getPath());
			ois = new ObjectInputStream(fin);

			newData = (DataProcessor) ois.readObject();

		}
		catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Couldn't load file correctly. Data might be corrupted");
			return data;
		}
		finally
		{
			if (fin != null)
			{
				try
				{
					fin.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}

			if (ois != null)
			{
				try
				{
					ois.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return newData;
	}
}
