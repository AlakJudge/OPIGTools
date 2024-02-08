package application;

import java.util.ArrayList;

public class CheckAffinities {

	public static void run() {
		ArrayList<Character> marines = new ArrayList<>();
		ArrayList<Character> wayOfFreedom = new ArrayList<>();
		ArrayList<Character> swordsmaster = new ArrayList<>();
		ArrayList<Character> will = new ArrayList<>();
		ArrayList<Character> piratesPursuit = new ArrayList<>();
		ArrayList<Character> paramecia = new ArrayList<>();
		ArrayList<Character> shichibukai = new ArrayList<>();
		ArrayList<Character> vinsmoke = new ArrayList<>();
		ArrayList<Character> piratesSH = new ArrayList<>();
		ArrayList<Character> thrillerBark = new ArrayList<>();
		ArrayList<Character> marineShichibukai = new ArrayList<>();
		ArrayList<Character> colonel = new ArrayList<>();
		ArrayList<Character> SHparamecia = new ArrayList<>();
		ArrayList<Character> skypiea = new ArrayList<>();
		ArrayList<Character> captain = new ArrayList<>();
		ArrayList<Character> shichibukaiVinsmoke = new ArrayList<>();
		ArrayList<Character> piratesRevolutionaries = new ArrayList<>();
		ArrayList<Character> SHsupernova = new ArrayList<>();
		ArrayList<Character> breakthrough = new ArrayList<>();
		ArrayList<Character> yonko = new ArrayList<>();
		ArrayList<Character> cyborg = new ArrayList<>();

		for (Character character : Controller.unit) {
			if (character.affiliation1 != null && character.affiliation1.equals("marines")
					|| character.affiliation2 != null && character.affiliation2.equals("marines")
					|| character.affiliation3 != null && character.affiliation3.equals("marines")
					|| character.affiliation4 != null && character.affiliation4.equals("marines")
					|| character.affiliation5 != null && character.affiliation5.equals("marines")) {
				marines.add(character);
			}	
			if (character.affiliation1 != null && character.affiliation1.equals("wayOfFreedom")
					|| character.affiliation2 != null && character.affiliation2.equals("wayOfFreedom")
					|| character.affiliation3 != null && character.affiliation3.equals("wayOfFreedom")
					|| character.affiliation4 != null && character.affiliation4.equals("wayOfFreedom")
					|| character.affiliation5 != null && character.affiliation5.equals("wayOfFreedom")) {
				wayOfFreedom.add(character);
			}	
			if (character.affiliation1 != null && character.affiliation1.equals("swordsmaster")
					|| character.affiliation2 != null && character.affiliation2.equals("swordsmaster")
					|| character.affiliation3 != null && character.affiliation3.equals("swordsmaster")
					|| character.affiliation4 != null && character.affiliation4.equals("swordsmaster")
					|| character.affiliation5 != null && character.affiliation5.equals("swordsmaster")) {
				swordsmaster.add(character);
			}
			if (character.affiliation1 != null && character.affiliation1.equals("will")
					|| character.affiliation2 != null && character.affiliation2.equals("will")
					|| character.affiliation3 != null && character.affiliation3.equals("will")
					|| character.affiliation4 != null && character.affiliation4.equals("will")
					|| character.affiliation5 != null && character.affiliation5.equals("will")) {
				will.add(character);
			}	
			if (character.affiliation1 != null && character.affiliation1.equals("piratesPursuit")
					|| character.affiliation2 != null && character.affiliation2.equals("piratesPursuit")
					|| character.affiliation3 != null && character.affiliation3.equals("piratesPursuit")
					|| character.affiliation4 != null && character.affiliation4.equals("piratesPursuit")
					|| character.affiliation5 != null && character.affiliation5.equals("piratesPursuit")) {
				piratesPursuit.add(character);
			}	
			if (character.affiliation1 != null && character.affiliation1.equals("paramecia")
					|| character.affiliation2 != null && character.affiliation2.equals("paramecia")
					|| character.affiliation3 != null && character.affiliation3.equals("paramecia")
					|| character.affiliation4 != null && character.affiliation4.equals("paramecia")
					|| character.affiliation5 != null && character.affiliation5.equals("paramecia")) {
				paramecia.add(character);
			}
			if (character.affiliation1 != null && character.affiliation1.equals("shichibukai")
					|| character.affiliation2 != null && character.affiliation2.equals("shichibukai")
					|| character.affiliation3 != null && character.affiliation3.equals("shichibukai")
					|| character.affiliation4 != null && character.affiliation4.equals("shichibukai")
					|| character.affiliation5 != null && character.affiliation5.equals("shichibukai")) {
				shichibukai.add(character);
			}	
			if (character.affiliation1 != null && character.affiliation1.equals("vinsmoke")
					|| character.affiliation2 != null && character.affiliation2.equals("vinsmoke")
					|| character.affiliation3 != null && character.affiliation3.equals("vinsmoke")
					|| character.affiliation4 != null && character.affiliation4.equals("vinsmoke")
					|| character.affiliation5 != null && character.affiliation5.equals("vinsmoke")) {
				vinsmoke.add(character);
			}	
			if (character.affiliation1 != null && character.affiliation1.equals("piratesSH")
					|| character.affiliation2 != null && character.affiliation2.equals("piratesSH")
					|| character.affiliation3 != null && character.affiliation3.equals("piratesSH")
					|| character.affiliation4 != null && character.affiliation4.equals("piratesSH")
					|| character.affiliation5 != null && character.affiliation5.equals("piratesSH")) {
				piratesSH.add(character);
			}
			if (character.affiliation1 != null && character.affiliation1.equals("thrillerBark")
					|| character.affiliation2 != null && character.affiliation2.equals("thrillerBark")
					|| character.affiliation3 != null && character.affiliation3.equals("thrillerBark")
					|| character.affiliation4 != null && character.affiliation4.equals("thrillerBark")
					|| character.affiliation5 != null && character.affiliation5.equals("thrillerBark")) {
				thrillerBark.add(character);
			}	
			if (character.affiliation1 != null && character.affiliation1.equals("marineShichibukai")
					|| character.affiliation2 != null && character.affiliation2.equals("marineShichibukai")
					|| character.affiliation3 != null && character.affiliation3.equals("marineShichibukai")
					|| character.affiliation4 != null && character.affiliation4.equals("marineShichibukai")
					|| character.affiliation5 != null && character.affiliation5.equals("marineShichibukai")) {
				marineShichibukai.add(character);
			}	
			if (character.affiliation1 != null && character.affiliation1.equals("colonel")
					|| character.affiliation2 != null && character.affiliation2.equals("colonel")
					|| character.affiliation3 != null && character.affiliation3.equals("colonel")
					|| character.affiliation4 != null && character.affiliation4.equals("colonel")
					|| character.affiliation5 != null && character.affiliation5.equals("colonel")) {
				colonel.add(character);
			}	
			if (character.affiliation1 != null && character.affiliation1.equals("SHparamecia")
					|| character.affiliation2 != null && character.affiliation2.equals("SHparamecia")
					|| character.affiliation3 != null && character.affiliation3.equals("SHparamecia")
					|| character.affiliation4 != null && character.affiliation4.equals("SHparamecia")
					|| character.affiliation5 != null && character.affiliation5.equals("SHparamecia")) {
				SHparamecia.add(character);
			}			
			if (character.affiliation1 != null && character.affiliation1.equals("skypiea")
					|| character.affiliation2 != null && character.affiliation2.equals("skypiea")
					|| character.affiliation3 != null && character.affiliation3.equals("skypiea")
					|| character.affiliation4 != null && character.affiliation4.equals("skypiea")
					|| character.affiliation5 != null && character.affiliation5.equals("skypiea")) {
				skypiea.add(character);
			}	
			if (character.affiliation1 != null && character.affiliation1.equals("captain")
					|| character.affiliation2 != null && character.affiliation2.equals("captain")
					|| character.affiliation3 != null && character.affiliation3.equals("captain")
					|| character.affiliation4 != null && character.affiliation4.equals("captain")
					|| character.affiliation5 != null && character.affiliation5.equals("captain")) {
				captain.add(character);
			}
			if (character.affiliation1 != null && character.affiliation1.equals("shichibukaiVinsmoke")
					|| character.affiliation2 != null && character.affiliation2.equals("shichibukaiVinsmoke")
					|| character.affiliation3 != null && character.affiliation3.equals("shichibukaiVinsmoke")
					|| character.affiliation4 != null && character.affiliation4.equals("shichibukaiVinsmoke")
					|| character.affiliation5 != null && character.affiliation5.equals("shichibukaiVinsmoke")) {
				shichibukaiVinsmoke.add(character);
			}	
			if (character.affiliation1 != null && character.affiliation1.equals("piratesRevolutionaries")
					|| character.affiliation2 != null && character.affiliation2.equals("piratesRevolutionaries")
					|| character.affiliation3 != null && character.affiliation3.equals("piratesRevolutionaries")
					|| character.affiliation4 != null && character.affiliation4.equals("piratesRevolutionaries")
					|| character.affiliation5 != null && character.affiliation5.equals("piratesRevolutionaries")) {
				piratesRevolutionaries.add(character);
			}	
			if (character.affiliation1 != null && character.affiliation1.equals("SHsupernova")
					|| character.affiliation2 != null && character.affiliation2.equals("SHsupernova")
					|| character.affiliation3 != null && character.affiliation3.equals("SHsupernova")
					|| character.affiliation4 != null && character.affiliation4.equals("SHsupernova")
					|| character.affiliation5 != null && character.affiliation5.equals("SHsupernova")) {
				SHsupernova.add(character);
			}
			if (character.affiliation1 != null && character.affiliation1.equals("breakthrough")
					|| character.affiliation2 != null && character.affiliation2.equals("breakthrough")
					|| character.affiliation3 != null && character.affiliation3.equals("breakthrough")
					|| character.affiliation4 != null && character.affiliation4.equals("breakthrough")
					|| character.affiliation5 != null && character.affiliation5.equals("breakthrough")) {
				breakthrough.add(character);
			}	
			if (character.affiliation1 != null && character.affiliation1.equals("yonko")
					|| character.affiliation2 != null && character.affiliation2.equals("yonko")
					|| character.affiliation3 != null && character.affiliation3.equals("yonko")
					|| character.affiliation4 != null && character.affiliation4.equals("yonko")
					|| character.affiliation5 != null && character.affiliation5.equals("yonko")) {
				yonko.add(character);
			}	
			if (character.affiliation1 != null && character.affiliation1.equals("cyborg")
					|| character.affiliation2 != null && character.affiliation2.equals("cyborg")
					|| character.affiliation3 != null && character.affiliation3.equals("cyborg")
					|| character.affiliation4 != null && character.affiliation4.equals("cyborg")
					|| character.affiliation5 != null && character.affiliation5.equals("cyborg")) {
				cyborg.add(character);
			}
		}
		System.out.println("\nMARINES | PIRATES:");
		for (int i = 0; i < marines.size(); i++) {		
			System.out.println(marines.get(i).getName());
		}
		System.out.println("\nWAY TO FREEDOM:");
		for (int i = 0; i < wayOfFreedom.size(); i++) {		
			System.out.println(wayOfFreedom.get(i).getName());
		}
		System.out.println("\nSWORDSMATER | PIRATES:");
		for (int i = 0; i < swordsmaster.size(); i++) {		
			System.out.println(swordsmaster.get(i).getName());
		}
		System.out.println("\nWILL AND SUCCESSION:");
		for (int i = 0; i < will.size(); i++) {		
			System.out.println(will.get(i).getName());
		}
		System.out.println("\nPIRATE PURSUIT:");
		for (int i = 0; i < piratesPursuit.size(); i++) {		
			System.out.println(piratesPursuit.get(i).getName());
		}
		System.out.println("\nPARAMECIA:");
		for (int i = 0; i < paramecia.size(); i++) {		
			System.out.println(paramecia.get(i).getName());
		}
		System.out.println("\nOKA SHICHIBUKAI:");
		for (int i = 0; i < shichibukai.size(); i++) {		
			System.out.println(shichibukai.get(i).getName());
		}
		System.out.println("\nVINSMOKE FAMILY:");
		for (int i = 0; i < vinsmoke.size(); i++) {		
			System.out.println(vinsmoke.get(i).getName());
		}
		System.out.println("\nPIRATES AND STRAWHAT ALLIANCE:");
		for (int i = 0; i < piratesSH.size(); i++) {		
			System.out.println(piratesSH.get(i).getName());
		}
		System.out.println("\nTHRILLER BARK:");
		for (int i = 0; i < thrillerBark.size(); i++) {		
			System.out.println(thrillerBark.get(i).getName());
		}
		System.out.println("\nMARINE AND SHICHIBUKAI:");
		for (int i = 0; i < marineShichibukai.size(); i++) {		
			System.out.println(marineShichibukai.get(i).getName());
		}
		System.out.println("\nCOLONEL GENERAL ATTACK:");
		for (int i = 0; i < colonel.size(); i++) {		
			System.out.println(colonel.get(i).getName());
		}
		System.out.println("\nSTRAWHAT ALLIANCE | PARAMECIA:");
		for (int i = 0; i < SHparamecia.size(); i++) {		
			System.out.println(SHparamecia.get(i).getName());
		}
		System.out.println("\nSKYPIEA DUEL:");
		for (int i = 0; i < skypiea.size(); i++) {		
			System.out.println(skypiea.get(i).getName());
		}
		System.out.println("\nCAPTAIN:");
		for (int i = 0; i < captain.size(); i++) {		
			System.out.println(captain.get(i).getName());
		}
		System.out.println("\nOKA SHICHIBUKAI AND VINSMOKE FAMILY:");
		for (int i = 0; i < shichibukaiVinsmoke.size(); i++) {		
			System.out.println(shichibukaiVinsmoke.get(i).getName());
		}
		System.out.println("\nPIRATES AND REVOLUTIONARY ARMY:");
		for (int i = 0; i < piratesRevolutionaries.size(); i++) {		
			System.out.println(piratesRevolutionaries.get(i).getName());
		}
		System.out.println("\nSTRAWHAT ALLIANCE | SUPERNOVA:");
		for (int i = 0; i < SHsupernova.size(); i++) {		
			System.out.println(SHsupernova.get(i).getName());
		}
		System.out.println("\nCYBORG | PARAMECIA:");
		for (int i = 0; i < cyborg.size(); i++) {		
			System.out.println(cyborg.get(i).getName());
		}
		System.out.println("\nMY BREAKTHROUGH:");
		for (int i = 0; i < breakthrough.size(); i++) {		
			System.out.println(breakthrough.get(i).getName());
		}
		System.out.println("\nYONKO AND FIRST MATE:");
		for (int i = 0; i < yonko.size(); i++) {		
			System.out.println(yonko.get(i).getName());
		}
	}
}
