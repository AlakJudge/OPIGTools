package application;

public class Pack {
	String name;
	String type;
	int price;
	String rss1;
	String rss2;
	String rss3;
	int rss1Amount;
	int rss2Amount;
	int rss3Amount;
	
	public Pack(String name, String type, int price, String rss1, int rss1Amount, String rss2, int rss2Amount, String rss3, int rss3Amount) {
		super();
		this.name = name;
		this.type = type;
		this.price = price;
		this.rss1 = rss1;
		this.rss2 = rss2;
		this.rss3 = rss3;
	}
	
	public Pack(String name, String type, int price, String rss1, int rss1Amount, String rss2, int rss2Amount) {
		super();
		this.name = name;
		this.type = type;
		this.price = price;
		this.rss1 = rss1;
		this.rss2 = rss2;
	}
	
	public Pack(String name, String type, int price, String rss1, int rss1Amount) {
		super();
		this.name = name;
		this.type = type;
		this.price = price;
		this.rss1 = rss1;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getRss1() {
		return rss1;
	}

	public void setRss1(String rss1) {
		this.rss1 = rss1;
	}

	public String getRss2() {
		return rss2;
	}

	public void setRss2(String rss2) {
		this.rss2 = rss2;
	}

	public String getRss3() {
		return rss3;
	}

	public void setRss3(String rss3) {
		this.rss3 = rss3;
	}

	public int getRss1Amount() {
		return rss1Amount;
	}

	public void setRss1Amount(int rss1Amount) {
		this.rss1Amount = rss1Amount;
	}

	public int getRss2Amount() {
		return rss2Amount;
	}

	public void setRss2Amount(int rss2Amount) {
		this.rss2Amount = rss2Amount;
	}

	public int getRss3Amount() {
		return rss3Amount;
	}

	public void setRss3Amount(int rss3Amount) {
		this.rss3Amount = rss3Amount;
	}

	@Override
	public String toString() {
		return  "Name: " + name + "\n" +
				"Type: " + type+ "\n" +
				"Price: " + price + "\n" +
				"Resource 1: " + rss1 + "\n" +
				"Resource 2: " + rss2 + "\n" +
				"Resource 3: " + rss3 + "\n";
	}	
}