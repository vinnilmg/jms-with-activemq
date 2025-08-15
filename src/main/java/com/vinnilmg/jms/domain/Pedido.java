package com.vinnilmg.jms.domain;

import com.vinnilmg.jms.utils.DateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date createdDate;
    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date paymentDate;
    private BigDecimal total;
    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    private List<Item> items;

    public Pedido() {
    }

    public Pedido(int id, Date createdDate, Date paymentDate, BigDecimal total, List<Item> items) {
        this.id = id;
        this.createdDate = createdDate;
        this.paymentDate = paymentDate;
        this.total = total;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", paymentDate=" + paymentDate +
                ", total=" + total +
                ", items=" + items +
                '}';
    }
}
