import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceCapture {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {

        VideoCapture camera = new VideoCapture(0);

        if (!camera.isOpened()) {
            System.out.println("❌ Camera not opened");
            return;
        }
        System.out.println("✅ Camera opened");

        CascadeClassifier faceDetector =
                new CascadeClassifier("haarcascade_frontalface_default.xml");

        Mat frame = new Mat();
        int saved = 0;

        while (true) {

            camera.read(frame);

            if (frame.empty()) {
                System.out.println("❌ Empty frame");
                continue;
            }

            Mat gray = new Mat();
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

            HighGui.imshow("Face Capture", frame);

            int key = HighGui.waitKey(30);

            // AUTO-CAPTURE ON FIRST FACE
            if (faces.toArray().length > 0 && saved == 0) {
                Imgcodecs.imwrite("captured_face.jpg", frame);
                System.out.println("📸 Image captured: captured_face.jpg");
                saved = 1;
            }

            // ESC to exit
            if (key == 27) break;
        }

        camera.release();
        HighGui.destroyAllWindows();
        System.out.println("🛑 Done");
    }
}