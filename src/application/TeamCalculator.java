package application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TeamCalculator {
	static int numMembers = Main.numMembers;
	static int teams = 2;
	static int totalBestTeams = 100;
	static int[] BestTeamPower = new int[totalBestTeams];
	static int BestTeamPowerTeamIndex = 0; // variable that will store the index for the team with the highest power
	static int bestTeamCounter = 0; 
	static ArrayList<List<Character>> allTeams = new ArrayList<>();
	static ArrayList<Character> bestTeamList = new ArrayList<Character>();
	static Set<Character>[] bestTeamSet= new HashSet[totalBestTeams];
	static Character[][] listOfTeams;
	static Character[][] tempTeams;
	static Character[][] bestTeam = new Character[totalBestTeams][numMembers];
	static int topTeamsPowers[] = new int[teams];

	static int[] teamsPower = new int[teams];
	static int lowest = 0;
	static int highest = 0;
	static int missionPowerHigh = 0;
	static boolean done = false;
	static boolean sameTeam = false;
	
	public static void compare(ArrayList<Character> characterList) {

		// making sure variables are renewed correctly in each calculation
		numMembers = Main.numMembers;	
		listOfTeams = new Character[teams][numMembers];
		tempTeams = new Character[teams][numMembers];
		List<Character> currentTeam = new ArrayList<>();	
		
		// find all the team combinations
		generateTeams(0, currentTeam, characterList);
		bestTeamCounter++;
		done = false;	
	}

	private static void generateTeams(int startIndex, List<Character> currentTeam, ArrayList<Character> characterList) {
	
		// sort characters by alphabetical order
		//Collections.sort(characterList, Comparator.comparing(Character::getName));

		// only start the calculation once we have enough teams for comparison. 
		// Possibly to be used for future features too		
		if (allTeams.size() >= teams) {
			calculate();		
		}
		
		if (currentTeam.size() == numMembers) {
			    					
			// Use a custom comparator to sort by character names
	       if (bestTeamCounter > 0) {
	        	
	    		for (Character character : bestTeam[bestTeamCounter-1]) {
	    			bestTeamList.add(character);
	    		}
	    		
	    		bestTeamSet[bestTeamCounter-1] = new HashSet<>(bestTeamList); 
	    		Set<Character> currentTeamSet = new HashSet<>(currentTeam);	    				
	    		
	    		boolean setsAreEqual = false;//= currentTeamSet.equals(bestTeamSet[bestTeamCounter-1]);
	    		
	    		for (Set<Character> set : bestTeamSet) {
	    		    if (currentTeamSet.equals(set)) {
	    		    	setsAreEqual = true;
	    		        break;  // No need to continue checking once a match is found
	    		    }
	    		}
	    		
	    		// Only add current team if it's not the same as other best teams
	    		if (!setsAreEqual)
	    			allTeams.add(new ArrayList<>(currentTeam)); 	    		
	        }
	       else
	    	   allTeams.add(new ArrayList<>(currentTeam)); // Found a valid group
	    	   
	       bestTeamList.clear();
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
							// if team within 100 power is found, stop calculating.
							done = true;
							BestTeamPower[bestTeamCounter] = teamsPower[i];
							for (int j = 0; j < numMembers; j++) {
								bestTeam[bestTeamCounter][j] = listOfTeams[i][j];
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
			if (topTeamsPowers[i] > BestTeamPower[bestTeamCounter]) {
				BestTeamPower[bestTeamCounter] = topTeamsPowers[i];
				BestTeamPowerTeamIndex = i;
				for (int j = 0; j < numMembers; j++) {
					bestTeam[bestTeamCounter][j] = tempTeams[BestTeamPowerTeamIndex][j];
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
		BestTeamPowerTeamIndex = 0;
		allTeams = new ArrayList<>();
		topTeamsPowers = new int[teams];
		teamsPower = new int[teams];
		lowest = 0;		
	}
	
	// reset all, including the list of best teams
	public static void resetAllBest() {
		BestTeamPowerTeamIndex = 0;
		allTeams = new ArrayList<>();
		topTeamsPowers = new int[teams];
		teamsPower = new int[teams];
		lowest = 0;	
		BestTeamPower = new int[totalBestTeams];
		bestTeamList = new ArrayList<Character>();
		bestTeam = new Character[totalBestTeams][numMembers];
		bestTeamSet= new HashSet[totalBestTeams];
		bestTeamCounter = 0;
	}
}