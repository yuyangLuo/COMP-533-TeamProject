package DataBase;


import models.*;

public class Querys {
	protected final static String CheckUser=
			"SELECT count(*) FROM login_info WHERE (email_address='%s');" ;
	protected static String checkUser(String email){
		return String.format(CheckUser, email);
	}
	protected final static String Register = "SELECT register('%S',%S,'%S','%s','%s');";

	protected static String register(UserInfo user, LoginInfo login){
		return String.format(Register, user.getFirstName(),user.getMiddleName()==null?"NULL":String.format("'%s'", user.getMiddleName()),user.getLastName(),login.getUserName(),login.getPassWord());
	}
	
	protected final static String UpdateUinfo=
			"UPDATE customer SET first_name = '%s',middle_name= %s ,last_name='%s',home_airport='%s' WHERE user_id=%d";
	protected static String updateUinfo(UserInfo user){
		return String.format(UpdateUinfo, user.getFirstName(),user.getMiddleName()==null?"NULL":String.format("'%s'", user.getMiddleName()),user.getLastName(),user.getHomeIATA(),user.getUid());
	}
	
	protected final static String UpdateLinfo=
			"UPDATE login_info SET password='%s' WHERE user_id = %d AND password='%s'";
	protected static String updateLinfo(String password,String oldpass, int uid){
		return String.format(UpdateLinfo, password,uid,oldpass);
	}
	
	protected final static String UserInfo=
			"SELECT * FROM customer \n"+
			"WHERE user_id = (SELECT user_id FROM login_info WHERE email_address = '%s' AND password='%s')";
	protected static String userInfo(LoginInfo info){
		return String.format(UserInfo, info.getUserName(),info.getPassWord());
	}
	
	protected final static String UserAddress=
			"SELECT * FROM address WHERE user_id = %d ORDER BY address_id";
	protected static String userAddress(int uid){
		return String.format(UserAddress, uid);
	}
	
	protected final static String InsertAddress = 
			"INSERT INTO address VALUES(DEFAULT,'%s','%s','%s',%s,'%s','%s','%s',%d);";
	protected static String insertAddress(Address addr,int uid){
		return String.format(InsertAddress,
				addr.getStreet(),
				addr.getDeptNum(),
				addr.getCity(),
				addr.getState()==null?"NULL":String.format("'%s'", addr.getState()),
				addr.getZipCode(),
				addr.getCountry(),
				addr.getPhoneNumber(),
				uid);
	}
	protected final static String UpdateAddress = 
			"UPDATE address SET phone_num='%s',zipcode='%s',street='%s', dept_num = '%s',City = '%s', state = %s, country= '%s' WHERE address_id=%d AND user_id =%d";
	protected static String updateAddress(Address addr, int uid){
		return String.format(UpdateAddress, 
				addr.getPhoneNumber(),
				addr.getZipCode(),
				addr.getStreet(),
				addr.getDeptNum(),
				addr.getCity(),
				addr.getState()==null?"NULL":String.format("'%s'", addr.getState()),
				addr.getCountry(),
				addr.getAddrID(),
				uid);
	}
	
	protected final static String DeleteAddress =
			"DELETE FROM address WHERE user_id=%d AND address_id=%d ";
	protected static String deleteAddress(int uid, int aid){
		return String.format(DeleteAddress, uid,aid);
	}
	
	
	protected final static String UserPayMethod=
			"SELECT * FROM credit_card WHERE user_id=%d";
	protected static String userPayMethod(int uid){
		return String.format(UserPayMethod, uid);
	}
	protected final static String InsertPayMethod= 
			"INSERT INTO credit_card VALUES(%d,'%s',%d,'%s',%d,%d)";
	protected static String insertPayMethod(CreditCard card){
		return String.format(InsertPayMethod, card.getUid(),card.getCardNumber(),card.getExpirationDate(),card.getCardHolder(),card.getCvv(),card.getBilliing_address());
	}
	protected final static String UpdateCard = 
			"UPDATE credit_card SET expiration_date = '%d', card_holder = '%s', cvv= %d, billing_address=%d, card_number='%s' WHERE user_id =%d AND card_number='%s'";
	protected static String updateCard (CreditCard old, CreditCard card){
		return String.format(UpdateCard, card.getExpirationDate(),card.getCardHolder(),card.getCvv(),card.getBilliing_address(),card.getCardNumber(),card.getUid(),old.getCardNumber());
	}
	protected final static String DeleteCard = 
			"DELETE FROM credit_card CASECADE WHERE user_id=%d AND card_number='%s'";
	protected static String deleteCard(CreditCard card){
		return String.format(DeleteCard, card.getUid(),card.getCardNumber());
	}
	
