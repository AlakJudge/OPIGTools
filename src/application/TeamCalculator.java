package application;

import java.util.ArrayList;
import java.util.List;

public class TeamCalculator {
	static int numMembers = Main.numMembers;
	static int teams = 2;
	static int highestPower = 0;
	static int highestPowerTeamIndex = 0; // variable that will store the index for the team with the highest power
	static ArrayList<List<Character>> allTeams = new ArrayList<>();
	static Character[][] listOfTeams;
	static Character[][] tempTeams;
	static Character[][] bestTeam;
	static int topTeamsPowers[] = new int[teams];
	static int totalNumberOfTeamsCounter = 0;

	static int[] teamsPower = new int[teams];
	static int lowest = 0;

	public static void compare(ArrayList<Character> characterList) {

		// making sure variables are renewed correctly in each calculation
		numMembers = Main.numMembers;
		listOfTeams = new Character[teams][numMembers];
		tempTeams = new Character[teams][numMembers];
		bestTeam = new Character[teams][numMembers];
		List<Character> currentTeam = new ArrayList<>();

		// find all the team combinations
		generateTeams(0, currentTeam, characterList);

	}

	private static void generateTeams(int startIndex, List<Character> currentTeam, ArrayList<Character> characterList) {

		// only start the calculation once we have enough teams for comparison. Possibly
		// to be used for future features too
		if (allTeams.size() >= teams) {
			calculate();
		}

		if (currentTeam.size() == numMembers) {
			allTeams.add(new ArrayList<>(currentTeam)); // Found a valid group
			totalNumberOfTeamsCounter++; // number of teams founds, might be used in progress bar change.
			return;
		}
		// add characters to current team, with different variations until all options
		// are exhausted
		for (int i = startIndex; i < characterList.size(); i++) {
			currentTeam.add(characterList.get(i));
			generateTeams(i + 1, currentTeam, characterList);
			currentTeam.remove(currentTeam.size() - 1); // Backtrack
		}
	}

	private static void calculate() {
		findTeamsPower();
		// find the lowest power currently saved
		for (int j = 0; j < teams; j++) {
			if (topTeamsPowers[j] < topTeamsPowers[lowest]) {
				lowest = j;
			}

			// save the actual characters in the saved teams, before the removing/adding
			for (int i = 0; i < numMembers; i++) {
				tempTeams[j][i] = listOfTeams[j][i];
			}
		}

		for (int i = 0; i < teams; i++) {
			// replacing the current lowest with the new top power teams
			for (int j = 0; j < teams; j++) {
				if (teamsPower[i] > topTeamsPowers[lowest]) {
					for (int x = 0; x < numMembers; x++) {
						tempTeams[lowest][x] = listOfTeams[i][x];
					}

					// tempTeams[lowest] = listOfTeams[i];
					topTeamsPowers[lowest] = teamsPower[i];
				}
			}
			// find the lowest power currently saved
			for (int j = 0; j < teams; j++) {
				if (topTeamsPowers[j] < topTeamsPowers[lowest]) {
					lowest = j;
				}
			}
		}

		for (int i = 0; i < teams; i++) {
			// compare the powers of all teams and save the total number + its index (in
			// order to find the chars)
			if (topTeamsPowers[i] > highestPower) {
				highestPower = topTeamsPowers[i];
				highestPowerTeamIndex = i;
				for (int j = 0; j < numMembers; j++) {
					bestTeam[0][j] = tempTeams[highestPowerTeamIndex][j];
				}
			}
		}
	}

	public static void findTeamsPower() {
		// converting from the allTeams arraylist to the listOfTeams Character object
		for (int x = 0; x < allTeams.size(); x++) {
			for (int i = 0; i < numMembers; i++) {
				listOfTeams[x][i] = allTeams.get(x).get(i);
			}
		}
		// making sure you're only ever comparing a couple of teams at a time
		allTeams.clear();
		// find the teams' power
		teamsPower = TeamPower.Formula(listOfTeams);
	}

	// reset global variables for a new team
	public static void resetBest() {
		highestPower = 0;
		highestPowerTeamIndex = 0;
		allTeams = new ArrayList<>();
		topTeamsPowers = new int[teams];
		teamsPower = new int[teams];
		lowest = 0;
		totalNumberOfTeamsCounter = 0;
	}
}