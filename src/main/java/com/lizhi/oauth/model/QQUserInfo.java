package com.lizhi.oauth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author: lizhi
 * @Date: 2020/1/14 14:47
 * @Description:
 */
@Data
public class QQUserInfo {
    private String nickname;
    @JsonProperty("figureurl")
    private String figureUrl30;
    @JsonProperty("figureurl_1")
    private String figureUrl50;
    @JsonProperty("figureurl_2")
    private String figureUrl100;
    private String gender;
    @JsonProperty("figureurl_qq_1")
    private String qqFigureUrl40;
    @JsonProperty("figureurl_qq_2")
    private String nicqqFigureUrl100;
    private String openId;

}
