package DataBase;

import java.sql.*;
import java.util.ArrayList;


import models.*;

public class DataHelper {
	private String connectURL;
	private Connection con;
	private Statement statement;
	
	public DataHelper(String url){
		connectURL=url;
		try {
			Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
			con=DriverManager.getConnection(connectURL, "postgres", "pgsql123");
			statement=con.createStatement();
			statement.setQueryTimeout(20);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	private static String cntStr1 = "jdbc:postgresql://35.223.114.159/database";
	//private static String cntStr2 = "jdbc:postgresql://192.168.233.130/database";
	public DataHelper(){
		this(cntStr1);
	}
	
	public ResultSet query(String query){
		try {
			System.out.println(query);
			return statement.executeQuery(query);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean execute(String update) {
		try {
			System.out.println(update);
			return statement.executeUpdate(update)>0;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
	}
	
	public void startTransection() {
		execute("BEGIN;");
		execute("SET default_transaction_isolation='repeatable read';");
	}
	public void endTransection(boolean rollBack) {
		execute(rollBack?"ROLLBACK":"COMMIT");
	}
	
	public void close(){
		try {
			statement.close();
			con.close();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public boolean isEmailAddrFree(LoginInfo loginInfo){
		ResultSet result=query(Querys.checkUser(loginInfo.getUserName()));
		try {
			while(result.next()){
				boolean res =result.getInt(1)==0;
				result.close();
				return res;
			}
			result.close();
			return false;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
	}
	public boolean register (LoginInfo login,UserInfo user){
		//return execute(Querys.register(user, login));
		try{
			ResultSet result=query(Querys.register(user, login));
			while (result.next()){
				boolean success = result.getBoolean(1);
				result.close();
				return success;
			}
			result.close();
			return false;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean updateUserInfo(UserInfo info){
		return execute(Querys.updateUinfo(info));
	}
	public boolean updateLoginInfo(String password,String oldpass,int uid){
		return execute(Querys.updateLinfo(password,oldpass, uid));
	}
	public boolean insertAddress(Address addr,int uid){
		return execute(Querys.insertAddress(addr, uid));
	}
	public boolean updateAddress(Address addr, int uid){
		return execute(Querys.updateAddress(addr, uid));
	}
	public boolean deleteAddress(int uid, int aid){
		return execute(Querys.deleteAddress(uid, aid));
	}
	public boolean insertPayMethod(CreditCard card){
		return execute(Querys.insertPayMethod(card));
	}
	public boolean updateCard(CreditCard old,CreditCard card){
		return execute(Querys.updateCard(old, card));
	}
	public boolean deleteCard(CreditCard card){
		return execute(Querys.deleteCard(card));
	}
	public boolean deleteBooking(int uid, int onum){
		return execute(Querys.deleteBooking(uid, onum));
	}
	public int insertBooking(float price,int uid,String cardNumber,String fromAP,String toAp,int fnum,boolean ret,String date){
		//return execute(Querys.insertbooking(price,uid,cardNumber,fromAP,toAp,fnum,ret,date));
		try{
			ResultSet result=query(Querys.insertbooking(price,uid,cardNumber,fromAP,toAp,fnum,ret,date));
			while (result.next()){
				int bid = result.getInt(1);
				result.close();
				return bid;
			}
			result.close();
			return -1;
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	public boolean deleteBookingSchedule(int oid){
		return execute(Querys.deleteBookingSchedule(oid));
	}
	
	public boolean insertBookingSchedule(int bid,Schedule schedule,boolean isret){
		//return execute(Querys.insertBookingSchedule(schedule, isret));
		try{
			ResultSet result=query(Querys.insertBookingSchedule(bid,schedule, isret));
			while (result.next()){
				boolean success = result.getBoolean(1);
				result.close();
				return success;
			}
			result.close();
			return false;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public UserInfo getUserInfo(LoginInfo loginInfo){
		ResultSet result=query(Querys.userInfo(loginInfo));
		try{
			while (result.next()){
				UserInfo info= new UserInfo(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5));
				result.close();
				return info;
			}
			result.close();
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public ArrayList<Booking> getUserBooking(int uid){
		ResultSet result=query(Querys.userBooking(uid));
		ArrayList<Booking> list = new ArrayList<>();
		try{
			while (result.next()){
				list.add(new Booking(
						result.getInt(1),
						result.getFloat(2),
						result.getInt(3),
						result.getString(4),
						result.getString(5),
						result.getString(6),
						result.getInt(7),
						result.getInt(8)==1,
						result.getString(9)));
			}
			result.close();
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public ArrayList<AirlineMileage> getMileage(UserInfo info){
		ResultSet result=query(Querys.mileage(info.getUid()));
		ArrayList<AirlineMileage> list = new ArrayList<>();
		try{
			while (result.next()){
				list.add(new AirlineMileage(result.getString(1), result.getInt(2)));
			}
			result.close();
			return list;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	public ArrayList<Address> getUserAddress(UserInfo info){
		ResultSet result=query(Querys.userAddress(info.getUid()));
		ArrayList<Address> list = new ArrayList<>();
		try{
			while (result.next()){
				list.add(new Address(
						result.getInt(1),
						result.getString(2),
						result.getString(3),
						result.getString(4),
						result.getString(5),
						result.getString(6),
						result.getString(7),
						result.getString(8)));
			}
			result.close();
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public ArrayList<ScheduleBooking> getScheduleBooking(int oid){
		ResultSet result=query(Querys.bookingSchedule(oid));
		ArrayList<ScheduleBooking> list = new ArrayList<>();
		try{
			while (result.next()){
				list.add(new ScheduleBooking(
						result.getInt(1),
						result.getString(2),
						result.getInt(3),
						result.getString(4),
						result.getInt(6)==1,
						result.getInt(5)==1,
						result.getInt(7)));
			}
			result.close();
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<CreditCard> getUserCards(UserInfo info){
		ResultSet result=query(Querys.userPayMethod(info.getUid()));
		ArrayList<CreditCard> list = new ArrayList<>();
		try{
			while (result.next()){
				list.add(new CreditCard(
						result.getInt(1),
						result.getString(2),
						result.getShort(3),
						result.getString(4),
						result.getShort(5),
						result.getInt(6)));
			}
			result.close();
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<FlightLine> searchFlights(
			String from,
			String to,
			int maxTrans,
			String tsLow,
			String tsUp,
			boolean limtTotalTime,
			String totalTime,
			boolean limtTotalPrice,
			float totalPrice,
			boolean selectDate,
			String date,
			String ob){
		ResultSet result=query(Querys.matchFlightLine(from, to, maxTrans, tsLow, tsUp, limtTotalTime, totalTime, limtTotalPrice, totalPrice,selectDate,date,ob));
		ArrayList<FlightLine> list = new ArrayList<>();
		try{
			while (result.next()){
				list.add(new FlightLine(
						result.getString(result.findColumn("fly_date")),
						result.getString(result.findColumn("fly_time")),
						result.getString(result.findColumn("arrive_date")), 
						result.getString(result.findColumn("arrive_time")), 
						result.getInt(result.findColumn("flight_count")), 
						result.getString(result.findColumn("line")), 
						result.getString(result.findColumn("flight")), 
						result.getFloat(result.findColumn("price")),
						result.getString(result.findColumn("f_ids")),
						result.getString(result.findColumn("length"))));
			}
			result.close();
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public ArrayList<FlightLine> searchFlights(
			String from,
			String to,
			int maxTrans,
			boolean limtTotalTime,
			String totalTime,
			boolean limtTotalPrice,
			float totalPrice,
			boolean selectDate,
			String date,
			String ob){
		ResultSet result=query(Querys.matchFlightLine(from, to, maxTrans, limtTotalTime, totalTime, limtTotalPrice, totalPrice,selectDate,date,ob));
		ArrayList<FlightLine> list = new ArrayList<>();
		try{
			while (result.next()){
				list.add(new FlightLine(
						result.getString(result.findColumn("fly_date")),
						result.getString(result.findColumn("fly_time")),
						result.getString(result.findColumn("arrive_date")), 
						result.getString(result.findColumn("arrive_time")), 
						result.getInt(result.findColumn("flight_count")), 
						result.getString(result.findColumn("line")), 
						result.getString(result.findColumn("flight")), 
						result.getFloat(result.findColumn("price")),
						result.getString(result.findColumn("f_ids")),
						result.getString(result.findColumn("length"))));
			}
			result.close();
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Schedule getSchedule(int fid,String date){
		ResultSet result=query(Querys.findSchedule(fid, date));
		try{
			while (result.next()){
				Schedule res = new Schedule(
						result.getString(result.findColumn("acode")),
						result.getInt(result.findColumn("flight_number")),
						result.getString(result.findColumn("from_airport")),
						result.getString(result.findColumn("to_airport")),
						result.getInt(result.findColumn("mileage")),
						result.getString(result.findColumn("schedule_date")),
						result.getString(result.findColumn("from_time")),
						result.getString(result.findColumn("to_time")), 
						result.getInt(result.findColumn("firstclasscapacity"))-result.getInt(result.findColumn("soldf")),//-
						result.getInt(result.findColumn("econclassprice"))-result.getInt("solde"),//-
						result.getFloat(result.findColumn("firstclassprice")),
						result.getFloat(result.findColumn("econclassprice")),
						result.getBoolean(result.findColumn("passdate"))?1:0,
						result.getString(result.findColumn("airline_name")),
						result.getInt(result.findColumn("flight_id")));
				result.close();
				return res;
			}
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public AirLine getAirline(String acode){
		ResultSet result=query(Querys.airLine(acode));
		try{
			while (result.next()){
				AirLine res = new AirLine(
						result.getString(1),
						result.getString(2),
						result.getString(3));
				result.close();
				return res;
			}
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public Airport getAirport(String iata){
		ResultSet result=query(Querys.airPort(iata));
		try{
			while (result.next()){
				Airport res = new Airport(
						result.getString(result.findColumn("iata")),
						result.getString(result.findColumn("country")),
						result.getString(result.findColumn("city")),
						result.getString(result.findColumn("state")), 
						result.getString(result.findColumn("name")));
				result.close();
				return res;
			}
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public ArrayList<String> iatas(){
		ResultSet result=query(Querys.AirPorts);
		ArrayList<String> list = new ArrayList<>();
		try{
			while (result.next()){
				list.add(result.getString(1));
			}
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public ArrayList<String> countries(){
		ResultSet result=query(Querys.Countries);
		ArrayList<String> list = new ArrayList<>();
		try{
			while (result.next()){
				list.add(result.getString(1));
			}
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public ArrayList<String> countryAirport(String country){
		ResultSet result=query(Querys.countryAirport(country));
		ArrayList<String> list = new ArrayList<>();
		try{
			while (result.next()){
				list.add(result.getString(1));
			}
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Airport> countryAirports(String country){
		ResultSet result=query(Querys.countryAirports(country));
		ArrayList<Airport> list = new ArrayList<>();
		try{
			while (result.next()){
				Airport res = new Airport(
						result.getString(result.findColumn("iata")),
						result.getString(result.findColumn("country")),
						result.getString(result.findColumn("city")),
						result.getString(result.findColumn("state")), 
						result.getString(result.findColumn("name")));
				list.add(res);
			}
			result.close();
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
