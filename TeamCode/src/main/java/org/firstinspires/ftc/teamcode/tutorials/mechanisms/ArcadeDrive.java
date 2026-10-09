package org.firstinspires.ftc.teamcode.tutorials.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArcadeDrive {
    private DcMotor frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor;

    public void init(HardwareMap hwMap) {
        frontLeftMotor = hwMap.get(DcMotor.class, "front_left");
        backLeftMotor = hwMap.get(DcMotor.class, "back_left");
        frontRightMotor = hwMap.get(DcMotor.class, "front_right");
        backRightMotor = hwMap.get(DcMotor.class, "back_right");

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void drive(double throttle, double spin) {
        double leftPower = throttle + spin;
        double rightPower = throttle - spin;
        double largest = Math.max(Math.abs(leftPower), Math.abs(rightPower));
        if (largest > 1.0) {
            leftPower /= largest;
            rightPower /= largest;
        }

        frontLeftMotor.setPower(leftPower);
        frontRightMotor.setPower(rightPower);
        backLeftMotor.setPower(leftPower);
        backRightMotor.setPower(rightPower);
    }
}
