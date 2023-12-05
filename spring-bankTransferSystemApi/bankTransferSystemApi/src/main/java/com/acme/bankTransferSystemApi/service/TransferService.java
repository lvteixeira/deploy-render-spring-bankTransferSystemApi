package com.acme.bankTransferSystemApi.service;

import com.acme.bankTransferSystemApi.dto.TransferDTO;
import com.acme.bankTransferSystemApi.exception.DateToTransferNotAllowedException;
import com.acme.bankTransferSystemApi.model.TransferEntity;
import com.acme.bankTransferSystemApi.repository.TransferRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class TransferService {
    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private ModelMapper modelMapper;

    public TransferService(TransferRepository transferRepository, ModelMapper modelMapper) {
        this.transferRepository = transferRepository;
        this.modelMapper = modelMapper;

        TypeMap<TransferDTO, TransferEntity> typeMap = this.modelMapper.createTypeMap(TransferDTO.class, TransferEntity.class);

        typeMap.addMappings(mapper -> mapper.skip(TransferEntity::setId));
        typeMap.addMappings(mapper -> mapper.skip(TransferEntity::setFee));
    }

    public TransferDTO convertEntityToDTO(TransferEntity entity) {
        return modelMapper.map(entity, TransferDTO.class);
    }

    public TransferEntity convertDTOToEntity(TransferDTO dto) {
        return modelMapper.map(dto, TransferEntity.class);
    }

    public List<TransferDTO> convertEntityListToDTOList(List<TransferEntity> entityList) {
        java.lang.reflect.Type targetListType = new TypeToken<List<TransferDTO>>() {
        }.getType();
        return modelMapper.map(entityList, targetListType);
    }

    public List<TransferEntity> getAll() {
        return transferRepository.findAll();
    }

    public Optional<TransferEntity> findById(Long id) {
        return transferRepository.findById(id);
    }

    public List<TransferEntity> findByUserId(String userId) { return transferRepository.findByUserId(userId); }

    public void create(TransferEntity transfer) throws Exception{
        this.parseTransfer(transfer);
        transferRepository.save(transfer);
        transferRepository.flush();
    }

    public void update(TransferEntity updated, Long id) {
        List<TransferEntity> transfers = this.getAll();
        transfers.stream()
                .filter(transfer -> transfer.getId().equals(id))
                .findFirst()
                .ifPresent(transfer -> {
                    transfer.setAmount(updated.getAmount());
                    transferRepository.flush();
                });
    }

    public void delete(Long id) {
        Optional<TransferEntity> transferToDelete = this.findById(id);
        transferToDelete.ifPresent(transfer -> transferRepository.deleteById(id));
        transferRepository.flush();
    }

    public void clear() {
        transferRepository.deleteAll();
        transferRepository.flush();
    }

    public void parseTransfer(TransferEntity transfer) throws Exception{
        String scheduleDate = transfer.getScheduleDate();
        String dateToTransfer = transfer.getDateToTransfer();

        int daysBetweenScheduleAndTransfer = this.getDaysBetweenScheduleAndTransfer(scheduleDate, dateToTransfer);

        if (daysBetweenScheduleAndTransfer == 0) {
            calculateFee(transfer, 3.00, 2.50);
        } else if (daysBetweenScheduleAndTransfer >= 1 && daysBetweenScheduleAndTransfer <= 10) {
            calculateFee(transfer, 12.00, 0.00);
        } else if (daysBetweenScheduleAndTransfer >= 11 && daysBetweenScheduleAndTransfer <= 20) {
            calculateFee(transfer, 0.00, 8.20);
        } else if (daysBetweenScheduleAndTransfer >= 21 && daysBetweenScheduleAndTransfer <= 30) {
            calculateFee(transfer, 0.00, 6.90);
        } else if (daysBetweenScheduleAndTransfer >= 31 && daysBetweenScheduleAndTransfer <= 40) {
            calculateFee(transfer, 0.00, 4.70);
        } else if (daysBetweenScheduleAndTransfer >= 41 && daysBetweenScheduleAndTransfer <= 50) {
            calculateFee(transfer, 0.00, 1.70);
        } else {
            throw new DateToTransferNotAllowedException("Transfer must be scheduled within 50 days.");
        }
    }

    public int getDaysBetweenScheduleAndTransfer(String scheduleDate, String dateToTransfer) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate scheduleDateLocalDate = LocalDate.parse(scheduleDate, formatter);
        LocalDate dateToTransferLocalDate = LocalDate.parse(dateToTransfer, formatter);

        long calculate = ChronoUnit.DAYS.between(scheduleDateLocalDate, dateToTransferLocalDate);
        System.out.println("Days Between: "+calculate);
        return (int) calculate;
    }

    public void calculateFee(TransferEntity transfer, double tax, double aliquot) {
        double fee = (((aliquot / 100) * Double.parseDouble(transfer.getAmount())) + tax);

        DecimalFormat df = new DecimalFormat("#.##");
        String formattedFee = df.format(fee);

        transfer.setFee(String.valueOf(formattedFee));
    }

}