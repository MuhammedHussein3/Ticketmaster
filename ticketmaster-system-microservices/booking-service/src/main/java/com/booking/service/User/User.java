package com.booking.service.User;

import lombok.*;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {

    private String firsName;

    private String lastName;

    private int age;
}
