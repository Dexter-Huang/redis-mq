package com.emergency.web.mq;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@ToString
public class RedisMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String topic;

    private ObjectNode data;

}
