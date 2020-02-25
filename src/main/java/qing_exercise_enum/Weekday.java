package qing_exercise_enum;

public enum Weekday {
	SUN(7,"星期日"),MON(1,"星期一"),TUE(2,"星期二"),WED(3,"星期三"),
	THU(4,"星期四"),FRI(5,"星期五"),SAT(6,"星期六");
	private int val;
	private String nam;
	public int getVal() {
		return val;
	}
	public void setVal(int val) {
		this.val = val;
	}
	public String getNam() {
		return nam;
	}
	public void setNam(String nam) {
		this.nam = nam;
	}
	private Weekday(int val,String nam) {
		this.val =val;
		this.nam = nam;
	}
	public static Weekday getNextDay(Weekday nowDay) {
		int nextDayValue = nowDay.val;
		if(++nextDayValue == 7) {
			nextDayValue = 0;
		}
		return getWeekdayByValue(nextDayValue);
	}
	public static Weekday getWeekdayByValue(int nextDayValue) {
		for(Weekday w : Weekday.values()) {
			if(w.val == nextDayValue) {
				return w;
			}
		}
		return null;
	}
	

}
