package com.booking.service.dto.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ReserveRequest{
        private Integer userId;
        private Long eventId;
        private List<Long> ticketIds;
}
