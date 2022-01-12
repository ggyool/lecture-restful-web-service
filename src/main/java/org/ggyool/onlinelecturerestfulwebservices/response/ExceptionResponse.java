package org.ggyool.onlinelecturerestfulwebservices.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private final Date timeStamp;
    private final String message;
    private final String details;
}
