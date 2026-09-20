package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp
@Autonomous
public class HelloWorld extends OpMode {

    @Override
    public void init() {
        telemetry.addData("Hello", "User");
    }

    @Override
    public void loop(){

    }

    //Single line comment

    /*
    Multi
    Line
    Comment
     */
}
