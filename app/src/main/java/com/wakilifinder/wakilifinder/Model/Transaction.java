package com.wakilifinder.wakilifinder.Model;

public class Transaction {

    private String id, status, type, currencyIsoCode,amount,merchantAccountId,subMerchantAccountId;
    private String masterMerchantAccountId, orderId, createdAt,updatedAt;

    public Transaction() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrencyIsoCode() {
        return currencyIsoCode;
    }

    public void setCurrencyIsoCode(String currencyIsoCode) {
        this.currencyIsoCode = currencyIsoCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMerchantAccountId() {
        return merchantAccountId;
    }

    public void setMerchantAccountId(String merchantAccountId) {
        this.merchantAccountId = merchantAccountId;
    }

    public String getSubMerchantAccountId() {
        return subMerchantAccountId;
    }

    public void setSubMerchantAccountId(String subMerchantAccountId) {
        this.subMerchantAccountId = subMerchantAccountId;
    }

    public String getMasterMerchantAccountId() {
        return masterMerchantAccountId;
    }

    public void setMasterMerchantAccountId(String masterMerchantAccountId) {
        this.masterMerchantAccountId = masterMerchantAccountId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
