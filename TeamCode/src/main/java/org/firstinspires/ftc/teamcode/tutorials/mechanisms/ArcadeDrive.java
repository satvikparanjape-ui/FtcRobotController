package org.firstinspires.ftc.teamcode.tutorials.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotHardware;

public class ArcadeDrive {
    private final RobotHardware robot = new RobotHardware();

    public void init(HardwareMap hwMap) {
        robot.init(hwMap);
    }

    public void drive(double throttle, double spin) {
        double leftPower = throttle + spin;
        double rightPower = throttle - spin;
        double largest = Math.max(Math.abs(leftPower), Math.abs(rightPower));
        if (largest > 1.0) {
            leftPower /= largest;
            rightPower /= largest;
        }

        robot.leftFrontDrive.setPower(leftPower);
        robot.rightFrontDrive.setPower(rightPower);
        robot.leftBackDrive.setPower(leftPower);
        robot.rightBackDrive.setPower(rightPower);
    }
}
