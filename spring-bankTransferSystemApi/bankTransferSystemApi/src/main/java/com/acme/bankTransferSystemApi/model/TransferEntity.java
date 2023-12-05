package com.acme.bankTransferSystemApi.model;

import jakarta.persistence.*;

@Entity
@Table(name="transfers")
public class TransferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable=false)
    private String userId;

    @Column(length=10, nullable=false)
    private String sourceAccount;

    @Column(length=10, nullable=false)
    private String destinationAccount;

    @Column(length=7, nullable=false)
    private String amount;

    @Column(length=7)
    private String fee;

    @Column(name="TRANSFER_DATE", length=11, nullable=false)
    private String dateToTransfer;

    @Column(length=11, nullable=false)
    private String scheduleDate;


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
