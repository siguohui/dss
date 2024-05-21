package com.xiaosi.gongzhonghao.baseservice.oneword;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author kcaco
 * @since 2022-08-19 12:24
 */
@Data
@NoArgsConstructor
public class OneWordRes {

    /**
     * 	一言标识
     */
    private Integer id;

    /**
     * 一言唯一标识；可以链接到 https://hitokoto.cn?uuid=[uuid] (opens new window)查看这个一言的完整信息
     */
    private String uuid;

    /**
     * 一言正文。编码方式 unicode。使用 utf-8。
     */
    private String hitokoto;

    /**
     * 类型
     */
    private String type;

    /**
     * from
     */
    private String from;

    /**
     * 一言的作者
     */
    private String fromWho;

    @JsonProperty("creator")
    private String creator;
    @JsonProperty("creator_uid")
    private Integer creatorUid;
    @JsonProperty("reviewer")
    private Integer reviewer;
    @JsonProperty("commit_from")
    private String commitFrom;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("length")
    private Integer length;
}
