package qing_exercise_enum;

public class EnumDemo {
	public static void main(String[] args) {
		System.out.println("nowday--->"+Weekday.SAT);
		System.out.println("nowday int --->"+Weekday.SAT.ordinal());
		System.out.println("nexday--->"+Weekday.getNextDay(Weekday.SUN));
		System.out.println("Weekday.FRI.compareTo(Weekday.WED)--->"+Weekday.FRI.compareTo(Weekday.WED));
		System.out.println("Weekday.SUN.getNam()--->"+Weekday.SUN.getNam());
		System.out.println("Weekday.SUN.getVal()--->"+Weekday.SUN.getVal());
		Weekday[] weekday1 = Weekday.class.getEnumConstants();
		Weekday[] weekday2 = Weekday.values();
		for(Weekday weekday : weekday1) {
			System.out.print(weekday+"-");
		}
		System.out.println();
		for(Weekday weekday : weekday2) {
			System.out.print(weekday+"--");
		}
	}

}
