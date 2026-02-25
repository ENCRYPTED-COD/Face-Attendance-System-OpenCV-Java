import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.highgui.HighGui;

import java.io.File;

public class DatasetCapture {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {

        String personName = "person1";   // change for each student
        String baseDir = "dataset/" + personName;
        new File(baseDir).mkdirs();

        VideoCapture camera = new VideoCapture(0);

        if (!camera.isOpened()) {
            System.out.println("❌ Camera not opened");
            return;
        }

        CascadeClassifier faceDetector =
                new CascadeClassifier("haarcascade_frontalface_default.xml");

        if (faceDetector.empty()) {
            System.out.println("❌ Haarcascade not loaded");
            return;
        }

        Mat frame = new Mat();
        Mat gray = new Mat();
        int count = 0;

        System.out.println("▶ Press C to capture | Q to quit");

        while (true) {
            camera.read(frame);
            if (frame.empty()) continue;

            Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);

            MatOfRect faces = new MatOfRect();
            faceDetector.detectMultiScale(gray, faces);

            for (Rect r : faces.toArray()) {
                Imgproc.rectangle(
                        frame,
                        new Point(r.x, r.y),
                        new Point(r.x + r.width, r.y + r.height),
                        new Scalar(0, 255, 0),
                        2
                );
            }

            // ✅ show frame FIRST
            HighGui.imshow("Dataset Capture (C = Save, Q = Quit)", frame);

            // ✅ waitKey ONCE per loop
            int key = HighGui.waitKey(30);

            // SAVE IMAGE
            if ((key == 'c' || key == 'C') && faces.toArray().length > 0) {
                Rect r = faces.toArray()[0];
                Mat face = gray.submat(r);
                count++;

                String fileName = baseDir + "/img_" + count + ".jpg";
                Imgcodecs.imwrite(fileName, face);

                System.out.println("✅ Saved: " + fileName);
            }

            // QUIT
            if (key == 'q' || key == 'Q') {
                break;
            }
        }

        camera.release();
        HighGui.destroyAllWindows();
        System.out.println("🛑 Dataset capture finished");
    }
}