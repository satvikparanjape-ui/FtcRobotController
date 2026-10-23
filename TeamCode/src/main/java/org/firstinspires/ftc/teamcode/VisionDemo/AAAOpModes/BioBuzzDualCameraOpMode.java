package org.firstinspires.ftc.teamcode.VisionDemo.AAAOpModes;

import static org.firstinspires.ftc.teamcode.VisionDemo.Subsystems.Constants.cx;
import static org.firstinspires.ftc.teamcode.VisionDemo.Subsystems.Constants.cy;
import static org.firstinspires.ftc.teamcode.VisionDemo.Subsystems.Constants.fx;
import static org.firstinspires.ftc.teamcode.VisionDemo.Subsystems.Constants.fy;

import android.util.Size;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.VisionDemo.Utilities.Vision.PollenProcessor;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.List;

@TeleOp(name = "BioBuzz Dual Camera Vision")
public class BioBuzzDualCameraOpMode extends OpMode {

    // Webcams
    WebcamName webcamField;      // Webcam 1: Field facing (detects yellow pollen)
    WebcamName webcamAprilTag;   // Webcam 2: Upward facing (detects AprilTags)

    // Processors
    PollenProcessor pollenProcessor;
    AprilTagProcessor aprilTagProcessor;

    // Portals
    VisionPortal visionPortalField;
    VisionPortal visionPortalAprilTag;

    static Size resolution = new Size(1280, 720);

    @Override
    public void init() {
        // Initialize processors
        pollenProcessor = new PollenProcessor();
        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setLensIntrinsics(fx, fy, cx, cy)
                .build();

        // Get webcams from hardware map (Make sure config names match "Webcam 1" and "Webcam 2")
        webcamField = hardwareMap.get(WebcamName.class, "Webcam 1");
        webcamAprilTag = hardwareMap.get(WebcamName.class, "Webcam 2");

        // 1. Build VisionPortal for Field Webcam (Yellow Pollen Detection)
        visionPortalField = new VisionPortal.Builder()
                .setCamera(webcamField)
                .addProcessor(pollenProcessor)
                .setCameraResolution(resolution)
                .build();

        // 2. Build VisionPortal for Upward-Facing Webcam (AprilTag Localization)
        visionPortalAprilTag = new VisionPortal.Builder()
                .setCamera(webcamAprilTag)
                .addProcessor(aprilTagProcessor)
                .setCameraResolution(new Size(640, 480))
                .build();

        // Start camera stream on FTC Dashboard (showing field camera by default)
        FtcDashboard.getInstance().startCameraStream(visionPortalField, 0);
        telemetry = FtcDashboard.getInstance().getTelemetry();

        telemetry.addData("Status", "Dual Camera Vision Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        // --- 1. Pollen Telemetry (Field Webcam) ---
        telemetry.addData("--- FIELD WEBCAM (Pollen) ---", "");
        telemetry.addData("Pollen Detected", PollenProcessor.pollenDetected);
        if (PollenProcessor.pollenDetected) {
            telemetry.addData("Pollen Center X", "%.1f", PollenProcessor.pollenCenterX);
            telemetry.addData("Pollen Center Y", "%.1f", PollenProcessor.pollenCenterY);
            telemetry.addData("Pollen Area", "%.1f", PollenProcessor.pollenArea);

            // Calculate horizontal offset from screen center for alignment/pickup
            double frameCenterX = resolution.getWidth() / 2.0;
            double xOffset = PollenProcessor.pollenCenterX - frameCenterX;
            telemetry.addData("X Offset from Center", "%.1f px", xOffset);
        }

        // --- 2. AprilTag Telemetry (Upward-Facing Webcam) ---
        telemetry.addData("--- UPWARD WEBCAM (AprilTags) ---", "");
        List<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();
        telemetryDetections(currentDetections);

        telemetry.update();
    }

    private void telemetryDetections(List<AprilTagDetection> detections) {
        telemetry.addData("# AprilTags Detected", detections.size());
        for (AprilTagDetection detection : detections) {
            if (detection instanceof AprilTagSingleDetection) {
                AprilTagSingleDetection singleDet = (AprilTagSingleDetection) detection;
                if (singleDet.metadata != null) {
                    telemetry.addLine(String.format("Single Tag ID %d (%s) - Dist: %.1f in, Bearing: %.1f deg",
                            singleDet.id,
                            singleDet.metadata.name,
                            singleDet.ftcPose.range,
                            singleDet.ftcPose.bearing));
                }
            } else if (detection instanceof AprilTagClusterDetection) {
                AprilTagClusterDetection clusterDet = (AprilTagClusterDetection) detection;
                if (clusterDet.metadata != null) {
                    telemetry.addLine(String.format("Cluster (%s) - Found: %d%%, Dist: %.1f in",
                            clusterDet.metadata.name,
                            clusterDet.percentClusterFound,
                            clusterDet.ftcPose.range));
                }
            }
        }
    }

    @Override
    public void stop() {
        if (visionPortalField != null) {
            visionPortalField.close();
        }
        if (visionPortalAprilTag != null) {
            visionPortalAprilTag.close();
        }
    }
}
