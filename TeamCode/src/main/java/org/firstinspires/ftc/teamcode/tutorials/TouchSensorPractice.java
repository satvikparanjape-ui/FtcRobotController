package org.firstinspires.ftc.teamcode.tutorials;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.tutorials.mechanisms.TestBenchTouch;

@Disabled
@TeleOp
public class TouchSensorPractice extends OpMode {
    TestBenchTouch bench = new TestBenchTouch();

    @Override
    public void init() {
        bench.init(hardwareMap);
    }

    @Override
    public void loop() {
        String touchSensorState = "not pressed!";
        if(bench.isTouchSensorPressed()) {
            touchSensorState = "pressed!";
        }
        telemetry.addData("Touch Sensor State", touchSensorState);
    }

}
