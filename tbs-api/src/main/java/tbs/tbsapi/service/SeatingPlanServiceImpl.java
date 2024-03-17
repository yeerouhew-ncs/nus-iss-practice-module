package tbs.tbsapi.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbs.tbsapi.domain.Seat;
import tbs.tbsapi.domain.SeatingPlan;
import tbs.tbsapi.domain.SectionSeat;
import tbs.tbsapi.domain.Venue;
import tbs.tbsapi.repository.SeatingPlanRepository;
import tbs.tbsapi.repository.SectionSeatRepository;
import tbs.tbsapi.repository.VenueRepository;
import tbs.tbsapi.vo.request.GetSeatingPlanRequest;
import tbs.tbsapi.vo.response.GetSeatingPlanResponse;

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

    public List<GetSeatingPlanResponse> getListofSeatingPlans() {
        List<SeatingPlan> seatingPlanList = seatingPlanRepository.findAll();

        List<GetSeatingPlanResponse> seatingPlanResponseList = new ArrayList<>();
        for(SeatingPlan seatingPlan: seatingPlanList) {
            Venue venue = venueRepository.findByVenueId(seatingPlan.getVenueId());
            List<SectionSeat> sectionSeat = sectionSeatRepository.findAllByPlanId(seatingPlan.getPlanId());

            GetSeatingPlanResponse seatingPlanResponse = new GetSeatingPlanResponse();
            seatingPlanResponse.setPlanId(seatingPlan.getPlanId());
            seatingPlanResponse.setVenueId(venue.getVenueId());
            seatingPlanResponse.setVenueName(venue.getVenueName());
            seatingPlanResponse.setAddress(venue.getAddress());
            seatingPlanResponse.setSectionSeatResponses(sectionSeat);

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
        seatingPlanResponse.setVenueId(venue.getVenueId());
        seatingPlanResponse.setVenueName(venue.getVenueName());
        seatingPlanResponse.setAddress(venue.getAddress());
        seatingPlanResponse.setSectionSeatResponses(sectionSeat);

        return seatingPlanResponse;
    }
}
