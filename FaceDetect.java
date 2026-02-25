import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

public class FaceDetect {

    static {
        System.load("C:\\Users\\ANIKET\\OneDrive\\Desktop\\Javastart\\opencv_java4120.dll");
    }

    public static void main(String[] args) {

        // Force DirectShow (fixes MSMF camera error on Windows)
        VideoCapture camera = new VideoCapture(0, Videoio.CAP_DSHOW);

        if (!camera.isOpened()) {
            System.out.println("❌ Camera not opened");
            return;
        }

        CascadeClassifier faceDetector =
                new CascadeClassifier("haarcascade_frontalface_default.xml");

        if (faceDetector.empty()) {
            System.out.println("❌ Haarcascade file not loaded");
            return;
        }

        Mat frame = new Mat();

        while (true) {
            if (!camera.read(frame) || frame.empty()) {
                System.out.println("❌ Cannot read frame");
                break;
            }

            Mat gray = new Mat();
            Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);

            MatOfRect faces = new MatOfRect();
            faceDetector.detectMultiScale(gray, faces);

            for (Rect rect : faces.toArray()) {
                Imgproc.rectangle(
                        frame,
                        new Point(rect.x, rect.y),
                        new Point(rect.x + rect.width, rect.y + rect.height),
                        new Scalar(0, 255, 0),
                        2
                );
            }

            HighGui.imshow("Face Detection", frame);

            if (HighGui.waitKey(1) == 27) { // ESC
                break;
            }
        }

        camera.release();
        HighGui.destroyAllWindows();
    }
}