package com.library.services.db.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public abstract class BaseDTO implements Serializable {
    protected static final long serialVersionUID = 1234L;

    private String id;
    private int status;
    @JsonIgnore
    private Date createdAt;
    @JsonIgnore
    private Date modifiedAt;

    @JsonIgnore
    public boolean isActive() {
        return this.status == 1;
    }
}
