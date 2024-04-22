package tbs.tbsapi.service;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbs.tbsapi.domain.Seat;
import tbs.tbsapi.domain.SeatingPlan;
import tbs.tbsapi.domain.SectionSeat;
import tbs.tbsapi.domain.Venue;
import tbs.tbsapi.domain.enums.SeatStatus;
import tbs.tbsapi.dto.*;
import tbs.tbsapi.repository.SeatRepository;
import tbs.tbsapi.repository.SeatingPlanRepository;
import tbs.tbsapi.repository.SectionSeatRepository;
import tbs.tbsapi.repository.VenueRepository;
import tbs.tbsapi.vo.request.GetSeatingPlanRequest;
import tbs.tbsapi.vo.response.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class SeatingPlanServiceImpl implements SeatingPlanService {
    @Autowired
    SeatingPlanRepository seatingPlanRepository;
    @Autowired
    VenueRepository venueRepository;
    @Autowired
    SectionSeatRepository sectionSeatRepository;
    @Autowired
    SeatRepository seatRepository;

    public AddSeatingPlanResponse addSeatingPlan(AddSeatingPlanDto seatingPlanDto) {
        SeatingPlan preSaveSeatingPlan = SeatingPlan.builder()
                .venueId(seatingPlanDto.getVenueId())
                .planName(seatingPlanDto.getPlanName())
                .planRow(seatingPlanDto.getPlanRow())
                .planCol(seatingPlanDto.getPlanCol())
                .build();

        SeatingPlan saveSeatingPlan = seatingPlanRepository.save(preSaveSeatingPlan);

        List<SectionSeatResponse> sectionSeatResponseList = new ArrayList<>();

        for(SectionSeatDto sectionSeat : seatingPlanDto.getSectionSeats()) {
            SectionSeat preSaveSectionSeat = SectionSeat.builder()
                    .planId(saveSeatingPlan.getPlanId())
                    .totalSeats(sectionSeat.getTotalSeats())
                    .noSeatsLeft(sectionSeat.getTotalSeats())
                    .seatPrice(sectionSeat.getSeatPrice())
                    .seatSectionDescription(sectionSeat.getSectionDesc())
                    .sectionRow(sectionSeat.getSectionRow())
                    .sectionCol(sectionSeat.getSectionCol())
                    .build();

            SectionSeat saveSectionSeat = sectionSeatRepository.save(preSaveSectionSeat);

            SectionSeatResponse sectionSeatResponse = new SectionSeatResponse();
            sectionSeatResponse.setSectionId(saveSectionSeat.getSectionId());
            sectionSeatResponse.setTotalSeats(saveSectionSeat.getTotalSeats());
            sectionSeatResponse.setNoSeatsLeft(saveSectionSeat.getNoSeatsLeft());
            sectionSeatResponse.setSectionDesc(saveSectionSeat.getSeatSectionDescription());
            sectionSeatResponse.setSectionRow(saveSectionSeat.getSectionRow());
            sectionSeatResponse.setSectionCol(saveSectionSeat.getSectionCol());
            sectionSeatResponse.setSeatPrice(saveSectionSeat.getSeatPrice());

            List<SeatResponse> seatList = new ArrayList<>();

            for(SeatDto seat: sectionSeat.getSeats()) {
                Seat preSaveSeat = Seat.builder()
                        .seatName(seat.getSeatName())
                        .seatRow(seat.getSeatRow())
                        .seatCol(seat.getSeatCol())
                        .seatStatus(SeatStatus.available)
                        .sectionId(saveSectionSeat.getSectionId())
                        .build();

                Seat saveSeat = seatRepository.save(preSaveSeat);

                SeatResponse seatResponse = new SeatResponse();
                seatResponse.setSeatId(saveSeat.getSeatId());
                seatResponse.setSeatName(saveSeat.getSeatName());
                seatResponse.setSeatStatus(saveSeat.getSeatStatus());
                seatResponse.setSeatRow(saveSeat.getSeatRow());
                seatResponse.setSeatCol(saveSeat.getSeatCol());
                seatList.add(seatResponse);
            }

            sectionSeatResponse.setSeatList(seatList);
            sectionSeatResponseList.add(sectionSeatResponse);
        }

        AddSeatingPlanResponse seatingPlanResponse = new AddSeatingPlanResponse();
        seatingPlanResponse.setStatusCode("200");
        seatingPlanResponse.setMessage("SUCCESS");
        seatingPlanResponse.setPlanId(saveSeatingPlan.getPlanId());
        seatingPlanResponse.setVenueId(saveSeatingPlan.getVenueId());

        seatingPlanResponse.setSectionSeatResponseList(sectionSeatResponseList);

        return seatingPlanResponse;
    }

    @Transactional
    public List<String> editSeatingPlan(EditSeatingPlanDto seatingPlanDto) {
        List<String> response = new ArrayList<>();

        if(seatingPlanRepository.findByPlanId(seatingPlanDto.getPlanId()) == null) {
            response.add("200");
            response.add("PLAN DOES NOT EXIST");
            return response;
        }

        log.info("seatingPlanDto: {} ", seatingPlanDto);
        Integer updatedPlan = seatingPlanRepository.updatePlan(
                seatingPlanDto.getPlanId(),
                seatingPlanDto.getPlanName(),
                seatingPlanDto.getPlanRow(),
                seatingPlanDto.getPlanCol(),
                seatingPlanDto.getVenueId());

        if(updatedPlan == 1) {
            for(EditSectionSeatDto sectionSeat : seatingPlanDto.getSectionSeats()) {
                log.info("sectionSeat.getSectionId() " + sectionSeat.getSectionId());
                Integer updateSectionSeat = 0;
                SectionSeat saveSectionSeat = null;
                Integer sectionId = sectionSeat.getSectionId();
                // if section id does not exist
                if(sectionSeat.getSectionId() == null) {
                    SectionSeat preSaveSectionSeat = SectionSeat.builder()
                            .planId(seatingPlanDto.getPlanId())
                            .totalSeats(sectionSeat.getTotalSeats())
                            .noSeatsLeft(sectionSeat.getTotalSeats())
                            .seatPrice(sectionSeat.getSeatPrice())
                            .seatSectionDescription(sectionSeat.getSectionDesc())
                            .sectionRow(sectionSeat.getSectionRow())
                            .sectionCol(sectionSeat.getSectionCol())
                            .build();
                    saveSectionSeat = sectionSeatRepository.save(preSaveSectionSeat);
                    sectionId = saveSectionSeat.getSectionId();
                } else {
                    updateSectionSeat = sectionSeatRepository.updateSectionSeat(
                            sectionSeat.getSectionId(),
                            seatingPlanDto.getPlanId(),
                            sectionSeat.getTotalSeats(),
                            sectionSeat.getTotalSeats(),
                            sectionSeat.getSeatPrice(),
                            sectionSeat.getSectionDesc(),
                            sectionSeat.getSectionRow(),
                            sectionSeat.getSectionCol()
                    );
                }
                log.info("sectionSeatId " + sectionSeat.getSectionId());

                if(updateSectionSeat != 1 && saveSectionSeat == null) {
                    response.add("400");
                    response.add("SECTION SEAT NOT UPDATED");
                    return response;
                }

                log.info("sectionSeat seats: {} ", sectionSeat.getSeats());
                // delete all the seats associated and add back
                if(sectionSeat.getSectionId() != null) {
                    log.info("SECTION ID EMPTY " + sectionId);
//                    seatRepository.deleteBySectionId(sectionId);
                    log.info("SECTION ID NOT EMPTY " + sectionId);
                }

                for(EditSeatDto seat: sectionSeat.getSeats()) {
                    Seat createdSeat = null;
                    Integer updateSeat = 0;

                    if(seat.getSeatId() == null) {
                        Seat preSaveSeat = Seat.builder()
                                .seatName(seat.getSeatName())
                                .seatRow(seat.getSeatRow())
                                .seatCol(seat.getSeatCol())
                                .seatStatus(SeatStatus.available)
                                .sectionId(sectionId)
                                .build();
                        log.info("preSaveSeat {} ", preSaveSeat);
                        createdSeat = seatRepository.save(preSaveSeat);
                    } else {
                        updateSeat = seatRepository.updateSeat(
                                seat.getSeatId(),
                                seat.getSeatName(),
                                seat.getSeatRow(),
                                seat.getSeatCol(),
                                seat.getSeatStatus(),
                                sectionId
                        );
                    }

                    if(createdSeat == null && updateSeat != 1) {
                        response.add("400");
                        response.add("SEAT NOT UPDATED");
                        return response;
                    }
                }
            }
            response.add("200");
            response.add("SUCCESS");
            return response;
        }
        response.add("400");
        response.add("Plan not updated");
        return response;
    }

    @Transactional
    public List<String> editPlanCategory(EditPlanSectionSeatDto editPlanSectionSeatDto) {
        List<String> response = new ArrayList<>();

        if(seatingPlanRepository.findByPlanId(editPlanSectionSeatDto.getPlanId()) == null) {
            response.add("200");
            response.add("PLAN DOES NOT EXIST");
            return response;
        }

        log.info("EditPlanSectionSeatDto {}", editPlanSectionSeatDto);
        for(EditSectionSeatDto sectionSeat : editPlanSectionSeatDto.getSectionSeats()) {
            Integer updateSectionSeat = 0;
            SectionSeat saveSectionSeat = null;
            Integer sectionId = sectionSeat.getSectionId();

            log.info("test test sectionSeat{} ", sectionSeat);
            if(sectionSeat.getSectionId() == null) {
                SectionSeat preSaveSectionSeat = SectionSeat.builder()
                        .planId(editPlanSectionSeatDto.getPlanId())
                        .totalSeats(sectionSeat.getTotalSeats())
                        .noSeatsLeft(sectionSeat.getTotalSeats())
                        .seatPrice(sectionSeat.getSeatPrice())
                        .seatSectionDescription(sectionSeat.getSectionDesc())
                        .sectionRow(sectionSeat.getSectionRow())
                        .sectionCol(sectionSeat.getSectionCol())
                        .build();
                saveSectionSeat = sectionSeatRepository.save(preSaveSectionSeat);
                sectionId = saveSectionSeat.getSectionId();
            } else {
                updateSectionSeat = sectionSeatRepository.updateSectionSeat(
                        sectionSeat.getSectionId(),
                        editPlanSectionSeatDto.getPlanId(),
                        sectionSeat.getTotalSeats(),
                        sectionSeat.getTotalSeats(),
                        sectionSeat.getSeatPrice(),
                        sectionSeat.getSectionDesc(),
                        sectionSeat.getSectionRow(),
                        sectionSeat.getSectionCol()
                );
            }

            if(updateSectionSeat != 1 && saveSectionSeat == null) {
                response.add("400");
                response.add("SECTION SEAT NOT UPDATED");
                return response;
            }
//
//            // delete all the seats associated and add back
//            if(sectionSeat.getSectionId() != null) {
//                seatRepository.deleteBySectionId(sectionId);
//            }
//
            for(EditSeatDto seat: sectionSeat.getSeats()) {
//                Seat preSaveSeat = Seat.builder()
//                        .seatName(seat.getSeatName())
//                        .seatStatus(SeatStatus.available)
//                        .sectionId(sectionId)
//                        .build();
//                log.info("preSaveSeat {} ", preSaveSeat);
//                Seat updateSeat = seatRepository.save(preSaveSeat);
//
//                if(updateSeat == null) {
//                    response.add("400");
//                    response.add("SEAT NOT UPDATED");
//                    return response;
//                }
                Seat createdSeat = null;
                Integer updateSeat = 0;

                log.info("test test seat {} ", seat);
                if(seat.getSeatId() == null) {
                    Seat preSaveSeat = Seat.builder()
                            .seatName(seat.getSeatName())
                            .seatRow(seat.getSeatRow())
                            .seatCol(seat.getSeatCol())
                            .seatStatus(SeatStatus.available)
                            .sectionId(sectionId)
                            .build();
                    log.info("preSaveSeat {} ", preSaveSeat);
                    createdSeat = seatRepository.save(preSaveSeat);
                } else {

                    updateSeat = seatRepository.updateSeat(
                            seat.getSeatId(),
                            seat.getSeatName(),
                            seat.getSeatRow(),
                            seat.getSeatCol(),
                            seat.getSeatStatus(),
                            sectionId
                    );
                }

                log.info("createdSeat {} ", createdSeat);
                log.info("updatedSeat", updateSeat);

                if(createdSeat == null && updateSeat != 1) {
                    response.add("400");
                    response.add("SEAT NOT UPDATED");
                    return response;
                }
            }
        }
        response.add("200");
        response.add("SUCCESS");
        return response;
    }

    public List<GetSeatingPlanResponse> getListofSeatingPlans() {
        List<SeatingPlan> seatingPlanList = seatingPlanRepository.findAll();

        List<GetSeatingPlanResponse> seatingPlanResponseList = new ArrayList<>();
        for(SeatingPlan seatingPlan: seatingPlanList) {
            Venue venue = venueRepository.findByVenueId(seatingPlan.getVenueId());
            List<SectionSeat> sectionSeat = sectionSeatRepository.findAllByPlanId(seatingPlan.getPlanId());

            GetSeatingPlanResponse seatingPlanResponse = new GetSeatingPlanResponse();
            seatingPlanResponse.setPlanId(seatingPlan.getPlanId());
            seatingPlanResponse.setPlanName(seatingPlan.getPlanName());
            seatingPlanResponse.setPlanRow(seatingPlan.getPlanRow());
            seatingPlanResponse.setPlanCol(seatingPlan.getPlanCol());
            seatingPlanResponse.setVenueId(venue.getVenueId());
            seatingPlanResponse.setVenueName(venue.getVenueName());
            seatingPlanResponse.setAddress(venue.getAddress());

            List<GetSectionSeatResponse> sectionSeatResponsesList = new ArrayList<>();

            for(SectionSeat category: sectionSeat) {
                GetSectionSeatResponse sectionSeatResponse = new GetSectionSeatResponse();
                sectionSeatResponse.setSeatPrice(category.getSeatPrice());
                sectionSeatResponse.setTotalSeats(category.getTotalSeats());
                sectionSeatResponse.setSeatSectionDescription(category.getSeatSectionDescription());
                sectionSeatResponse.setSectionCol(category.getSectionCol());
                sectionSeatResponse.setSectionRow(category.getSectionRow());
                sectionSeatResponse.setNoSeatsLeft(category.getNoSeatsLeft());
                sectionSeatResponse.setSectionId(category.getSectionId());

                List<Seat> seats = seatRepository.findAllBySectionId(category.getSectionId());
                sectionSeatResponse.setSeatResponses(seats);

                sectionSeatResponsesList.add(sectionSeatResponse);
            }

            seatingPlanResponse.setSectionSeatResponses(sectionSeatResponsesList);
            seatingPlanResponseList.add(seatingPlanResponse);
        }

        return seatingPlanResponseList;
    }

    public GetSeatingPlanResponse getSeatingPlanDetails(GetSeatingPlanRequest getSeatingPlanRequest) {
        SeatingPlan plan = seatingPlanRepository.findByPlanId(getSeatingPlanRequest.getPlanId());
        Venue venue = venueRepository.findByVenueId(plan.getVenueId());
        List<SectionSeat> sectionSeat = sectionSeatRepository.findAllByPlanId(getSeatingPlanRequest.getPlanId());

        GetSeatingPlanResponse seatingPlanResponse = new GetSeatingPlanResponse();
        seatingPlanResponse.setPlanId(plan.getPlanId());
        seatingPlanResponse.setPlanName(plan.getPlanName());
        seatingPlanResponse.setPlanRow(plan.getPlanRow());
        seatingPlanResponse.setPlanCol(plan.getPlanCol());
        seatingPlanResponse.setVenueId(venue.getVenueId());
        seatingPlanResponse.setVenueName(venue.getVenueName());
        seatingPlanResponse.setAddress(venue.getAddress());

        List<GetSectionSeatResponse> sectionSeatResponsesList = new ArrayList<>();

        for(SectionSeat category: sectionSeat) {
            GetSectionSeatResponse sectionSeatResponse = new GetSectionSeatResponse();
            sectionSeatResponse.setSeatPrice(category.getSeatPrice());
            sectionSeatResponse.setTotalSeats(category.getTotalSeats());
            sectionSeatResponse.setSeatSectionDescription(category.getSeatSectionDescription());
            sectionSeatResponse.setSectionCol(category.getSectionCol());
            sectionSeatResponse.setSectionRow(category.getSectionRow());
            sectionSeatResponse.setNoSeatsLeft(category.getNoSeatsLeft());
            sectionSeatResponse.setSectionId(category.getSectionId());

            List<Seat> seats = seatRepository.findAllBySectionId(category.getSectionId());
            log.info("seats {}",seats);
            sectionSeatResponse.setSeatResponses(seats);

            sectionSeatResponsesList.add(sectionSeatResponse);
        }

        seatingPlanResponse.setSectionSeatResponses(sectionSeatResponsesList);

        return seatingPlanResponse;
    }
}
