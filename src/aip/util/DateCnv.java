package aip.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import aip.util.NVL;

import fi.joensuu.joyds1.calendar.JalaliCalendar;
import fi.joensuu.joyds1.calendar.PersianCalendar;

public class DateCnv {

	public static void main(String args[]){
		System.out.println("DateCnv.main():"+nowJalali());
	}
  public DateCnv() {
  }
  public static String nowTime(){
		Date d = new Date();
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				"HH:mm", Locale.US);
		return formatter.format(d);
	}
//  public static String nowJalai(){
//		Date d = new Date();
//		return DateCnv.M2S(d);
//	}
  public static String nowJalali(){
	  return getPersianDate(new JalaliCalendar());
  }
  public static String getPersianDate(JalaliCalendar pc){
    int y = pc.getYear();
    int m = pc.getMonth();
    int d = pc.getDay();

    return y +"/"+ ( m>=10 ? ""+m : "0"+m ) +"/"+ (d>=10?""+d:"0"+d);
  }
  static GregorianCalendar g = (GregorianCalendar)Calendar.getInstance();
  static JalaliCalendar pc=new JalaliCalendar();//.getJulianDayNumber());
  public static String M2S(java.util.Date MDate) {
    g.setTime(MDate);
    pc.set(g);
    return getPersianDate(pc);
  }
  public static int SYear(String mSDate){
      int i;
      try{
        i = mSDate.indexOf("/");
        return NVL.getInt(mSDate.substring(0,i));
      }catch(Exception ex){
        return -1;
      }
  }
  public static int SMonth(String mSDate){
      int i1, i2,m;
      try{
      i1 = mSDate.indexOf("/");
      i2 = mSDate.indexOf("/",i1 + 1);
      m = NVL.getInt(mSDate.substring(i1 + 1, i2));
      if( m < 1 || m > 12 ) m = -1;
      return m;
      }catch(Exception ex){
        return -1;
      }
  }
  public static int SDay(String mSDate){
      int i,d;
      try{
        i = mSDate.lastIndexOf("/");
        d = NVL.getInt(mSDate.substring(i + 1));
        if (d < 1 || d > 31)d=-1;
        return d;
      }catch(Exception ex){
        return -1;
      }
  }
}