	protected final static String UserBooking = 
			"SELECT * FROM booking WHERE user_id = %d";
	protected static String userBooking(int uid){
		return String.format(UserBooking, uid);
	}
	protected final static String DeleteBooking = 
			"DELETE FROM booking where user_id = %d AND order_num = %d";
	protected static String deleteBooking (int uid,int onum){
		return String.format(DeleteBooking, uid,onum);
	}
	protected final static String InsertBooking = 
			"SELECT INSERTBOOKING(%f,%d,'%s','%s','%s',%d,'%d','%s');";
	protected static String insertbooking (float price,int uid,String cardNumber,String fromAP,String toAp,int fnum,boolean ret,String date){
		return String.format(InsertBooking, price,uid,cardNumber,fromAP,toAp,fnum,ret?1:0,date);
	}
	
	protected final static String BookingSchedule = 
			"SELECT * FROM booking_schedule WHERE order_number = %d";
	protected static String bookingSchedule(int oid){
		return String.format(BookingSchedule, oid);
	}
	protected final static String DeleteBookingSchedule = 
			"DELETE FROM booking_schedule WHERE order_number = %d";
	protected static String deleteBookingSchedule(int oid){
		return String.format(DeleteBookingSchedule, oid);
	}
	
	protected final static String InsertBookingSchedule=
			"SELECT INSERTBKSCH(%d,'%s',%d,'%s','%d','%d',%d);";
	protected final static String insertBookingSchedule(int bid,Schedule schedule,boolean isret){
		return String.format(InsertBookingSchedule, bid,schedule.getAcode(),schedule.getFlightNumber(),schedule.getSchedule_date(),isret?1:0,schedule.isBookfirstclass()?1:0,schedule.getFlight_id());
	}
	
	
	
	
	protected final static String MatchFlightLine= "SELECT * FROM matchPath('%s','%s',%d,'%s','%s',%b,'%s',%b,%f,%b,'%s','%s')";
			
	
	protected static String matchFlightLine(String from,String to,int maxTrans,String tsLow,String tsUp,boolean limtTotalTime,String totalTime,boolean limtTotalPrice,float totalPrice,boolean selectDate, String date,String orderBy){
		return String.format(MatchFlightLine,from,to,maxTrans,tsLow,tsUp,limtTotalTime,totalTime,limtTotalPrice,totalPrice,selectDate,date,orderBy);
	}
	protected static String matchFlightLine(String from,String to,int maxTrans,boolean limtTotalTime,String totalTime,boolean limtTotalPrice,float totalPrice,boolean selectDate, String date,String ob){
		//return "SELECT * FROM matchPath('ORD','IAH',3,'3 hours','1 day',false,'1 day',false,1.000000,true,'2019-03-01','none')";
		return matchFlightLine(from, to, maxTrans, "3 hours", "1 day", limtTotalTime, totalTime, limtTotalPrice, totalPrice,selectDate,date,ob);
	}
	protected static String orderBy(String tag){
		if(tag==null)
			return ";";
		switch (tag) {
		case "connect":return "ORDER BY flight_count;";
		case "length" :return "ORDER BY length;";
		case "price"  :return "ORDER BY price;";
		default:
			return ";";
		}
	}
	
	protected final static String FindSchedule="SELECT * FROM findSchedule(%d,'%s')";
	protected static String findSchedule(int fid,String date){
		return String.format(FindSchedule,fid,date);
	}
	
	protected final static String AirLine = "SELECT * FROM air_line WHERE acode = '%s'";
	protected static String airLine(String acode) {
		return String.format(AirLine, acode);
	}
	
	protected final static String AirPort = "SELECT * FROM airports WHERE iata  = '%s'";
	protected static String airPort(String iata) {
		return String.format(AirPort, iata);
	}
	protected final static String AirPortName = "SELECT name FROM airports WHERE iata  = '%s'";
	protected static String airPortName(String iata) {
		return String.format(AirPortName, iata);
	}
	
	protected final static String AirPorts = "SELECT iata FROM airports WHERE iata IS NOT NULL";
	
	protected final static String Countries = 
			"SELECT DISTINCT country FROM airports WHERE iata IS NOT NULL ORDER BY country";
	protected final static String CountryAirport = 
			"SELECT DISTINCT iata FROM airports WHERE country = '%s'";
	protected final static String CountryAirports = 
			"SELECT * FROM airports WHERE country = '%s' ORDER BY city,iata";
	protected static String countryAirport(String country) {
		return String.format(CountryAirport, country);
		
	}
	protected static String countryAirports(String country) {
		return String.format(CountryAirports, country);
		
	}
	
	protected final static String Mileage = 
			"SELECT airline_name, sum(mileage) AS mileage\n"+
			"FROM\n"+ 
			"booking_schedule NATURAL JOIN \n"+
			"flight NATURAL JOIN \n"+
			"air_line NATURAL JOIN\n"+
			"(SELECT order_num AS order_number, user_id FROM Booking)AS oid\n"+
			"WHERE user_id = %d\n"+
			"GROUP BY airline_name";
	protected static String mileage(int uid){
		return String.format(Mileage, uid);
	}
	
	
}
