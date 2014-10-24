package de.fhws.javaee.fhws;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

@Entity
@NamedQuery(
    name=LogInfo.QUERY_BY_MESSAGE,
    query="SELECT li FROM LogInfo li WHERE li.message = :" + LogInfo.PARAM_MESSAGE
)
public class LogInfo implements Serializable {
    
    public static final String QUERY_BY_MESSAGE = "LogInfoByMessage";
    public static final String PARAM_MESSAGE = "message";

    @Id
    @GeneratedValue
    private Long id;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
