package com.xaiaosi.i18n.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "i18n_msg")
@Data
public class I18nMsg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "locale",length = 50)
    private String locale;

    @Column(name = "code",length = 50)
    private String code;

    @Column(name = "msg",length = 50)
    private String msg;
}
