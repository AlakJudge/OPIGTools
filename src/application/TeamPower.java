package application;

public class TeamPower {

	public static int[] Formula(Character currentTeam[][]) {
		int[] total = new int[currentTeam.length];
		int numMembers = Main.numMembers;

		// Calculation of the amount of affiliations in the team.
		for (int x = 0; x < currentTeam.length; x++) {

			double teamPowerPlusBonus = 0;
			double bonus = 1;
			double roundedTotal;
			int teamPower = 0;
			int marinesCounter = 0;
			int wayOfFreedomCounter = 0;
			int swordsmasterCounter = 0;
			int willCounter = 0;
			int pursuitCounter = 0;
			int parameciaCounter = 0;
			int shichibukaiCounter = 0;
			int vinsmokeCounter = 0;
			int piratesShCounter = 0;
			int thrillerBarkCounter = 0;
			int marineShichibukaiCounter = 0;
			int colonelCounter = 0;
			int SHparameciaCounter = 0;
			int skypieaCounter = 0;
			int captainCounter = 0;
			int shichibukaiVinsmokeCounter = 0;
			int piratesRevolutionariesCounter = 0;
			int ShSupernovaCounter = 0;
			int breakthroughCounter = 0;
			int yonkoCounter = 0;
			int cyborgCounter = 0;

			// add a counter for each affiliation each character of the team possesses
			for (int i = 0; i < numMembers; i++) {

				if (currentTeam[x][i].affiliation1 != null && currentTeam[x][i].affiliation1.equals("marines")
						|| currentTeam[x][i].affiliation2 != null && currentTeam[x][i].affiliation2.equals("marines")
						|| currentTeam[x][i].affiliation3 != null && currentTeam[x][i].affiliation3.equals("marines")
						|| currentTeam[x][i].affiliation4 != null && currentTeam[x][i].affiliation4.equals("marines"))
					marinesCounter++;

				if (currentTeam[x][i].affiliation1 != null && currentTeam[x][i].affiliation1.equals("wayOfFreedom")
						|| currentTeam[x][i].affiliation2 != null
								&& currentTeam[x][i].affiliation2.equals("wayOfFreedom")
						|| currentTeam[x][i].affiliation3 != null
								&& currentTeam[x][i].affiliation3.equals("wayOfFreedom")
						|| currentTeam[x][i].affiliation4 != null
								&& currentTeam[x][i].affiliation4.equals("wayOfFreedom"))
					wayOfFreedomCounter++;

				if (currentTeam[x][i].affiliation1 != null && currentTeam[x][i].affiliation1.equals("swordsmaster")
						|| currentTeam[x][i].affiliation2 != null
								&& currentTeam[x][i].affiliation2.equals("swordsmaster")
						|| currentTeam[x][i].affiliation3 != null
								&& currentTeam[x][i].affiliation3.equals("swordsmaster")
						|| currentTeam[x][i].affiliation4 != null
								&& currentTeam[x][i].affiliation4.equals("swordsmaster"))
					swordsmasterCounter++;

				if (currentTeam[x][i].affiliation1 != null && currentTeam[x][i].affiliation1.equals("will")
						|| currentTeam[x][i].affiliation2 != null && currentTeam[x][i].affiliation2.equals("will")
						|| currentTeam[x][i].affiliation3 != null && currentTeam[x][i].affiliation3.equals("will")
						|| currentTeam[x][i].affiliation4 != null && currentTeam[x][i].affiliation4.equals("will"))
					willCounter++;

				if (currentTeam[x][i].affiliation1 != null && currentTeam[x][i].affiliation1.equals("piratesPursuit")
						|| currentTeam[x][i].affiliation2 != null
								&& currentTeam[x][i].affiliation2.equals("piratesPursuit")
						|| currentTeam[x][i].affiliation3 != null
								&& currentTeam[x][i].affiliation3.equals("piratesPursuit")
						|| currentTeam[x][i].affiliation4 != null
								&& currentTeam[x][i].affiliation4.equals("piratesPursuit"))
					pursuitCounter++;

				if (currentTeam[x][i].affiliation1 != null && currentTeam[x][i].affiliation1.equals("paramecia")
						|| currentTeam[x][i].affiliation2 != null && currentTeam[x][i].affiliation2.equals("paramecia")
						|| currentTeam[x][i].affiliation3 != null && currentTeam[x][i].affiliation3.equals("paramecia")
						|| currentTeam[x][i].affiliation4 != null && currentTeam[x][i].affiliation4.equals("paramecia"))
					parameciaCounter++;

				if (currentTeam[x][i].affiliation1 != null && currentTeam[x][i].affiliation1.equals("shichibukai")
						|| currentTeam[x][i].affiliation2 != null
								&& currentTeam[x][i].affiliation2.equals("shichibukai")
						|| currentTeam[x][i].affiliation3 != null
								&& currentTeam[x][i].affiliation3.equals("shichibukai")
						|| currentTeam[x][i].affiliation4 != null
								&& currentTeam[x][i].affiliation4.equals("shichibukai"))
					shichibukaiCounter++;

				if (currentTeam[x][i].affiliation1 != null && currentTeam[x][i].affiliation1.equals("vinsmoke")
						|| currentTeam[x][i].affiliation2 != null && currentTeam[x][i].affiliation2.equals("vinsmoke")
						|| currentTeam[x][i].affiliation3 != null && currentTeam[x][i].affiliation3.equals("vinsmoke")
						|| currentTeam[x][i].affiliation4 != null && currentTeam[x][i].affiliation4.equals("vinsmoke"))
					vinsmokeCounter++;

				if (currentTeam[x][i].affiliation1 != null && currentTeam[x][i].affiliation1.equals("piratesSH")
						|| currentTeam[x][i].affiliation2 != null && currentTeam[x][i].affiliation2.equals("piratesSH")
						|| currentTeam[x][i].affiliation3 != null && currentTeam[x][i].affiliation3.equals("piratesSH")
						|| currentTeam[x][i].affiliation4 != null && currentTeam[x][i].affiliation4.equals("piratesSH"))
					piratesShCounter++;

				if (currentTeam[x][i].affiliation1 != null && currentTeam[x][i].affiliation1.equals("thrillerBark")
						|| currentTeam[x][i].affiliation2 != null
								&& currentTeam[x][i].affiliation2.equals("thrillerBark")
						|| currentTeam[x][i].affiliation3 != null
								&& currentTeam[x][i].affiliation3.equals("thrillerBark")
						|| currentTeam[x][i].affiliation4 != null
								&& currentTeam[x][i].affiliation4.equals("thrillerBark"))
					thrillerBarkCounter++;

				if (currentTeam[x][i].affiliation1 != null && currentTeam[x][i].affiliation1.equals("marineShichibukai")
						|| currentTeam[x][i].affiliation2 != null
								&& currentTeam[x][i].affiliation2.equals("marineShichibukai")
						|| currentTeam[x][i].affiliation3 != null
								&& currentTeam[x][i].affiliation3.equals("marineShichibukai")
						|| currentTeam[x][i].affiliation4 != null
								&& currentTeam[x][i].affiliation4.equals("marineShichibukai"))
					marineShichibukaiCounter++;

				if (currentTeam[x][i].affiliation1 != null && currentTeam[x][i].affiliation1.equals("colonel")
						|| currentTeam[x][i].affiliation2 != null && currentTeam[x][i].affiliation2.equals("colonel")
						|| currentTeam[x][i].affiliation3 != null && currentTeam[x][i].affiliation3.equals("colonel")
						|| currentTeam[x][i].affiliation4 != null && currentTeam[x][i].affiliation4.equals("colonel"))
					colonelCounter++;

				if (currentTeam[x][i].affiliation1 != null && currentTeam[x][i].affiliation1.equals("SHparamecia")
						|| currentTeam[x][i].affiliation2 != null
								&& currentTeam[x][i].affiliation2.equals("SHparamecia")
						|| currentTeam[x][i].affiliation3 != null
								&& currentTeam[x][i].affiliation3.equals("SHparamecia")
						|| currentTeam[x][i].affiliation4 != null
								&& currentTeam[x][i].affiliation4.equals("SHparamecia"))
					SHparameciaCounter++;

				if (currentTeam[x][i].affiliation1 != null && currentTeam[x][i].affiliation1.equals("skypiea")
						|| currentTeam[x][i].affiliation2 != null && currentTeam[x][i].affiliation2.equals("skypiea")
						|| currentTeam[x][i].affiliation3 != null && currentTeam[x][i].affiliation3.equals("skypiea")
						|| currentTeam[x][i].affiliation4 != null && currentTeam[x][i].affiliation4.equals("skypiea"))
					skypieaCounter++;

				if (currentTeam[x][i].affiliation1 != null && currentTeam[x][i].affiliation1.equals("captain")
						|| currentTeam[x][i].affiliation2 != null && currentTeam[x][i].affiliation2.equals("captain")
						|| currentTeam[x][i].affiliation3 != null && currentTeam[x][i].affiliation3.equals("captain")
						|| currentTeam[x][i].affiliation4 != null && currentTeam[x][i].affiliation4.equals("captain"))
					captainCounter++;

				if (currentTeam[x][i].affiliation1 != null
						&& currentTeam[x][i].affiliation1.equals("shichibukaiVinsmoke")
						|| currentTeam[x][i].affiliation2 != null
								&& currentTeam[x][i].affiliation2.equals("shichibukaiVinsmoke")
						|| currentTeam[x][i].affiliation3 != null
								&& currentTeam[x][i].affiliation3.equals("shichibukaiVinsmoke")
						|| currentTeam[x][i].affiliation4 != null
								&& currentTeam[x][i].affiliation4.equals("shichibukaiVinsmoke"))
					shichibukaiVinsmokeCounter++;

				if (currentTeam[x][i].affiliation1 != null
						&& currentTeam[x][i].affiliation1.equals("piratesRevolutionaries")
						|| currentTeam[x][i].affiliation2 != null
								&& currentTeam[x][i].affiliation2.equals("piratesRevolutionaries")
						|| currentTeam[x][i].affiliation3 != null
								&& currentTeam[x][i].affiliation3.equals("piratesRevolutionaries")
						|| currentTeam[x][i].affiliation4 != null
								&& currentTeam[x][i].affiliation4.equals("piratesRevolutionaries"))
					piratesRevolutionariesCounter++;

				if (currentTeam[x][i].affiliation1 != null && currentTeam[x][i].affiliation1.equals("SHsupernova")
						|| currentTeam[x][i].affiliation2 != null
								&& currentTeam[x][i].affiliation2.equals("SHsupernova")
						|| currentTeam[x][i].affiliation3 != null
								&& currentTeam[x][i].affiliation3.equals("SHsupernova")
						|| currentTeam[x][i].affiliation4 != null
								&& currentTeam[x][i].affiliation4.equals("SHsupernova"))
					ShSupernovaCounter++;

				if (currentTeam[x][i].affiliation1 != null && currentTeam[x][i].affiliation1.equals("breakthrough")
						|| currentTeam[x][i].affiliation2 != null
								&& currentTeam[x][i].affiliation2.equals("breakthrough")
						|| currentTeam[x][i].affiliation3 != null
								&& currentTeam[x][i].affiliation3.equals("breakthrough")
						|| currentTeam[x][i].affiliation4 != null
								&& currentTeam[x][i].affiliation4.equals("breakthrough"))
					breakthroughCounter++;

				if (currentTeam[x][i].affiliation1 != null && currentTeam[x][i].affiliation1.equals("yonko")
						|| currentTeam[x][i].affiliation2 != null && currentTeam[x][i].affiliation2.equals("yonko")
						|| currentTeam[x][i].affiliation3 != null && currentTeam[x][i].affiliation3.equals("yonko")
						|| currentTeam[x][i].affiliation4 != null && currentTeam[x][i].affiliation4.equals("yonko"))
					yonkoCounter++;
				
				if (currentTeam[x][i].affiliation1 != null && currentTeam[x][i].affiliation1.equals("cyborg")
						|| currentTeam[x][i].affiliation2 != null && currentTeam[x][i].affiliation2.equals("cyborg")
						|| currentTeam[x][i].affiliation3 != null && currentTeam[x][i].affiliation3.equals("cyborg")
						|| currentTeam[x][i].affiliation4 != null && currentTeam[x][i].affiliation4.equals("cyborg"))
					cyborgCounter++;

				teamPower += currentTeam[x][i].dPower;
			}
			// Calculation of the total bonus
			if (marinesCounter >= 4)
				bonus += 0.72;
			else if (marinesCounter >= 2)
				bonus += 0.36;

			if (wayOfFreedomCounter >= 2)
				bonus += 0.36;

			if (swordsmasterCounter >= 6)
				bonus += 0.87;
			else if (swordsmasterCounter >= 4)
				bonus += 0.58;
			else if (swordsmasterCounter >= 2)
				bonus += 0.29;

			if (willCounter >= 6)
				bonus += 0.48;
			else if (willCounter >= 3)
				bonus += 0.24;

			if (pursuitCounter >= 6)
				bonus += 0.72;
			else if (pursuitCounter >= 4)
				bonus += 0.48;
			else if (pursuitCounter >= 2)
				bonus += 0.24;

			if (parameciaCounter >= 6)
				bonus += 0.72;
			else if (parameciaCounter >= 4)
				bonus += 0.48;
			else if (parameciaCounter >= 2)
				bonus += 0.24;

			if (shichibukaiCounter >= 2)
				bonus += 0.29;

			if (vinsmokeCounter >= 4)
				bonus += 0.48;
			else if (vinsmokeCounter >= 2)
				bonus += 0.24;

			if (piratesShCounter >= 6)
				bonus += 0.72;
			else if (piratesShCounter >= 4)
				bonus += 0.48;
			else if (piratesShCounter >= 2)
				bonus += 0.24;

			if (thrillerBarkCounter >= 6)
				bonus += 0.36;
			else if (thrillerBarkCounter >= 3)
				bonus += 0.18;

			if (marineShichibukaiCounter >= 2)
				bonus += 0.36;

			if (colonelCounter >= 4)
				bonus += 0.58;
			else if (colonelCounter >= 2)
				bonus += 0.29;

			if (SHparameciaCounter >= 4)
				bonus += 0.48;
			else if (SHparameciaCounter >= 2)
				bonus += 0.24;

			if (skypieaCounter >= 1)
				bonus += 0.18;

			if (captainCounter >= 1)
				bonus += 0.18;

			if (shichibukaiVinsmokeCounter >= 6)
				bonus += 0.72;
			else if (shichibukaiVinsmokeCounter >= 4)
				bonus += 0.48;
			else if (shichibukaiVinsmokeCounter >= 2)
				bonus += 0.24;

			if (piratesRevolutionariesCounter >= 2)
				bonus += 0.24;

			if (ShSupernovaCounter >= 6)
				bonus += 0.54;
			else if (ShSupernovaCounter >= 4)
				bonus += 0.36;
			else if (ShSupernovaCounter >= 2)
				bonus += 0.18;

			if (breakthroughCounter >= 4)
				bonus += 0.48;
			else if (breakthroughCounter >= 2)
				bonus += 0.24;

			if (yonkoCounter >= 2)
				bonus += 0.36;
			
			if (cyborgCounter >= 2)
				bonus += 0.24;

			teamPowerPlusBonus = teamPower * bonus;

			// rounding up and removing decimal 0
			roundedTotal = Math.ceil(teamPowerPlusBonus);
			total[x] = (int) roundedTotal;
		}
		return total;
	}
}
