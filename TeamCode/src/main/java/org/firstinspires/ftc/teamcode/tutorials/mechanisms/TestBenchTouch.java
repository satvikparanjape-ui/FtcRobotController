package org.firstinspires.ftc.teamcode.tutorials.mechanisms;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotHardwareNames;

public class TestBenchTouch {
    private DigitalChannel touchSensor; // touchSensorOnClaw or touchSensorIntake

    public void init(HardwareMap hwMap) {
        touchSensor = hwMap.get(DigitalChannel.class, RobotHardwareNames.TOUCH_SENSOR);
        touchSensor.setMode(DigitalChannel.Mode.INPUT);
    }

    public boolean isTouchSensorPressed() {
        return !touchSensor.getState();
    }

    public boolean isTouchSensorReleased() {
        return touchSensor.getState();
    }
}
