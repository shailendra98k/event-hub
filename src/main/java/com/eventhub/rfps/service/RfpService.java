package com.eventhub.rfps.service;

import com.eventhub.auth.dto.CustomUserDetails;
import com.eventhub.auth.entity.Role;
import com.eventhub.auth.entity.User;
import com.eventhub.auth.repository.UserRepository;
import com.eventhub.rfps.dto.RfpRequest;
import com.eventhub.rfps.entity.RFP_STATUS;
import com.eventhub.rfps.entity.Rfp;
import com.eventhub.rfps.repository.RfpRepository;
import com.eventhub.venueDiscovery.entity.Venue;
import com.eventhub.venueDiscovery.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.naming.AuthenticationException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class RfpService {
    @Autowired
    private RfpRepository rfpRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private UserRepository userRepository;

    public Rfp createRfp(RfpRequest request) throws AuthenticationException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            Long userId = userDetails.getUserId();
            Venue venue = venueRepository.findById(request.getVenueId()).orElseThrow(() -> new IllegalArgumentException("Venue not found"));
            User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
            Rfp rfp = Rfp.builder()
                    .venue(venue)
                    .eventDate(request.getEventDate())
                    .headcount(request.getHeadcount())
                    .budgetMin(request.getBudgetMin())
                    .budgetMax(request.getBudgetMax())
                    .buyer(user)
                    .notes(request.getNotes())
                    .status(RFP_STATUS.SUBMITTED.name())
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    .build();
            return rfpRepository.save(rfp);
        } else {
            throw new AuthenticationException("User not authenticated");
        }


    }


    public Optional<Rfp> getRfpById(Long rfpId) throws ChangeSetPersister.NotFoundException {


        Optional<Rfp> optionalRfp = rfpRepository.findById(rfpId);
        if (optionalRfp.isEmpty()) {
            throw new ChangeSetPersister.NotFoundException();
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            Long userId = userDetails.getUserId();
            Rfp rfp = optionalRfp.get();
            if (!rfp.getBuyer().getId().equals(userId)) {
                return Optional.empty();
            }
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }

        return rfpRepository.findById(rfpId);
    }

    public Page<Rfp> getRfps(int page, int size) throws AuthenticationException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            Long userId = userDetails.getUserId();
            PageRequest pageable = PageRequest.of(page, size);
            if(userDetails.getRole().equals(Role.VENUE_OWNER.name())) {
                List<Venue> venues = venueRepository.findByUserId(userId);
                List<Long> venueIds = venues.stream().map(Venue::getId).toList();
                return rfpRepository.findByVenueIdIn(venueIds, pageable);
            }
            return rfpRepository.findByBuyerId(userId, pageable);
        } else {
            throw new AuthenticationException("User not authenticated");
        }
    }
}
