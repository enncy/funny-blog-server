package cn.enncy.funny.constant;


import lombok.Data;

/**
 * 邮箱支持字段
 *
 * @author: enncy
 */


public enum EmailType {

    /* qq邮箱 */
    QQ("@qq.com", Domain.QQ),
    QQ_VIP("@vip.qq.com", Domain.QQ);

    /**
     *  支持的邮箱
     */
    public String value;
    /**
     *  邮箱归属的域名
     */
    public Domain domain;

    EmailType(String value) {
        this.value = value;
    }

    EmailType(String value, Domain domain) {
        this.value = value;
        this.domain = domain;
    }


    /**
     *  支持的域名
     */
    public enum Domain {
        // 邮箱域名
        QQ("qq.com");

        public String value;

        Domain(String value) {
            this.value = value;
        }
    }
}
