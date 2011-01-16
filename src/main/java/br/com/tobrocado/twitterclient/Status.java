package br.com.tobrocado.twitterclient;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Status {

    @XmlElement(name="created_at") public String data;
    public String text;
    public User user;

    public String getData() {
        return data;
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    
}
