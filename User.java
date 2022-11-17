public class User {
	String seatNum;
	String seatClass;
	String isWindow;
	String isAisle;
	String isTable;
	Double seatPrice;
	String eMail;
		
	User(String seatNum, String seatClass, String isWindow, String isAisle, String isTable, Double seatPrice, String eMail)
		{
			this.seatNum = seatNum;
			this.seatClass = seatClass;
			this.isWindow = isWindow;
			this.isAisle = isAisle;
			this.isTable = isTable;
			this.seatPrice = seatPrice;
			this.eMail = eMail;
		}
}