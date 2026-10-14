package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//@Disabled
@TeleOp(name = "Modified Teleop", group = "StarterBot")
public class StarterbotTeleopWithMods extends OpMode {
    private final RobotHardware robot = new RobotHardware();

    public final int LAUNCHER_TARGET_VELOCITY = 1250; //2678 RPM
    public final int LAUNCHER_MIN_VELOCITY = LAUNCHER_TARGET_VELOCITY - 50; //2571 RPM

    double leftFrontPower;
    double rightFrontPower;
    double leftBackPower;
    double rightBackPower;

    double intakePower;

    @Override
    public void init() {
        robot.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        double powerState;

        mecanumDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        intakePower = -gamepad2.right_stick_y + gamepad1.left_stick_y;

        launch();

        if (gamepad2.x) powerState = -1; else powerState = 1;
        if (gamepad2.triangle) robot.setBroomPower(powerState);
        if (gamepad2.left_bumper) robot.setLeftIntakeServoPower(powerState);
        if (gamepad2.right_bumper) robot.setRightIntakeServoPower(powerState);

        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftFrontPower, rightFrontPower);
        //telemetry.addData("Triggers", "left (%.2f, right (%.2f)",gamepad1.left_trigger, gamepad1.right_trigger);
    }

    void mecanumDrive(double forward, double strafe, double rotate) {
        leftFrontPower = forward + strafe + rotate;
        rightFrontPower = forward - strafe - rotate;
        leftBackPower = forward - strafe + rotate;
        rightBackPower = forward + strafe - rotate;

        double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }

        robot.setDrivePower(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower);
    }

    double normalisedYValues(double gamepadInput) {
        // Invert the FTC stick so forward is positive, shifting range from [-1.0, 1.0] to [1.0, -1.0]
        double invertedInput = -gamepadInput;

        // Shift and scale the range from [-1.0, 1.0] to exactly [0.0, 1.0]
        return (invertedInput + 1.0) / 2.0;
    }

    void launch() {
        final double differenceCoefficient = 2.0;
        double triggerWindmill = 0;
        double triggerLauncher = 0;

        if (gamepad2.left_trigger_pressed) triggerLauncher = 1;
        if (gamepad2.right_trigger_pressed) triggerWindmill = 1;


        double velocityModifier = normalisedYValues(gamepad2.left_stick_y);
        robot.setLauncherVelocity(Math.abs(LAUNCHER_TARGET_VELOCITY * velocityModifier) * triggerLauncher);

        if (robot.getLauncherVelocity() > LAUNCHER_MIN_VELOCITY) {
            robot.setWindmillPower(-gamepad2.right_stick_y * triggerWindmill);
            intakePower += (0.5 * triggerWindmill);
        } else {
            robot.setWindmillPower(0);
        }
    }
}
