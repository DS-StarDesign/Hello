package ds.app.cs24rider.Models.Auths;

import com.google.gson.annotations.SerializedName;

/*
 *  Created By MD. OLI ULLAH DEWAN 12-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public class User {
    @SerializedName("id")
    private String id;
    @SerializedName("username")
    private String username;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("nid")
    private String nid;

    @SerializedName("address")
    private String address;

    @SerializedName("division")
    private String division;

    @SerializedName("district")
    private String district;

    @SerializedName("upozela")
    private String upozila;

    @SerializedName("branch_type")
    private String branchType;

    @SerializedName("sub_dealer")
    private String subDealer;

    @SerializedName("zone_id")
    private String zoneId;

    @SerializedName("dipu_id")
    private String dipuId;

    @SerializedName("affilate")
    private String affilate;

    @SerializedName("email")
    private String email;

    @SerializedName("email_add")
    private String emailAdd;

    @SerializedName("password")
    private String password;

    @SerializedName("salt")
    private String salt;

    @SerializedName("cID")
    private String cID;

    @SerializedName("type")
    private String type;

    @SerializedName("member_type")
    private String memberType;

    @SerializedName("id_type")
    private String idType;

    @SerializedName("id_number")
    private String idNumber;

    @SerializedName("id_front")
    private String idFront;

    @SerializedName("id_back")
    private String idBack;

    @SerializedName("my_pic")
    private String myPic;

    @SerializedName("trade")
    private String trade;

    @SerializedName("branch")
    private String branch;

    @SerializedName("soft_type")
    private String softType;

    @SerializedName("date")
    private String date;

    @SerializedName("date_e")
    private String dateE;

    @SerializedName("joining_ac")
    private String joiningAc;

    @SerializedName("reff")
    private String reff;

    @SerializedName("ref_value")
    private String refValue;

    @SerializedName("up_date")
    private String upDate;

    @SerializedName("activity")
    private String activity;


    public User() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getUpozila() {
        return upozila;
    }

    public void setUpozila(String upozila) {
        this.upozila = upozila;
    }

    public String getBranchType() {
        return branchType;
    }

    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    public String getSubDealer() {
        return subDealer;
    }

    public void setSubDealer(String subDealer) {
        this.subDealer = subDealer;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getDipuId() {
        return dipuId;
    }

    public void setDipuId(String dipuId) {
        this.dipuId = dipuId;
    }

    public String getAffilate() {
        return affilate;
    }

    public void setAffilate(String affilate) {
        this.affilate = affilate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailAdd() {
        return emailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdFront() {
        return idFront;
    }

    public void setIdFront(String idFront) {
        this.idFront = idFront;
    }

    public String getIdBack() {
        return idBack;
    }

    public void setIdBack(String idBack) {
        this.idBack = idBack;
    }

    public String getMyPic() {
        return myPic;
    }

    public void setMyPic(String myPic) {
        this.myPic = myPic;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSoftType() {
        return softType;
    }

    public void setSoftType(String softType) {
        this.softType = softType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateE() {
        return dateE;
    }

    public void setDateE(String dateE) {
        this.dateE = dateE;
    }

    public String getJoiningAc() {
        return joiningAc;
    }

    public void setJoiningAc(String joiningAc) {
        this.joiningAc = joiningAc;
    }

    public String getReff() {
        return reff;
    }

    public void setReff(String reff) {
        this.reff = reff;
    }

    public String getRefValue() {
        return refValue;
    }

    public void setRefValue(String refValue) {
        this.refValue = refValue;
    }

    public String getUpDate() {
        return upDate;
    }

    public void setUpDate(String upDate) {
        this.upDate = upDate;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String[] getKeys(){
        String[] keys = {
            "id", "username", "mobile", "nid", "address", "division", "district", "upozela", "branch_type", "sub_dealer", 
            "zone_id", "dipu_id", "affilate", "email", "email_add", "password", "salt", "cID", "type", "member_type", "id_type", 
            "id_number", "id_front", "id_back", "my_pic", "trade", "branch", "soft_type", "date", "date_e", "joining_ac",
            "reff", "ref_value", "up_date", "activity"
        };
        return keys;
    }
    
    public String getValueByKey(String key) {
        String value = "";
        switch (key){
            case "id": value = getId(); break;
            case "username" : value = getUsername(); break;
            case "mobile" : value = getMobile(); break;
            case "nid" : value = getNid(); break;
            case "address" : value = getAddress(); break;
            case "division" : value = getDivision(); break;
            case "district" : value = getDistrict(); break;
            case "upozela" : value = getUpozila(); break;
            case "branch_type" : value = getBranchType(); break;
            case "sub_dealer" : value = getSubDealer(); break;
            case "zone_id" : value = getZoneId(); break;
            case "dipu_id" : value = getDipuId(); break;
            case "affilate" : value = getAffilate(); break;
            case "email" : value = getEmail(); break;
            case "email_add" : value = getEmailAdd(); break;
            case "password" : value = getPassword(); break;
            case "salt" : value = getSalt(); break;
            case "cID" : value = getcID(); break;
            case "type" : value = getType(); break;
            case "member_type" : value = getMemberType(); break;
            case "id_type" : value = getIdType(); break;
            case "id_number" : value = getIdNumber(); break;
            case "id_front" : value = getIdFront(); break;
            case "id_back" : value = getIdBack(); break;
            case "my_pic" : value = getMyPic(); break;
            case "trade" : value = getTrade(); break;
            case "branch" : value = getBranch(); break;
            case "soft_type" : value = getSoftType(); break;
            case "date" : value = getDate(); break;
            case "date_e" : value = getDateE(); break;
            case "joining_ac" : value = getJoiningAc(); break;
            case "reff" : value = getReff(); break;
            case "ref_value" : value = getRefValue(); break;
            case "up_date" : value = getUpDate(); break;
            case "activity" : value = getActivity(); break;
            default: value = "";
        }
        return value;
    }
}
