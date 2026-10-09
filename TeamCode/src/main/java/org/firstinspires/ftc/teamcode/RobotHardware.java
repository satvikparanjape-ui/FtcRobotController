package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

/**
 * Hardware Abstraction Class for the Robot.
 * This class fetches all hardware components from the HardwareMap using
 * {@link RobotHardwareNames} and configures their directions, brake modes, and motor modes.
 */
public class RobotHardware {

    // Hardware components
    public DcMotor leftFrontDrive = null;
    public DcMotor leftBackDrive = null;
    public DcMotor rightFrontDrive = null;
    public DcMotor rightBackDrive = null;
    public DcMotorEx launcher = null;
    public DcMotor intake = null;
    public CRServo leftIntakeServo = null;
    public CRServo rightIntakeServo = null;
    public CRServo windmillServo = null;

    /**
     * Initialize all hardware components from the supplied HardwareMap.
     *
     * @param hwMap The HardwareMap instance from the current OpMode.
     */
    public void init(HardwareMap hwMap) {
        // Fetch devices using centralized hardware names
        leftFrontDrive = hwMap.get(DcMotor.class, RobotHardwareNames.LEFT_FRONT_DRIVE);
        rightFrontDrive = hwMap.get(DcMotor.class, RobotHardwareNames.RIGHT_FRONT_DRIVE);
        leftBackDrive = hwMap.get(DcMotor.class, RobotHardwareNames.LEFT_BACK_DRIVE);
        rightBackDrive = hwMap.get(DcMotor.class, RobotHardwareNames.RIGHT_BACK_DRIVE);
        intake = hwMap.get(DcMotor.class, RobotHardwareNames.INTAKE);
        launcher = hwMap.get(DcMotorEx.class, RobotHardwareNames.LAUNCHER);
        windmillServo = hwMap.get(CRServo.class, RobotHardwareNames.WINDMILL_SERVO);
        leftIntakeServo = hwMap.get(CRServo.class, RobotHardwareNames.LEFT_INTAKE_SERVO);
        rightIntakeServo = hwMap.get(CRServo.class, RobotHardwareNames.RIGHT_INTAKE_SERVO);

        // Motor directions
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        // Brake behavior
        leftFrontDrive.setZeroPowerBehavior(BRAKE);
        rightFrontDrive.setZeroPowerBehavior(BRAKE);
        leftBackDrive.setZeroPowerBehavior(BRAKE);
        rightBackDrive.setZeroPowerBehavior(BRAKE);
        intake.setZeroPowerBehavior(BRAKE);

        // Run using encoder
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Launcher setup
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(40, 0, 0, 12.5));

        // Servo initializations
        leftIntakeServo.setPower(0);
        rightIntakeServo.setPower(0);
        windmillServo.setPower(0);

        rightIntakeServo.setDirection(DcMotorSimple.Direction.REVERSE);
        windmillServo.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}
