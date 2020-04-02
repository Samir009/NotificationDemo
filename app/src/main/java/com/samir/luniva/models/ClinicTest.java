package com.samir.luniva.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ClinicTest {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("ClinicId")
    @Expose
    private Integer clinicId;
    @SerializedName("TestId")
    @Expose
    private Integer testId;
    @SerializedName("Price")
    @Expose
    private Double price;
    @SerializedName("TotalPrice")
    @Expose
    private Double totalPrice;
    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("CreatedBy")
    @Expose
    private Integer createdBy;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("test")
    @Expose
    private Test test;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }


    public class Test {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("TestCode")
        @Expose
        private String testCode;
        @SerializedName("Testname")
        @Expose
        private String testname;
        @SerializedName("Price")
        @Expose
        private Double price;
        @SerializedName("TotalPrice")
        @Expose
        private Double totalPrice;
        @SerializedName("Specimen")
        @Expose
        private String specimen;
        @SerializedName("Method")
        @Expose
        private String method;
        @SerializedName("Schedule")
        @Expose
        private String schedule;
        @SerializedName("Reporting")
        @Expose
        private String reporting;
        @SerializedName("Units")
        @Expose
        private String units;
        @SerializedName("IsOutGoingTest")
        @Expose
        private Boolean isOutGoingTest;
        @SerializedName("SubGroupId")
        @Expose
        private Boolean subGroupId;
        @SerializedName("SubGroupType")
        @Expose
        private String subGroupType;
        @SerializedName("IsCulture")
        @Expose
        private Boolean isCulture;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("EntryDate")
        @Expose
        private String entryDate;
        @SerializedName("IsActive")
        @Expose
        private Integer isActive;
        @SerializedName("TestType")
        @Expose
        private String testType;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTestCode() {
            return testCode;
        }

        public void setTestCode(String testCode) {
            this.testCode = testCode;
        }

        public String getTestname() {
            return testname;
        }

        public void setTestname(String testname) {
            this.testname = testname;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getSpecimen() {
            return specimen;
        }

        public void setSpecimen(String specimen) {
            this.specimen = specimen;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getSchedule() {
            return schedule;
        }

        public void setSchedule(String schedule) {
            this.schedule = schedule;
        }

        public String getReporting() {
            return reporting;
        }

        public void setReporting(String reporting) {
            this.reporting = reporting;
        }

        public String getUnits() {
            return units;
        }

        public void setUnits(String units) {
            this.units = units;
        }

        public Boolean getIsOutGoingTest() {
            return isOutGoingTest;
        }

        public void setIsOutGoingTest(Boolean isOutGoingTest) {
            this.isOutGoingTest = isOutGoingTest;
        }

        public Boolean getSubGroupId() {
            return subGroupId;
        }

        public void setSubGroupId(Boolean subGroupId) {
            this.subGroupId = subGroupId;
        }

        public String getSubGroupType() {
            return subGroupType;
        }

        public void setSubGroupType(String subGroupType) {
            this.subGroupType = subGroupType;
        }

        public Boolean getIsCulture() {
            return isCulture;
        }

        public void setIsCulture(Boolean isCulture) {
            this.isCulture = isCulture;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getEntryDate() {
            return entryDate;
        }

        public void setEntryDate(String entryDate) {
            this.entryDate = entryDate;
        }

        public Integer getIsActive() {
            return isActive;
        }

        public void setIsActive(Integer isActive) {
            this.isActive = isActive;
        }

        public String getTestType() {
            return testType;
        }

        public void setTestType(String testType) {
            this.testType = testType;
        }

    }
}
