package scanheros.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class Hardware4Win {

    private static String sn = null;

    public static String getSerialNumber() {

        if (sn != null) {
            return sn;
        }

        ProcessBuilder pb = new ProcessBuilder("wmic", "bios", "get", "serialnumber");

        try {
            Process process = pb.start();

            try (InputStream is = process.getInputStream();
                OutputStream os = process.getOutputStream();
                Scanner sc = new Scanner(is)) {

                os.close();
                while (sc.hasNext()) {
                    String next = sc.next();
                    if ("SerialNumber".equals(next)) {
                        sn = sc.next().trim();
                        break;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (sn == null) {
            throw new RuntimeException("Cannot find computer SN");
        }

        return sn;
    }
}
