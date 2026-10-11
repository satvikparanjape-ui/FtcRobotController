package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp(name = "PS4_TeleOp_Broom", group = "Linear OpMode")
public class Teleop extends LinearOpMode {

    // Declare RobotHardware instance to handle hardware initialization and access
    private final RobotHardware robot = new RobotHardware();
    @Override
    public void runOpMode() {
        // Initialize all hardware variables using RobotHardware class
        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized. Press Play to Start.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // --- DRIVETRAIN MATH ---
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);
            double frontLeftPower = (y + x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            robot.setDrivePower(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

            // --- INTAKE (BROOM) CONTROL ---
            // If R1 (right_bumper) is pressed, run intake forward at full power
            if (gamepad1.right_bumper) {
                robot.setIntakePower(1.0);
            }
            // If L1 (left_bumper) is pressed, run intake backward at full power (to reject/outtake)
            else if (gamepad1.left_bumper) {
                robot.setIntakePower(-1.0);
            }
            // If neither button is pressed, turn the intake off
            else {
                robot.setIntakePower(0.0);
            }

            // Show telemetry data
            telemetry.addData("Status", "Running");
            if (robot.intake != null) {
                telemetry.addData("Broom Power", robot.intake.getPower());
            }
            telemetry.update();
        }
    }
}
