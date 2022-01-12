package org.ggyool.onlinelecturerestfulwebservices.domain;


import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonFilter("UserInfoV2")
public class UserV2 extends User {

    private String grade;

    public UserV2(Integer id, String name, Date joinDate, String password, String ssn, String grade) {
        super(id, name, joinDate, password, ssn);
        this.grade = grade;
    }
}
