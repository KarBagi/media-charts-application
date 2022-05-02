public class EndPoint {
    private String postCode,city,street,buildingNumber,flatNumber;
    private MediaType mediaType;
    public EndPoint(String postCode, String city, String street, String buildingNumber,String flatNumber,MediaType mediaType){
        this.postCode = postCode;
        this.city = city;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.flatNumber = flatNumber;
        this.mediaType = mediaType;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    @Override

    public String toString(){
        return this.postCode+", "+this.city+", "+this.street+", "+this.buildingNumber+"/"+this.flatNumber+", "+this.mediaType;
    }
}