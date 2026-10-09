package org.firstinspires.ftc.teamcode.tutorials.mechanisms;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.RobotHardwareNames;

public class TestBenchIMU {
    private IMU imu;
    private DcMotor motor;

    public void init(HardwareMap hwMap) {
        imu = hwMap.get(IMU.class, RobotHardwareNames.IMU);
        motor = hwMap.get(DcMotor.class, RobotHardwareNames.MOTOR);

        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                //May need to change these:
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        );

        imu.initialize(new IMU.Parameters(RevOrientation));

        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public double getHeading(
            //AngleUnit angleUnit
    ) {
        return imu.getRobotYawPitchRollAngles().getYaw(
                //angleUnit
                AngleUnit.DEGREES
        );
    }

    public void setMotor(double power) {
        motor.setPower(power);
    }
}
