package com.czareg.session.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionUser {
    private int sessionId;
    private String name;
}