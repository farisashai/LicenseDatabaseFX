package projDRIVERSLICENSE.LicensePics;

import projDRIVERSLICENSE.Database.License;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;

public class LicenseImage {

    private static SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy");

    public static String createImg (License l) throws IOException, URISyntaxException {
        BufferedImage image = ImageIO.read(new File(projDRIVERSLICENSE.LicensePics.LicenseImage.class.getResource("license.png").toURI()));
        Graphics g = image.getGraphics();
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        g.setColor(Color.RED);
        g.drawString(fmt.format(l.getExpiresOn()),205,192);
        g.drawString(l.getId(), 405, 243);
        g.drawString(fmt.format(l.getBirthDate()),420,468);
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        g.setColor(Color.BLACK);
        g.drawString(l.getFirstName(),405,376);
        g.drawString(l.getLastName(),405,336);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString(l.getAddress(),375,405);
        g.drawString(l.getCity() + ", " + l.getState() + " " + l.getZipCode(),375,430);
        g.drawString(l.getGender(), 558,548);
        g.drawString(l.getHeight(),558,576);
        g.drawString(l.getHairCol(),685,548);
        g.drawString("" + l.getWeight(),685,576);
        g.drawString(l.getEyeCol(),810,548);
        if (l.isDonor())
            g.drawImage(ImageIO.read(new File("/Users/farisashai/Documents/everything/intellij/src/projDRIVERSLICENSE/LicensePics/donor.png")), 355, 506, 82, 76, (img, infoflags, x, y, width, height) -> false);
        g.drawImage(ImageIO.read(new File(l.getImgPath())), 45, 199, 291, 357, (img, infoflags, x, y, width, height) -> false);
        System.out.println("Generating license for " + l.getFirstName() + " (" + l.getId() +") ...");
        g.dispose();
        String path = "/Users/farisashai/Documents/everything/intellij/src/projDRIVERSLICENSE/LicensePics/StoredLicenses/"+l.getId()+".png";
        ImageIO.write(image, "png", new File(path));
        return path;
    }
}
