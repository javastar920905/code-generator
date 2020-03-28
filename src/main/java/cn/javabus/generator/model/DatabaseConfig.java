package cn.javabus.generator.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Owen on 5/13/16.
 * <p>
 * selectedDatabaseConfig = {DatabaseConfig@4639} "DatabaseConfig{id=1, dbType='MySQL', name='dev', host='dbserver', port='3306', schema='article', username='apple', password='X0dxeLpK', encoding='utf8', lport='null', rport='null', sshPort='null', sshHost='null', sshUser='null', sshPassword='null'}"
 * id = {Integer@4676} 1
 * dbType = "MySQL"
 * name = "dev"
 * host = "dbserver"
 * port = "3306"
 * schema = "article"
 * username = "apple"
 * password = ""
 * encoding = "utf8"
 */
@ApiModel
@Getter
@Setter
public class DatabaseConfig {
    //tcp/ip 连接或者 ssh 方式连接
    @ApiModelProperty("tcp/ip 连接或者 ssh 方式连接 true/false")
    private Boolean isOverssh = false;
    private Boolean isUpdate = false;
    /**
     * The primary key in the sqlite db
     */
    @ApiModelProperty("The primary key in the sqlite db")
    private Integer id;

    @ApiModelProperty("MySQL")
    private String dbType;
    /**
     * The name of the config
     */
    @ApiModelProperty("The name of the config")
    private String name;
    @ApiModelProperty("dbserver")
    private String host;

    @ApiModelProperty("3306")
    private String port;

    @ApiModelProperty("数据库名 article_dev")
    private String schema;

    @ApiModelProperty("数据库登录名 apple")
    private String username;

    private String password;

    @ApiModelProperty("utf8")
    private String encoding;

    private String lport;

    private String rport;

    private String sshPort;

    private String sshHost;

    private String sshUser;

    private String sshPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getLport() {
        return lport;
    }

    public void setLport(String lport) {
        this.lport = lport;
    }

    public String getRport() {
        return rport;
    }

    public void setRport(String rport) {
        this.rport = rport;
    }

    public String getSshPort() {
        return sshPort;
    }

    public void setSshPort(String sshPort) {
        this.sshPort = sshPort;
    }

    public String getSshHost() {
        return sshHost;
    }

    public void setSshHost(String sshHost) {
        this.sshHost = sshHost;
    }

    public String getSshUser() {
        return sshUser;
    }

    public void setSshUser(String sshUser) {
        this.sshUser = sshUser;
    }

    public String getSshPassword() {
        return sshPassword;
    }

    public void setSshPassword(String sshPassword) {
        this.sshPassword = sshPassword;
    }

    public Boolean getOverssh() {
        return isOverssh;
    }

    public void setOverssh(Boolean overssh) {
        isOverssh = overssh;
    }


    public Boolean getUpdate() {
        return isUpdate;
    }

    public void setUpdate(Boolean update) {
        isUpdate = update;
    }
}
