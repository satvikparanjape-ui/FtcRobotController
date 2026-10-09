package org.firstinspires.ftc.teamcode;

/**
 * Centralized repository for all robot hardware device names.
 * Reference these constants across your OpModes so that when a device name
 * changes in the Robot Configuration on the Driver Station, you only have
 * to change it once in this file.
 */
public final class RobotHardwareNames {

    // Private constructor to prevent instantiation
    private RobotHardwareNames() {}

    // Mecanum Drivetrain Motors
    public static final String LEFT_FRONT_DRIVE = "left_front_drive";
    public static final String RIGHT_FRONT_DRIVE = "right_front_drive";
    public static final String LEFT_BACK_DRIVE = "left_back_drive";
    public static final String RIGHT_BACK_DRIVE = "right_back_drive";

    // Arcade Drive Motors
    public static final String FRONT_LEFT = "front_left";
    public static final String BACK_LEFT = "back_left";
    public static final String FRONT_RIGHT = "front_right";
    public static final String BACK_RIGHT = "back_right";

    // Intake & Launcher Hardware
    public static final String INTAKE = "intake";
    public static final String LAUNCHER = "launcher";
    public static final String WINDMILL_SERVO = "windmillServo";
    public static final String LEFT_INTAKE_SERVO = "left_intake_servo";
    public static final String RIGHT_INTAKE_SERVO = "right_intake_servo";

    // Sensors & TestBench Hardware
    public static final String MOTOR = "motor";
    public static final String IMU = "imu";
    public static final String COLOR_SENSOR = "sensor_color_distance";
    public static final String TOUCH_SENSOR = "touch_sensor";
    public static final String DISTANCE_SENSOR = "sensor_distance";
    public static final String SERVO_POS = "servo_pos";
    public static final String SERVO_ROT = "servo_rot";
}
