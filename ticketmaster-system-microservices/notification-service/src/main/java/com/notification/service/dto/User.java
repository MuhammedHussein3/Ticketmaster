package com.notification.service.dto;

import lombok.*;

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
