import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.imgproc.Imgproc;
import org.opencv.highgui.HighGui;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AttendanceCapture {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws Exception {

        String name = "person1"; // manual for now
        CascadeClassifier faceDetector =
                new CascadeClassifier("haarcascade_frontalface_default.xml");

        VideoCapture cam = new VideoCapture(0);
        if (!cam.isOpened()) {
            System.out.println("Camera not opened");
            return;
        }

        Mat frame = new Mat();
        boolean marked = false;

        while (true) {
            cam.read(frame);
            if (frame.empty()) continue;

            Mat gray = new Mat();
            Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);

            MatOfRect faces = new MatOfRect();
            faceDetector.detectMultiScale(gray, faces);

            if (faces.toArray().length > 0 && !marked) {
                markAttendance(name);
                marked = true;
                System.out.println("Attendance marked for " + name);
            }

            HighGui.imshow("Attendance Camera", frame);

            if (HighGui.waitKey(1) == 'q') break;
        }

        cam.release();
        HighGui.destroyAllWindows();
    }

    static void markAttendance(String name) throws Exception {
        FileWriter fw = new FileWriter("attendance.csv", true);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        fw.write(name + "," + date + "," + time + "\n");
        fw.close();
    }
}