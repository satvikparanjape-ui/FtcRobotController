package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//@Disabled
@TeleOp(name = "Mec BioBuzz StarterBot Teleop", group = "StarterBot")
public class BioBuzzStarterbotTeleopMecanum extends OpMode {
    private final RobotHardware robot = new RobotHardware();

    public final int LAUNCHER_TARGET_VELOCITY = 1250; //2678 RPM
    public final int LAUNCHER_MIN_VELOCITY = 1240; //2571 RPM

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
    public void init_loop() {}

    @Override
    public void start() {}

    @Override
    public void loop() {
        mecanumDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        intakePower = -gamepad2.right_stick_y + gamepad1.left_stick_y;

        launch();

        robot.setIntakePower(intakePower);

        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftFrontPower, rightFrontPower);
        //telemetry.addData("Triggers", "left (%.2f, right (%.2f)",gamepad1.left_trigger, gamepad1.right_trigger);
    }

    @Override
    public void stop() {}

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

    void launch() {

        robot.setLauncherVelocity(Math.abs(LAUNCHER_TARGET_VELOCITY/-gamepad2.left_stick_y));
        
        if (robot.getLauncherVelocity() > LAUNCHER_MIN_VELOCITY) {
            robot.setWindmillPower(-gamepad2.right_stick_y);
            intakePower += 0.5;
        } else {
            robot.setWindmillPower(0);
        }
    }
}
