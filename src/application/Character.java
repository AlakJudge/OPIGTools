package application;

//Constructor for the characters
public class Character {

	String name;
	int defaultPower;
	String affiliation1;
	String affiliation2;
	String affiliation3;
	String affiliation4;
	int dPower;
	String type;
	int hp;
	int atk;
	int def;
	int speed;

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Integer getDefaultPower() {
		return defaultPower;
	}

	public void setDefaultPower(int defaultPower) {
		this.defaultPower = defaultPower;
	}

	public int getdPower() {
		return dPower;
	}

	public void setdPower(int dPower) {
		this.dPower = dPower;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAffiliation1() {
		return affiliation1;
	}

	public void setAffiliation1(String affiliation1) {
		this.affiliation1 = affiliation1;
	}

	public String getAffiliation2() {
		return affiliation2;
	}

	public void setAffiliation2(String affiliation2) {
		this.affiliation2 = affiliation2;
	}

	public String getAffiliation3() {
		return affiliation3;
	}

	public void setAffiliation3(String affiliation3) {
		this.affiliation3 = affiliation3;
	}

	public String getAffiliation4() {
		return affiliation4;
	}

	public void setAffiliation4(String affiliation4) {
		this.affiliation4 = affiliation4;
	}

	//overloaded constructors for the characters with their name, default power, type (rarity), stats, and affiliations. 
	
	Character(String name, String type, int defaultPower, int hp, int atk, int def, int speed) {

		this.setName(name);
		this.setDefaultPower(defaultPower);
		this.setType(type);
		this.setHp(hp);
		this.setAtk(atk);
		this.setDef(def);
		this.setSpeed(speed);
	}

	Character(String name, String type, int defaultPower, String affiliation, int hp, int atk, int def, int speed) {

		this.setName(name);
		this.setDefaultPower(defaultPower);
		this.setType(type);
		this.setAffiliation1(affiliation);
		this.setHp(hp);
		this.setAtk(atk);
		this.setDef(def);
		this.setSpeed(speed);

	}

	Character(String name, String type, int defaultPower, String affiliation1, String affiliation2, int hp, int atk,
			int def, int speed) {

		this.setName(name);
		this.setDefaultPower(defaultPower);
		this.setType(type);
		this.setAffiliation1(affiliation1);
		this.setAffiliation2(affiliation2);
		this.setHp(hp);
		this.setAtk(atk);
		this.setDef(def);
		this.setSpeed(speed);

	}

	Character(String name, String type, int defaultPower, String affiliation1, String affiliation2, String affiliation3,
			int hp, int atk, int def, int speed) {

		this.setName(name);
		this.setDefaultPower(defaultPower);
		this.setType(type);
		this.setAffiliation1(affiliation1);
		this.setAffiliation2(affiliation2);
		this.setAffiliation3(affiliation3);
		this.setHp(hp);
		this.setAtk(atk);
		this.setDef(def);
		this.setSpeed(speed);
	}

	Character(String name, String type, int defaultPower, String affiliation1, String affiliation2, String affiliation3,
			String affiliation4, int hp, int atk, int def, int speed) {

		this.setName(name);
		this.setDefaultPower(defaultPower);
		this.setType(type);
		this.setAffiliation1(affiliation1);
		this.setAffiliation2(affiliation2);
		this.setAffiliation3(affiliation3);
		this.setAffiliation4(affiliation4);
		this.setHp(hp);
		this.setAtk(atk);
		this.setDef(def);
		this.setSpeed(speed);
	}
}