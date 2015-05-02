package cognizant.nayan.io;



public final class IO {
	public static final class Paths {
		private static final String USER_DIR = System.getProperty("user.dir");
		public static final String BASE_IMAGE_DIR_PATH = "/base_Images/Scenario_Directory_V";
		public static final String BASE_IMAGE_ABS_DIR_PATH = USER_DIR+"/src/main/resources/base_Images/Scenario_Directory_V";
		public static final String CURRENT_IMAGE_DIR_PATH = "/current_Images/Scenario_Directory_V";
		public static final String CURRENT_IMAGE_ABS_DIR_PATH = USER_DIR+"/src/main/resources/current_Images/Scenario_Directory_V";
		public static final String INCOMPATIBLE_IMAGE_DIR_PATH = "/incompatible_Images/Scenario_Directory_V";
		public static final String INCOMPATIBLE_IMAGE_ABS_DIR_PATH = USER_DIR+ "/src/main/resources/incompatible_Images/Scenario_Directory_V";
		public static final String OBJECT_STATE_FOLDER = "src/main/resources/object_state/";
	}
}
