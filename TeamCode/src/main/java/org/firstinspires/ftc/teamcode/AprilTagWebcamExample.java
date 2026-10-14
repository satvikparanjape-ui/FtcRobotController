package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.mechanisms.AprilTagWebcam;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

@Autonomous
public class AprilTagWebcamExample extends OpMode {
    AprilTagWebcam aprilTagWebcam = new AprilTagWebcam();

    @Override
    public void init() {
        aprilTagWebcam.init(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        // update the vision portal
        aprilTagWebcam.update();
        /* *
        AprilTagDetection id20 = aprilTagWebcam.getTagBySpecificId(20);
        telemetry.addData("id20 String", id20.toString());
        */

        AprilTagSingleDetection id20 = (AprilTagSingleDetection) aprilTagWebcam.getTagBySpecificId(20);

        if (id20 != null) {
            aprilTagWebcam.displayDetectionTelemetry(id20);
        } else {
            telemetry.addData("Tag 20", "Not Detected");
        }
    }
}
