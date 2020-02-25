package qing_exercise_enum;
/**
 * 枚举类在switch中也可以用
 * 枚举类不可以继承其他类了，因为默认继承了Enum类。
 * @author zheng
 *
 */
public enum TrafficLamp {
	RED(30){

		@Override
		public TrafficLamp getNextLamp() {
			// TODO Auto-generated method stub
			return GREEN;
		}
		
	},
	GREEN(45){

		@Override
		public TrafficLamp getNextLamp() {
			// TODO Auto-generated method stub
			return YEELOW;
		}
		
	},
	YEELOW(5)
	{

		@Override
		public TrafficLamp getNextLamp() {
			// TODO Auto-generated method stub
			return RED;
		}
		
	};
	private int colorNum;
	private TrafficLamp(int colorNum) {
		this.colorNum = colorNum;
	}
	public abstract TrafficLamp getNextLamp();
}
