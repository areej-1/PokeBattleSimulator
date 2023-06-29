public class Clock {
	private int hour;
	private int minute;
	private int second;
	
	public Clock(int aHour, int aMinute, int aSecond) {
		hour = aHour;
		minute = aMinute;
		second = aSecond;
	}
	public String toString() {
		return ""+hour+":"+minute+":"+second;
	}
	public Clock getTime() {
		return this;
	}
	public void setTime(int newHour, int newMin, int newSec) {
		hour = newHour;
		minute = newMin;
		second = newSec;
	}

}
