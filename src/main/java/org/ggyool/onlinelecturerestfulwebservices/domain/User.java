package org.ggyool.onlinelecturerestfulwebservices.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value = {"password", "ssn"})
@JsonFilter("UserInfo")
public class User {

    private Integer id;

    @ApiModelProperty(notes = "사용자의 이름을 입력해 주세요.")
    @Size(min = 2, message = "Name 은 2글자 이상 입력해 주세요.")
    private String name;

    @ApiModelProperty(notes = "사용자의 등록일을 입력해 주세요.")
    @Past
    private Date joinDate;

    @ApiModelProperty(notes = "사용자의 비밀번호를 입력해 주세요.")
    private String password;

    @ApiModelProperty(notes = "사용자의 주민번호를 입력해 주세요.")
    private String ssn;
}
