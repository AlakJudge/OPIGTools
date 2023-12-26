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

	static int[] teamsPower = new int[teams];
	static int lowest = 0;
	static int highest = 0;
	static int missionPowerHigh = 0;
	static boolean done = false;

	public static void compare(ArrayList<Character> characterList) {

		// making sure variables are renewed correctly in each calculation
		numMembers = Main.numMembers;
		listOfTeams = new Character[teams][numMembers];
		tempTeams = new Character[teams][numMembers];
		bestTeam = new Character[teams][numMembers];
		List<Character> currentTeam = new ArrayList<>();
		
		// find all the team combinations
		generateTeams(0, currentTeam, characterList);
		done = false;

	}

	private static void generateTeams(int startIndex, List<Character> currentTeam, ArrayList<Character> characterList) {

		// only start the calculation once we have enough teams for comparison. 
		// Possibly to be used for future features too
		if (allTeams.size() >= teams) {
			calculate();		
		}

		if (currentTeam.size() == numMembers) {
			allTeams.add(new ArrayList<>(currentTeam)); // Found a valid group
			return;
		}
		
		// add characters to current team, with different variations until all options
		// are exhausted
		for (int i = startIndex; i < characterList.size(); i++) {
			if (done) {
				break;
			} 
			currentTeam.add(characterList.get(i));
			generateTeams(i + 1, currentTeam, characterList);
			currentTeam.remove(currentTeam.size() - 1); // Backtrack
		}
	}

	private static void calculate() {
		findTeamsPower();
		
		// find the team with the closest power to target mission power	
		if (Controller.missionPower != 0) {
			for (int i = 0; i < teams; i++) {	
				if (teamsPower[i] >= Controller.missionPower) {
					for (int x = 1; x < 10; x++) { // find the closest 100 by 100					
						missionPowerHigh = Controller.missionPower+(x*100);
						if(teamsPower[i] < missionPowerHigh) {					
							done = true;
							highestPower = teamsPower[i];
							for (int j = 0; j < numMembers; j++) {
								bestTeam[0][j] = listOfTeams[i][j];
							}			
						break;
						}				
					}
				}
			}
		}		
		if (done)
			return;			
		
		for (int j = 0; j < teams; j++) {				
			// find the highest and lowest indexes for powers currently saved
			if (topTeamsPowers[j] < topTeamsPowers[lowest]) {
				lowest = j;	
			}
			// save the actual characters in the saved teams, before the removing/adding
			for (int i = 0; i < numMembers; i++) {
				tempTeams[j][i] = listOfTeams[j][i];
			}
		}

		for (int i = 0; i < teams; i++) {
			// replacing the current lowest power team
			if (teamsPower[i] >= topTeamsPowers[lowest]) {
				for (int x = 0; x < numMembers; x++) {
					tempTeams[lowest][x] = listOfTeams[i][x];
				}
				topTeamsPowers[lowest] = teamsPower[i];				
			}
		}
		
		for (int i = 0; i < teams; i++) {
			// compare the powers of both teams and save the total number + its index (in order to find the chars)
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
	}
}