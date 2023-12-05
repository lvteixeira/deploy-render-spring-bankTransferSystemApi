package com.acme.bankTransferSystemApi.dto;

public class TransferDTO {
    private Long id;
    private String userId;
    private String sourceAccount;
    private String destinationAccount;
    private String amount;
    private String fee;
    private String dateToTransfer;
    private String scheduleDate;

    public TransferDTO() {}

    public TransferDTO(Long id, String userId, String sourceAccount, String destinationAccount, String fee, String amount, String dateToTransfer, String scheduleDate) {
        this.id = id;
        this.userId = userId;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
        this.fee = fee;
        this.dateToTransfer = dateToTransfer;
        this.scheduleDate = scheduleDate;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getSourceAccount() { return sourceAccount; }

    public void setSourceAccount(String sourceAccount) { this.sourceAccount = sourceAccount; }

    public String getDestinationAccount() { return destinationAccount; }

    public void setDestinationAccount(String destinationAccount) { this.destinationAccount = destinationAccount; }

    public String getAmount() { return amount; }

    public void setAmount(String amount) { this.amount = amount; }

    public String getFee() { return fee; }

    public void setFee(String fee) { this.fee = fee; }

    public String getDateToTransfer() { return dateToTransfer; }

    public void setDateToTransfer(String dateToTransfer) { this.dateToTransfer = dateToTransfer; }

    public String getScheduleDate() { return scheduleDate; }

    public void setScheduleDate(String scheduleDate) { this.scheduleDate = scheduleDate; }
}
