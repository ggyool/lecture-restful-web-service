package org.ggyool.onlinelecturerestfulwebservices.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value = {"password", "ssn"})
//@JsonFilter("UserInfo")
@Entity
public class User {

    @Id
    @GeneratedValue
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

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public User(int id, String name, Date joinDate, String password, String ssn) {
        this(id, name, joinDate, password, ssn, new ArrayList<>());
    }
}
