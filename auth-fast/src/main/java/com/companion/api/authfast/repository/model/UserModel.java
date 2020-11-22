package com.companion.api.authfast.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "USERNAME",
        name = "USERNAME_UNIQUE_CONSTRAINT"))
public class UserModel implements Persistable<String> {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String userId;
    private String username;
    private String password;

    @Override
    @Transient
    public String getId() {
        return null;
    }

    @Override
    @Transient
    public boolean isNew() {
        return true;
    }
}
