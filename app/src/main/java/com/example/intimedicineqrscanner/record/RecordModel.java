package com.example.intimedicineqrscanner.record;


import com.google.firebase.Timestamp;

public class RecordModel {
    int numOfData;
    String entry, studentId, temperature, recordId, status, name;
    Timestamp checkIn;

    public RecordModel() {
    }

    public RecordModel(int numOfData, String entry, String studentId, String temperature,
                       String recordId, String status, String name, Timestamp checkIn) {
        this.numOfData = numOfData;
        this.entry = entry;
        this.studentId = studentId;
        this.temperature = temperature;
        this.recordId = recordId;
        this.status = status;
        this.name = name;
        this.checkIn = checkIn;
    }

    public int getNumOfData() {
        return numOfData;
    }

    public void setNumOfData(int numOfData) {
        this.numOfData = numOfData;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Timestamp checkIn) {
        this.checkIn = checkIn;
    }
}
