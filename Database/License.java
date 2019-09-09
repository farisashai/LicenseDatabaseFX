package projDRIVERSLICENSE.Database;

import java.io.File;
import java.text.*;
import java.util.Date;

public class License {

    private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    private String name, address, city, state, gender, hairCol, eyeCol, height, id, imgPath, licensePath;
    private int zipCode, weight;
    private Date issuedOn, expiresOn, birthDate;
    private boolean donor;

    License(String name, String address, String city, String state, int zipCode, String gender, String hairCol, String eyeCol, String height, int weight, String issuedOn, String expiresOn, String birthDate, String id, String donor, String imgPath, String licensePath) throws ParseException {
        this.name = name.replace(',',' ');
        this.address = address.replace(',',' ');
        this.city = city.replace(',',' ');
        this.state = state.replace(',',' ');
        this.zipCode = zipCode;
        this.gender = gender;
        this.hairCol = hairCol.replace(',',' ');
        this.eyeCol = eyeCol.replace(',',' ');
        this.height = height.replace(',',' ').replace("\"\"\"","\"").substring(1);
        this.weight = weight;
        this.issuedOn = fmt.parse(issuedOn);
        this.expiresOn = fmt.parse(expiresOn);
        this.birthDate = fmt.parse(birthDate);
        this.id = id;
        this.donor = donor.charAt(0) == 89;
        this.imgPath = imgPath;
        String path = "/Users/farisashai/Documents/everything/intellij/src/projDRIVERSLICENSE/LicensePics/StoredLicenses/"+id+".png";
        this.licensePath = new File(path).exists() ? path : "";
    }

    public String toString() {
        return name + "," + address + "," + city + "," + state + "," + zipCode + "," + gender + "," + hairCol + "," + eyeCol + "," + height + "," + weight + "," + fmt.format(issuedOn) + "," + fmt.format(expiresOn) + "," + fmt.format(birthDate) + "," + id + "," + (donor ? (char)89 : (char)78);
    }
    public String getFirstName() {
        String[] arr = name.split(" ");
        String s = "";
        for (int i = 0; i < arr.length - 1; i++)
            s+= arr[i];
        return s;
    }
    public String getLastName() {
        String[] arr = name.split(" ");
        return arr[arr.length-1];
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public int getZipCode() {
        return zipCode;
    }
    public String getGender() {
        return gender;
    }
    public String getHairCol() {
        return hairCol;
    }
    public String getEyeCol() {
        return eyeCol;
    }
    public String getHeight() {
        return height;
    }
    public int getWeight() {
        return weight;
    }
    public Date getIssuedOn() {
        return issuedOn;
    }
    public Date getExpiresOn() {
        return expiresOn;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public boolean isDonor() {
        return donor;
    }
    public String getImgPath() {
        return imgPath;
    }
    public String getLicensePath() {
        return licensePath;
    }
    public void setLicensePath(String licensePath) {
        this.licensePath = licensePath;
    }
}
