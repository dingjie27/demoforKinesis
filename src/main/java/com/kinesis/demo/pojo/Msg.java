package com.kinesis.demo.pojo;

import java.util.Date;

public class Msg {
    Date maintenance_end_date;
    String model_id="007db43c18be4b0098f26886510d13b9";
    String miner_farm_id="59fe4dbc";
    int repair_order_id=22907;
    String brand_id="AntMiner";
    int chip_count=180;
    String unit="G";
    String customer_email="shaohua.wang@bitmain.com";
    int site_id=5;
    String computing_power="22000.0000";
    String sn="FXDZ47BAHABBF07N3";
    int board_count=3;
    String miner_type="ASIC";

    public Date getMaintenance_end_date() {
        return maintenance_end_date;
    }

    public void setMaintenance_end_date(Date maintenance_end_date) {
        this.maintenance_end_date = maintenance_end_date;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public String getMiner_farm_id() {
        return miner_farm_id;
    }

    public void setMiner_farm_id(String miner_farm_id) {
        this.miner_farm_id = miner_farm_id;
    }

    public int getRepair_order_id() {
        return repair_order_id;
    }

    public void setRepair_order_id(int repair_order_id) {
        this.repair_order_id = repair_order_id;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public int getChip_count() {
        return chip_count;
    }

    public void setChip_count(int chip_count) {
        this.chip_count = chip_count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public int getSite_id() {
        return site_id;
    }

    public void setSite_id(int site_id) {
        this.site_id = site_id;
    }

    public String getComputing_power() {
        return computing_power;
    }

    public void setComputing_power(String computing_power) {
        this.computing_power = computing_power;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getBoard_count() {
        return board_count;
    }

    public void setBoard_count(int board_count) {
        this.board_count = board_count;
    }

    public String getMiner_type() {
        return miner_type;
    }

    public void setMiner_type(String miner_type) {
        this.miner_type = miner_type;
    }
}

