package org.firstinspires.ftc.teamcode.tutorials.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotHardwareNames;

public class TestBenchDC {
    private DcMotor motor; //linearSlideMotor0
    private double ticksPerRev; //revolution

    public void init(HardwareMap hwMap) {
        motor = hwMap.get(DcMotor.class, RobotHardwareNames.MOTOR);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ticksPerRev = motor.getMotorType().getTicksPerRev();
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setMotorSpeed(double speed) {
        // accepts values from -1.0 - 1.0
        motor.setPower(speed);
    }

    public double getMotorRevs() {
        return motor.getCurrentPosition() / ticksPerRev; // normalizing ticks to revolutions
    }

    public void setMotorZeroBehaviour(DcMotor.ZeroPowerBehavior zeroBehaviour) {
        motor.setZeroPowerBehavior(zeroBehaviour);
    }
}
