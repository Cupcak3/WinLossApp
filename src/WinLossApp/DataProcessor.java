package WinLossApp;

class DataProcessor
{
	private int wins, losses, ties, games;

	DataProcessor()
	{
		wins = losses = ties = games = 0;
	}

	void addWin()
	{
		wins++;
		games++;
	}

	void addLoss()
	{
		losses++;
		games++;
	}

	void addTie()
	{
		ties++;
		games++;
	}

	void removeWin()
	{
		if (wins == 0) return;
		wins--;
		games--;
	}

	void removeLoss()
	{
		if (losses == 0) return;
		losses--;
		games--;
	}

	void removeTie()
	{
		if (ties == 0) return;
		ties--;
		games--;
	}

	int getWins()
	{
		return wins;
	}
	int getLosses()
	{
		return losses;
	}
	int getTies()
	{
		return ties;
	}

	float getWinsToGames()
	{
		if (games == 0) return -1;
		return (float) wins / games;
	}
}