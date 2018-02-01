
package tt.general;

import java.io.Serializable;

public class CatConfiguracionMail implements Serializable{
    
    private String id_conf_mail, des_conf_mail, hostname, smtp_port, user, pass,  mailfrom;

    public CatConfiguracionMail() {
    }

    public CatConfiguracionMail(String id_conf_mail, String des_conf_mail, String hostname, String smtp_port, String user, String pass, String mailfrom) {
        this.id_conf_mail = id_conf_mail;
        this.des_conf_mail = des_conf_mail;
        this.hostname = hostname;
        this.smtp_port = smtp_port;
        this.user = user;
        this.pass = pass;
        this.mailfrom = mailfrom;
    }

    public String getId_conf_mail() {
        return id_conf_mail;
    }

    public void setId_conf_mail(String id_conf_mail) {
        this.id_conf_mail = id_conf_mail;
    }

    public String getDes_conf_mail() {
        return des_conf_mail;
    }

    public void setDes_conf_mail(String des_conf_mail) {
        this.des_conf_mail = des_conf_mail;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getSmtp_port() {
        return smtp_port;
    }

    public void setSmtp_port(String smtp_port) {
        this.smtp_port = smtp_port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMailfrom() {
        return mailfrom;
    }

    public void setMailfrom(String mailfrom) {
        this.mailfrom = mailfrom;
    }
   
}
