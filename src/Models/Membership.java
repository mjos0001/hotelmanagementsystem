/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mrkjse
 */
@Entity
@Table(name = "MEMBERSHIP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Membership.findAll", query = "SELECT m FROM Membership m")
    , @NamedQuery(name = "Membership.findByMembershipTierCode", query = "SELECT m FROM Membership m WHERE m.membershipTierCode = :membershipTierCode")
    , @NamedQuery(name = "Membership.findByMembershipTier", query = "SELECT m FROM Membership m WHERE m.membershipTier = :membershipTier")
    , @NamedQuery(name = "Membership.findByTierCredits", query = "SELECT m FROM Membership m WHERE m.tierCredits <= :tierCredits")
    , @NamedQuery(name = "Membership.findByDiscount", query = "SELECT m FROM Membership m WHERE m.discount = :discount")
    , @NamedQuery(name = "Membership.findByOtherRewards", query = "SELECT m FROM Membership m WHERE m.otherRewards = :otherRewards")})
public class Membership implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "MEMBERSHIP_TIER_CODE")
    private String membershipTierCode;
    @Basic(optional = false)
    @Column(name = "MEMBERSHIP_TIER")
    private String membershipTier;
    @Basic(optional = false)
    @Column(name = "TIER_CREDITS")
    private long tierCredits;
    @Basic(optional = false)
    @Column(name = "DISCOUNT")
    private long discount;
    @Basic(optional = false)
    @Column(name = "OTHER_REWARDS")
    private String otherRewards;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "membership")
    private Collection<Customer> customerCollection;

    public Membership() {
    }

    public Membership(String membershipTierCode) {
        this.membershipTierCode = membershipTierCode;
    }

    public Membership(String membershipTierCode, String membershipTier, long tierCredits, long discount, String otherRewards) {
        this.membershipTierCode = membershipTierCode;
        this.membershipTier = membershipTier;
        this.tierCredits = tierCredits;
        this.discount = discount;
        this.otherRewards = otherRewards;
    }

    public String getMembershipTierCode() {
        return membershipTierCode;
    }

    public void setMembershipTierCode(String membershipTierCode) {
        this.membershipTierCode = membershipTierCode;
    }

    public String getMembershipTier() {
        return membershipTier;
    }

    public void setMembershipTier(String membershipTier) {
        this.membershipTier = membershipTier;
    }

    public long getTierCredits() {
        return tierCredits;
    }

    public void setTierCredits(long tierCredits) {
        this.tierCredits = tierCredits;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public String getOtherRewards() {
        return otherRewards;
    }

    public void setOtherRewards(String otherRewards) {
        this.otherRewards = otherRewards;
    }

    @XmlTransient
    public Collection<Customer> getCustomerCollection() {
        return customerCollection;
    }

    public void setCustomerCollection(Collection<Customer> customerCollection) {
        this.customerCollection = customerCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (membershipTierCode != null ? membershipTierCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Membership)) {
            return false;
        }
        Membership other = (Membership) object;
        if ((this.membershipTierCode == null && other.membershipTierCode != null) || (this.membershipTierCode != null && !this.membershipTierCode.equals(other.membershipTierCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Membership[ membershipTierCode=" + membershipTierCode + " ]";
    }
    
}
