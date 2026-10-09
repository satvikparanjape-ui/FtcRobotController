package org.firstinspires.ftc.teamcode.tutorials.mechanisms;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotHardwareNames;

public class TestBenchServo {
    private Servo servoPos;
    private CRServo servoRot;

    public void init(HardwareMap hwMap) {
        servoPos = hwMap.get(Servo.class, RobotHardwareNames.SERVO_POS);
        servoRot = hwMap.get(CRServo.class, RobotHardwareNames.SERVO_ROT);
        servoPos.scaleRange(0.5, 1.0); // set range from midpoint to 180*
        servoPos.setDirection(Servo.Direction.REVERSE);
        servoRot.setDirection(CRServo.Direction.REVERSE);
    }

    public void setServoPos(double angle) {
        servoPos.setPosition(angle);
    }

    public void setServoRot(double power) {
        servoRot.setPower(power);
    }
}
