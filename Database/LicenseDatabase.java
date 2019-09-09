package projDRIVERSLICENSE.Database;

import java.io.*;
import java.text.ParseException;
import java.util.LinkedList;

public class LicenseDatabase {

    private LinkedList<License> licenses;

    public LicenseDatabase(String excelPath) {
        licenses = new LinkedList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File(excelPath)));
            String line;

            while ((line = in.readLine()) != null) {
                String[] licenseInfo = line.split(",");
                try {
                    if (licenseInfo.length == 16) {
                        License l = new License(licenseInfo[0], licenseInfo[1], licenseInfo[2], licenseInfo[3], Integer.parseInt(licenseInfo[4]), licenseInfo[5], licenseInfo[6], licenseInfo[7], licenseInfo[8], Integer.parseInt(licenseInfo[9]), licenseInfo[10], licenseInfo[11], licenseInfo[12], licenseInfo[13], licenseInfo[14],licenseInfo[15],"");
                        licenses.add(l);
                    }
                } catch (NumberFormatException | ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public LinkedList<License> getLicenses() {
        return licenses;
    }
    public void add(License license) {
        licenses.add(license);
    }
}