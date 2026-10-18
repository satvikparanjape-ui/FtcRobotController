package org.firstinspires.ftc.teamcode.BioBuzzVision.Utilities.Vision;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.RED;
import static org.opencv.core.Core.inRange;
import static org.opencv.core.CvType.CV_8U;
import static org.opencv.imgproc.Imgproc.CHAIN_APPROX_SIMPLE;
import static org.opencv.imgproc.Imgproc.COLOR_RGB2HSV;
import static org.opencv.imgproc.Imgproc.RETR_TREE;
import static org.opencv.imgproc.Imgproc.boundingRect;
import static org.opencv.imgproc.Imgproc.dilate;
import static org.opencv.imgproc.Imgproc.erode;
import static org.opencv.imgproc.Imgproc.findContours;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.robotcore.external.function.Consumer;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BioBuzzProcessor implements VisionProcessor, CameraStreamSource {

    public static Rect largestRect;
    public static Rect largestTeam1Rect;
    public static Rect largestTeam2Rect;

    public static int erodeConstant = 5;
    public static int dilateConstant = 5;

    public static boolean targetDetected = false;
    ArrayList<MatOfPoint> contoursTeam1 = new ArrayList<>();
    ArrayList<MatOfPoint> contoursTeam2 = new ArrayList<>();

    List<Rect> team1Rects = new ArrayList<>();
    List<Rect> team2Rects = new ArrayList<>();
    private static int IMG_HEIGHT = 0;
    private static int IMG_WIDTH = 0;

    private Mat output = new Mat(),
            maskTeam1 = new Mat(),
            maskTeam2 = new Mat();
    private Mat hierarchy = new Mat();

    private final AtomicReference<Bitmap> lastFrame =
            new AtomicReference<>(Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565));

    @Config
    public static class BioBuzzDash {
        // BIOBUZZ Season HSV Color Thresholds (adjustable in Dashboard)
        public static int maxH_team1 = 120; // Blue
        public static int maxS_team1 = 255;
        public static int maxV_team1 = 255;
        public static int minH_team1 = 90;
        public static int minS_team1 = 100;
        public static int minV_team1 = 80;

        public static int maxH_team2 = 15;  // Red
        public static int maxS_team2 = 255;
        public static int maxV_team2 = 255;
        public static int minH_team2 = 0;
        public static int minS_team2 = 100;
        public static int minV_team2 = 80;
    }

    @Override
    public Mat processFrame(Mat input, long captureTimeNanos) {
        input.copyTo(output);
        contoursTeam1.clear();
        contoursTeam2.clear();
        team1Rects.clear();
        team2Rects.clear();

        IMG_HEIGHT = input.rows();
        IMG_WIDTH = input.cols();

        Scalar MIN_THRESH_1 = new Scalar(BioBuzzDash.minH_team1, BioBuzzDash.minS_team1, BioBuzzDash.minV_team1);
        Scalar MAX_THRESH_1 = new Scalar(BioBuzzDash.maxH_team1, BioBuzzDash.maxS_team1, BioBuzzDash.maxV_team1);

        Scalar MIN_THRESH_2 = new Scalar(BioBuzzDash.minH_team2, BioBuzzDash.minS_team2, BioBuzzDash.minV_team2);
        Scalar MAX_THRESH_2 = new Scalar(BioBuzzDash.maxH_team2, BioBuzzDash.maxS_team2, BioBuzzDash.maxV_team2);

        Imgproc.cvtColor(input, maskTeam1, COLOR_RGB2HSV);
        Imgproc.cvtColor(input, maskTeam2, COLOR_RGB2HSV);
        inRange(maskTeam1, MIN_THRESH_1, MAX_THRESH_1, maskTeam1);
        inRange(maskTeam2, MIN_THRESH_2, MAX_THRESH_2, maskTeam2);

        Rect submatRect = new Rect(new Point(4, 4), new Point(IMG_WIDTH, IMG_HEIGHT));
        maskTeam1 = maskTeam1.submat(submatRect);
        maskTeam2 = maskTeam2.submat(submatRect);

        erode(maskTeam1, maskTeam1, new Mat(erodeConstant, erodeConstant, CV_8U));
        dilate(maskTeam1, maskTeam1, new Mat(dilateConstant, dilateConstant, CV_8U));
        erode(maskTeam2, maskTeam2, new Mat(erodeConstant, erodeConstant, CV_8U));
        dilate(maskTeam2, maskTeam2, new Mat(dilateConstant, dilateConstant, CV_8U));

        findContours(maskTeam1, contoursTeam1, hierarchy, RETR_TREE, CHAIN_APPROX_SIMPLE);
        findContours(maskTeam2, contoursTeam2, hierarchy, RETR_TREE, CHAIN_APPROX_SIMPLE);

        largestTeam1Rect = getObjectsDetected(contoursTeam1, BLUE);
        largestTeam2Rect = getObjectsDetected(contoursTeam2, RED);

        Bitmap b = Bitmap.createBitmap(output.width(), output.height(), Bitmap.Config.RGB_565);
        Utils.matToBitmap(output, b);
        lastFrame.set(b);

        return output;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
        Paint paintTeam1 = new Paint();
        paintTeam1.setColor(BLUE);
        paintTeam1.setStyle(Paint.Style.STROKE);
        paintTeam1.setStrokeWidth(scaleCanvasDensity * 8);

        Paint paintTeam2 = new Paint();
        paintTeam2.setColor(RED);
        paintTeam2.setStyle(Paint.Style.STROKE);
        paintTeam2.setStrokeWidth(scaleCanvasDensity * 8);

        if (targetDetected) {
            if (largestTeam1Rect != null) {
                canvas.drawRect(makeGraphicsRect(largestTeam1Rect, scaleBmpPxToCanvasPx), paintTeam1);
            }
            if (largestTeam2Rect != null) {
                canvas.drawRect(makeGraphicsRect(largestTeam2Rect, scaleBmpPxToCanvasPx), paintTeam2);
            }
        }
    }

    @Override
    public void getFrameBitmap(Continuation<? extends Consumer<Bitmap>> continuation) {
        continuation.dispatch(bitmapConsumer -> bitmapConsumer.accept(lastFrame.get()));
    }

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        lastFrame.set(Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565));
    }

    public Rect getObjectsDetected(ArrayList<MatOfPoint> contours, int color) {
        if (!contours.isEmpty()) {
            MatOfPoint largestContour = findLargestContour(contours);
            if (largestContour != null) {
                List<Rect> rects = new ArrayList<>();
                for (int i = 0; i < contours.size(); i++) {
                    Rect rect = boundingRect(contours.get(i));
                    rects.add(rect);
                }
                if (!rects.isEmpty()) {
                    largestRect = VisionUtils.sortRectsByMaxOption(1, VisionUtils.RECT_OPTION.AREA, rects).get(0);
                    targetDetected = true;
                    if (color == BLUE && !rects.isEmpty()) {
                        team1Rects = VisionUtils.sortRectsByMaxOption(1, VisionUtils.RECT_OPTION.AREA, rects);
                    } else if (color == RED && !rects.isEmpty()) {
                        team2Rects = VisionUtils.sortRectsByMaxOption(1, VisionUtils.RECT_OPTION.AREA, rects);
                    }
                } else {
                    largestRect = null;
                    targetDetected = false;
                }
            }
            return largestRect;
        } else {
            return null;
        }
    }

    private MatOfPoint findLargestContour(List<MatOfPoint> contours) {
        double maxArea = 0;
        MatOfPoint largestContour = null;
        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area > maxArea) {
                maxArea = area;
                largestContour = contour;
            }
        }
        return largestContour;
    }

    private android.graphics.Rect makeGraphicsRect(Rect rect, float scaleBmpPxToCanvasPx) {
        int left = Math.round(rect.x * scaleBmpPxToCanvasPx);
        int top = Math.round(rect.y * scaleBmpPxToCanvasPx);
        int right = left + Math.round(rect.width * scaleBmpPxToCanvasPx);
        int bottom = top + Math.round(rect.height * scaleBmpPxToCanvasPx);

        return new android.graphics.Rect(left, top, right, bottom);
    }
}
